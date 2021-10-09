package com.example.wordmaster.view.dialog.bottomsheet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.R;
import com.example.wordmaster.adapter.CustomSpinnerAdapter;
import com.example.wordmaster.databinding.DialogBottomSheetSetWordTestBinding;
import com.example.wordmaster.view.dialog.custom.DateTimeSettingDialog;
import com.example.wordmaster.model.etc.SpinnerItem;
import com.example.wordmaster.model.firebase.UserDictionary;
import com.example.wordmaster.model.firebase.UserTest;
import com.example.wordmaster.model.recycler.OnlineTestMemberItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class WordTestSettingDialog extends BottomSheetDialogFragment implements DateTimeSettingDialog.DateTimeCallBack {
    private static final String TAG = "WordTestSettingDialog";
    private static final int TEST_ORDER_BY_LINEAR = 1;
    private static final int TEST_ORDER_BY_RANDOM = 2;
    private static final int TEST_TYPE_ENG2KOR = 1;
    private static final int TEST_TYPE_KOR2ENG = 2;
    private static final int TEST_TYPE_RANDOM = 3;
    private static final int TEST_PRIVATE = 1;
    private static final int TEST_PUBLIC = 2;
    private int maxCountDict;
    private DialogBottomSheetSetWordTestBinding mb;
    private TestBottomSheetCallBack listener;
    private int dictMaxCount;


    private int rgTestOrderBy = 0, rgTestType = 0, rgTestOption = 0;

    @Override
    public void startTimeCallback(String date) {
        mb.btnSettingStartTime.setText(date);

    }

    @Override
    public void endTimeCallback(String date) {
        mb.btnSettingEndTime.setText(date);
    }

    public interface TestBottomSheetCallBack {
        void setOnClickListener(int maxCount, String limitTime, int rgTestType, int rgTestTimeOption);

    }

    public void setListener(TestBottomSheetCallBack listener) {
        this.listener = listener;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetSetWordTestBinding.inflate(getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        return mb.getRoot();
    }

    /**
     * 스피너에 추가할 내 단어장 모두 불러옴
     */
    private void loadSpinnerWordList(ArrayAdapter<SpinnerItem> adapter) {
        String userId = SharedManger.loadData(Const.SHARED_USER_ID, "");
        Util.myRefWord.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot item : snapshot.getChildren()) {
                    UserDictionary getInfo = item.getValue(UserDictionary.class);
                    Log.e(TAG, "onDataChange: " + getInfo.getTitle());
                    adapter.add(new SpinnerItem(
                            getInfo.getTitle(),
                            String.valueOf(getInfo.getCurrentCount()),
                            String.valueOf(getInfo.getMaxCount()),
                            getInfo.getDescription(),
                            getInfo.getRoomKey()
                    ));

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        adapter.notifyDataSetChanged();

    }

    @SuppressLint("NonConstantResourceId")
    private void init() {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getContext(), R.layout.spinner_custom_item);
        loadSpinnerWordList(adapter);
        mb.wordSpinner.setAdapter(adapter);

        mb.rgTestTestOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.test_option_linear:
                        rgTestOrderBy = TEST_ORDER_BY_LINEAR;
                        break;
                    case R.id.test_option_random:
                        rgTestOrderBy = TEST_ORDER_BY_RANDOM;
                        break;
                    default:
                        break;
                }
                Log.e(TAG, "onCheckedChanged: " + rgTestOrderBy);

            }
        });
        mb.rgTestType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.kor2eng:
                        rgTestType = TEST_TYPE_KOR2ENG;
                        break;
                    case R.id.eng2kor:
                        rgTestType = TEST_TYPE_ENG2KOR;
                        break;
                    case R.id.random:
                        rgTestType = TEST_TYPE_RANDOM;
                        break;
                    default:
                        break;
                }
                Log.e(TAG, "onCheckedChanged: " + rgTestType);
            }
        });
        mb.rgTestPrivatePublic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.r_btn_private:
                        rgTestOption = TEST_PRIVATE;
                        break;
                    case R.id.r_btn_public:
                        rgTestOption = TEST_PUBLIC;
                        break;
                    default:
                        break;
                }
                Log.e(TAG, "onCheckedChanged: " + rgTestOption);

            }
        });

        mb.btnSettingStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeSettingDialog dialog = new DateTimeSettingDialog(getContext());
                dialog.setDateTimeCallback(WordTestSettingDialog.this);
                dialog.setMode(DateTimeSettingDialog.START_TIME);
                dialog.show();
            }
        });
        mb.btnSettingEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimeSettingDialog dialog = new DateTimeSettingDialog(getContext());
                dialog.setDateTimeCallback(WordTestSettingDialog.this);
                dialog.setMode(DateTimeSettingDialog.END_TIME);
                dialog.show();
            }
        });
        mb.btnUserPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getUserCount = Integer.parseInt(mb.etUserCount.getText().toString());
                getUserCount += 1;
                mb.etUserCount.setText(String.valueOf(getUserCount));
            }
        });
        mb.btnUserMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getUserCount = Integer.parseInt(mb.etUserCount.getText().toString());
                if (getUserCount == 1) {
                    Toast.makeText(getContext(), "최소 1명 이상이여야 됩니다.", Toast.LENGTH_SHORT).show();
                } else {
                    getUserCount -= 1;
                    mb.etUserCount.setText(String.valueOf(getUserCount));
                }
            }
        });

        mb.btnTestStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference pushRef = Util.myRefTest.push();
                String roomKey = pushRef.getKey();
                // 현재 스피너에서 선택된 아이템 가져옴
                SpinnerItem item = (SpinnerItem) mb.wordSpinner.getItemAtPosition(mb.wordSpinner.getSelectedItemPosition());
                // 단어테스트 멤버 초기값으로 자기 자신을 추가
                ArrayList<OnlineTestMemberItem> list = new ArrayList<>();
                HashMap<String,OnlineTestMemberItem> hashMap = new HashMap<>();
                hashMap.put(SharedManger.loadData(Const.SHARED_USER_ID,""),new OnlineTestMemberItem(
                        SharedManger.loadData(Const.SHARED_USER_NAME, ""),
                        SharedManger.loadData(Const.SHARED_USER_ID, ""),
                        SharedManger.loadData(Const.SHARED_USER_PROFILE_URI, ""),
                        "1등 할고야"
                ));

                mb.wordSpinner.getItemAtPosition(mb.wordSpinner.getSelectedItemPosition());
                UserTest userTest = new UserTest(
                        mb.etOnlineTestName.getText().toString(),
                        SharedManger.loadData(Const.SHARED_USER_ID,""),
                        SharedManger.loadData(Const.SHARED_USER_NAME,""),
                        item.getRoomKey(),
                        mb.btnSettingStartTime.getText().toString(),
                        mb.btnSettingEndTime.getText().toString(),
                        "",
                        mb.etOnlineTestDescription.getText().toString(),
                        rgTestOption,
                        rgTestOrderBy,
                        Integer.parseInt(mb.etUserCount.getText().toString()),
                        rgTestType,
                        Integer.parseInt(item.getMaxCount()),
                        hashMap,
                        roomKey);

                pushRef.setValue(userTest);
                SpinnerItem spinnerItem = (SpinnerItem) mb.wordSpinner.getSelectedItem();
                Log.e(TAG, "onClick: " + spinnerItem.getCurrentCount() + " " + spinnerItem.getMaxCount());
                Log.e(TAG, "onClick: " + item.getDescription());
                if (spinnerItem.getCurrentCount() == spinnerItem.getMaxCount()) {
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "현재 단어장을 전부 채워주세요", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}
