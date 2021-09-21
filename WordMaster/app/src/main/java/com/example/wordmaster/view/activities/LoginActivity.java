package com.example.wordmaster.view.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.R;
import com.example.wordmaster.databinding.ActivityLoginBinding;
import com.example.wordmaster.view.dialog.bottomsheet.LoginSuccessSheetDialog;
import com.example.wordmaster.model.firebase.UserAccount;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private static final String TAG = "LoginActivity";
    Function2<OAuthToken, Throwable, Unit> callback;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // 컨텍스트를 넘겨서 sharedManger 에 저장
        SharedManger.init(getApplicationContext());
        View root = binding.getRoot();

        // 처음 앱을 설치해서 로그인을 해야될경우 Login 화면 보여줌
        Log.e(TAG, "onCreate: " + SharedManger.getUserId());
        if (SharedManger.getUserId().equals("")) {
            setContentView(root);
            //getHashKey();
            init();

        }
        // 로그인 한적이 있을떄 바로 메인화면으로 전환
        else {
            changeLogin2Main();
        }

    }

    private void setLoginGoogle() {
        Intent googleIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(googleIntent, 123);
    }

    public void init() {
        getHashKey();
        // 구글 로그인
        mFirebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // 로그인 성공여부 체크 콜백 메서드
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                Log.e(TAG, "invoke: ");
                if (oAuthToken != null) {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
                showLoginSuccessDialog();
                return null;
            }
        };
        // 카카오톡 설치여부에따라 자동로그인을 해주거나 웹에서 로그인
        binding.kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);

                } else {

                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);

                }
            }
        });
        binding.btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLoginGoogle();
            }
        });

    }

    private void web() {
        UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);

    }

    /**
     * 처음 가입할경우 파베에 유저정보 저장 키값과 밸류값 받음
     *
     * @param id      키값 : 유저 아이디(계정당 가질수 있는 교유값을 키값으로 지정)
     * @param account 밸류값 : 계정정보를 저장한 객체를 밸류값으로 지정
     */
    private void addUserInformationFirebase(String id, UserAccount account) {
        Log.e(TAG, "addUserInformationFirebase: "+account.getJoinDate() );
        Util.myRefUser.child(id).setValue(account);
    }

    // 로그인 화면에서 메인 화면으로 전환
    private void changeLogin2Main() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();

    }

    // 카카오 로그인 성공시
    private void showLoginSuccessDialog() {
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                // 유저정보를 가져왔을때
                if (user != null) {
                    // 유저 고유 값
                    String userId = String.valueOf(user.getId());
                    checkUser(userId, user);

                    // 로그인 성공시 유저정보 바텀시트 뿌려줄 다이얼로그
                } else {
                    Toast.makeText(getApplicationContext(), "유저정보를 제대로 가져오지 못헀습니다. 다시시도해주세요", Toast.LENGTH_LONG).show();
                }
                return null;
            }
        });
    }

    private void saveUserData(String id) {
        Util.myRefUser.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                if (userAccount != null) {
                    SharedManger.saveData(Const.SHARED_USER_NAME, userAccount.getUserName());
                    SharedManger.saveData(Const.SHARED_USER_ID, id);
                    SharedManger.saveData(Const.SHARED_USER_EMAIL, userAccount.getUserEmail());
                    SharedManger.saveData(Const.SHARED_USER_PROFILE_URI, userAccount.getUserProfileUri());
                    SharedManger.saveData(Const.SHARED_USER_JOIN_DATE,userAccount.getJoinDate());
                }
                changeLogin2Main();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    Log.e(TAG, "onActivityResult: " + account.getAccount().name + " "
                            + account.getEmail() + " " + account.getId() + " " + account.getIdToken());
                    Log.e(TAG, "onActivityResult: "+account.getDisplayName() );
                    Log.e(TAG, "onActivityResult: "+account.getFamilyName() );
                    Log.e(TAG, "onActivityResult: "+account.getServerAuthCode() );
                    Log.e(TAG, "onActivityResult: "+account.getGivenName() );
                    Log.e(TAG, "onActivityResult: "+account.getPhotoUrl().toString() );
                    checkUser(account.getId(),account.getDisplayName(),account.getEmail(),account.getPhotoUrl().toString());
                }

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(getApplicationContext(), "Google sign in Failed", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void checkUser( String id, String nickName,String email, String profileURL) {
        Util.myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    if(item.getKey().equals(id)){
                        saveUserData(id);
                        return;
                    }

                }

                // 신규 가입 유저라면 로그인 한정보 가져와 정보 수정 권한 부여
                LoginSuccessSheetDialog loginSuccessSheetDialog = new LoginSuccessSheetDialog();
                // 다이얼로그에 넘겨줄값 번들에 저장
                Bundle bundle = new Bundle();
                // 프사 uri 와 닉네임 넘겨줌
                bundle.putString("image_uri", profileURL);
                bundle.putString("nickname", nickName);
                loginSuccessSheetDialog.setArguments(bundle);
                loginSuccessSheetDialog.show(getSupportFragmentManager(), "login_success_bottom_sheet");
                loginSuccessSheetDialog.setBottomSheetSetOnClick(new LoginSuccessSheetDialog.BottomSheetSetOnClick() {
                    @Override
                    public void clickSubmit(String name) {
                        Calendar calendar = Calendar.getInstance();
                        int month = calendar.get(Calendar.MONTH)+1;
                        int year = calendar.get(Calendar.YEAR);
                        int days = calendar.get(Calendar.DAY_OF_MONTH);
                        String date = year+"/"+month+"/"+days;
                        addUserInformationFirebase(
                                id,
                                new UserAccount(
                                        name,
                                        email,
                                        null,
                                        null,
                                        null,
                                        profileURL,
                                        null,
                                        date,
                                        "",
                                        null
                                )
                        );
                        // 셰어드 프리퍼런스에 계정 정보 저장
                        SharedManger.saveData(Const.SHARED_USER_ID, id);
                        SharedManger.saveData(Const.SHARED_USER_NAME, name);
                        SharedManger.saveData(Const.SHARED_USER_PROFILE_URI, profileURL);
                        SharedManger.saveData(Const.SHARED_USER_EMAIL, email);
                        SharedManger.saveData(Const.SHARED_USER_JOIN_DATE,date);
                        changeLogin2Main();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /**
     * 로그인한 기록이 있는지 확인
     *
     * @param id   user 고유 아이디
     * @param user 카카오 user 정보
     */
    @Deprecated
    private void checkUser(String id, User user) {
        // 아이디 중복체크
        Util.myRefUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    // 회원가입한 유저라면 셰어드프리펀스에 정보저장
                    if (id != null & Objects.equals(item.getKey(), id)) {
                        saveUserData(id);
                        return;
                    }
                }
                // 신규 가입 유저라면 로그인 한정보 가져와 정보 수정 권한 부여
                LoginSuccessSheetDialog loginSuccessSheetDialog = new LoginSuccessSheetDialog();
                // 다이얼로그에 넘겨줄값 번들에 저장
                Bundle bundle = new Bundle();
                // 프사 uri 와 닉네임 넘겨줌
                bundle.putString("image_uri", user.getKakaoAccount().getProfile().getProfileImageUrl());
                bundle.putString("nickname", user.getKakaoAccount().getProfile().getNickname());
                loginSuccessSheetDialog.setArguments(bundle);
                loginSuccessSheetDialog.show(getSupportFragmentManager(), "login_success_bottom_sheet");
                loginSuccessSheetDialog.setBottomSheetSetOnClick(new LoginSuccessSheetDialog.BottomSheetSetOnClick() {
                    // 다이얼로그에서 콜백 받아서 name 값 가져옴
                    @Override
                    public void clickSubmit(String name) {
                        Calendar calendar = Calendar.getInstance();
                        int month = calendar.get(Calendar.MONTH)+1;
                        int year = calendar.get(Calendar.YEAR);
                        int days = calendar.get(Calendar.DAY_OF_MONTH);
                        String date = year+"/"+month+"/"+days;
                        //Log.e(TAG, user.getKakaoAccount().getAgeRange().toString()+"" );
                        addUserInformationFirebase(String.valueOf(user.getId()), new UserAccount(
                                name,
                                user.getKakaoAccount().getEmail(),
                                user.getKakaoAccount().getGender().toString(),
                                "",
                                user.getKakaoAccount().getBirthday(),
                                user.getKakaoAccount().getProfile().getProfileImageUrl(),
                                null,
                                date,
                                "",
                                null
                        ));
                        // 셰어드 프리퍼런스에 계정 정보 저장
                        SharedManger.saveData(Const.SHARED_USER_ID, id);
                        SharedManger.saveData(Const.SHARED_USER_NAME, name);
                        SharedManger.saveData(Const.SHARED_USER_PROFILE_URI, user.getKakaoAccount().getProfile().getProfileImageUrl());
                        SharedManger.saveData(Const.SHARED_USER_EMAIL, user.getKakaoAccount().getEmail());
                        changeLogin2Main();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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