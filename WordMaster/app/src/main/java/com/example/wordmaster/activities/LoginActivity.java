package com.example.wordmaster.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.databinding.ActivityLoginBinding;
import com.example.wordmaster.dialog.bottomsheet.LoginSuccessSheetDialog;
import com.example.wordmaster.model.firebase.UserAccount;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mb;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = ActivityLoginBinding.inflate(getLayoutInflater());
        // 컨텍스트를 넘겨서 sharedManger 에 저장
        SharedManger.init(getApplicationContext());
        View root = mb.getRoot();
        // 처음 앱을 설치해서 로그인을 해야될경우 Login 화면 보여줌
        Log.e(TAG, "onCreate: "+SharedManger.getUserId() );
        if (SharedManger.getUserId().equals("")){
            setContentView(root);
            //getHashKey();
            init();

        }
        // 로그인 한적이 있을떄 바로 메인화면으로 전환
        else{
            changeLogin2Main();
        }

    }
    public void init() {
        // 로그인 성공여부 체크 콜백 메서드
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken != null) {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
                return null;
            }
        };
        // 카카오톡 설치여부에따라 자동로그인을 해주거나 웹에서 로그인
        mb.kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);

                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);

                }
                showLoginSuccessDialog();
            }
        });

    }
    /**
     * 처음 가입할경우 파베에 유저정보 저장 키값과 밸류값 받음
     * @param id 키값 : 유저 아이디(계정당 가질수 있는 교유값을 키값으로 지정)
     * @param account 밸류값 : 계정정보를 저장한 객체를 밸류값으로 지정
     */
    private void addUserInformationFirebase(String id, UserAccount account){
        Util.myRefUser.child(id).setValue(account);
    }

    // 로그인 화면에서 메인 화면으로 전환
    private void changeLogin2Main(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();

    }
    // 카카오 로그인 성공시
    private void showLoginSuccessDialog(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 유저정보를 가져왔을때
                if (user != null) {
                    // 유저 고유 값
                    String userId = String.valueOf(user.getId());
                    // 로그인 성공시 유저정보 바텀시트 뿌려줄 다이얼로그
                    LoginSuccessSheetDialog loginSuccessSheetDialog = new LoginSuccessSheetDialog();
                    // 다이얼로그에 넘겨줄값 번들에 저장
                    Bundle bundle = new Bundle();
                    // 프사 uri 와 닉네임 넘겨줌
                    bundle.putString("image_uri",user.getKakaoAccount().getProfile().getProfileImageUrl());
                    bundle.putString("nickname",user.getKakaoAccount().getProfile().getNickname());
                    loginSuccessSheetDialog.setArguments(bundle);
                    loginSuccessSheetDialog.show(getSupportFragmentManager(),"login_success_bottom_sheet");
                    loginSuccessSheetDialog.setBottomSheetSetOnClick(new LoginSuccessSheetDialog.BottomSheetSetOnClick() {
                        // 다이얼로그에서 콜백 받아서 name 값 가져옴
                        @Override
                        public void clickSubmit(String name) {
                            //Log.e(TAG, user.getKakaoAccount().getAgeRange().toString()+"" );
                            addUserInformationFirebase(String.valueOf(user.getId()),new UserAccount(
                                    name,
                                    user.getKakaoAccount().getEmail(),
                                    user.getKakaoAccount().getGender().toString(),
                                    "",
                                    user.getKakaoAccount().getBirthday(),
                                    user.getKakaoAccount().getProfile().getProfileImageUrl()
                            ));
                            // 셰어드 프리퍼런스에 계정 정보 저장
                            SharedManger.saveUserId(Const.SHARED_USER_ID, userId);
                            SharedManger.saveUserName(Const.SHARED_USER_NAME,name);
                            SharedManger.saveUserProfileUri(Const.SHARED_USER_PROFILE_URI,user.getKakaoAccount().getProfile().getProfileImageUrl());
                            SharedManger.saveUserEmail(Const.SHARED_USER_EMAIL,user.getKakaoAccount().getEmail());
                            changeLogin2Main();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"유저정보를 제대로 가져오지 못헀습니다. 다시시도해주세요",Toast.LENGTH_LONG).show();
                }
                return null;
            }
        });
    }

    // 해시키  가져오기
    private void getHashKey() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}