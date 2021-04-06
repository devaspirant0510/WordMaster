package com.example.wordmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wordmaster.R;
import com.example.wordmaster.callback.SessionCallback;
import com.example.wordmaster.data.firebase.UserAccount;
import com.example.wordmaster.databinding.ActivityLoginBinding;
import com.example.wordmaster.dialog.bottomsheet.LoginBottomSheetDialog;
import com.example.wordmaster.dialog.bottomsheet.LoginSuccessSheetDialog;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.Gender;
import com.kakao.sdk.user.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding mb;
    private FirebaseAuth mAuth;
    private static final int RESULT_SIGN_GOOGLE = 100;
    private static final String TAG = "LoginActivity";
    private SharedPreferences sharedPreferences;
    public static String USER = "";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = ActivityLoginBinding.inflate(getLayoutInflater());
        View root = mb.getRoot();
        setContentView(root);
        //getHashKey();
        setFirebase();
        init();

    }

    private void setFirebase() {
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("UserAccount");

    }
    private void addUserInformationFirebase(String id, UserAccount account){
        Log.e(TAG, "addUserInformationFirebase: " );
        mRef.child(id).setValue(null);
        mRef.child(id).setValue(account);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    private void showLoginSuccessDialog(){

        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if (user != null) {
                    USER = String.valueOf(user.getId());
                    LoginSuccessSheetDialog loginSuccessSheetDialog = new LoginSuccessSheetDialog();
                    Bundle bundle = new Bundle();
                    bundle.putString("image_uri",user.getKakaoAccount().getProfile().getProfileImageUrl());
                    bundle.putString("nickname",user.getKakaoAccount().getProfile().getNickname());
                    loginSuccessSheetDialog.setArguments(bundle);
                    loginSuccessSheetDialog.show(getSupportFragmentManager(),"login_success_bottom_sheet");
                    loginSuccessSheetDialog.setBottomSheetSetOnClick(new LoginSuccessSheetDialog.BottomSheetSetOnClick() {
                        @Override
                        public void clickSubmit(String name) {
                            addUserInformationFirebase(String.valueOf(user.getId()),new UserAccount(
                                    name,
                                    user.getKakaoAccount().getEmail(),
                                    user.getKakaoAccount().getGender().toString(),
                                    user.getKakaoAccount().getAgeRange().toString(),
                                    user.getKakaoAccount().getBirthday(),
                                    user.getKakaoAccount().getProfile().getProfileImageUrl()
                            ));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                        }
                    });

                } else {

                }
                return null;
            }
        });
    }
    public void init() {
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
        mb.kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, callback);
                    showLoginSuccessDialog();

                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
                    showLoginSuccessDialog();

                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

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