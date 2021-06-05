package com.example.wordmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.Define.Util;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.FragmentTestBinding;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class TestFragment extends Fragment {
    private FragmentTestBinding getArgs;
    private int dictMaxCount, rgDictType, rgOption;
    private String dictTitle, dictHostName, limitTime, spUserId, spUserEmail, spUserName;
    private String myTestUserid, myTestRoomKey, myTestHost, myTestTitle;
    private int myTestMaxCount, myTestRgTestType;
    private static String TAG = "TestFragment";
    private int currentIdx = 0;
    private ArrayList<DictionaryWordItem> testList = new ArrayList<>();
    private ArrayList<String> mylist = new ArrayList<>();
    private String[] myArr;
    private String[] answerArr;
    private ArrayList<String> answerList = new ArrayList<>();
    private MainActivity activity;
    private int myScore = 0;
    private SendDataToActivity sendDataToActivity = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs = FragmentTestBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SendDataToActivity) {
            sendDataToActivity = (SendDataToActivity) context;
        }
        activity = (MainActivity) getActivity();
        if (activity != null) {
            spUserId = SharedManger.loadData("userId", "");
            spUserEmail = SharedManger.loadData("userEmail", "");
            spUserName = SharedManger.loadData("userName", "");
        }
        Bundle getArgs = getArguments();
        if (getArgs != null) {
            dictTitle = getArgs.getString("tvTestTitle", "");
            dictHostName = getArgs.getString("tvTestHost", "");
            dictMaxCount = getArgs.getInt("testMaxCount", -1);
            limitTime = getArgs.getString("testLimitTime", "");
            rgDictType = getArgs.getInt("rgTestType", -1);
            rgOption = getArgs.getInt("rgTestTimeOption", -1);
            myTestUserid = getArgs.getString("userId", "");
            myTestRoomKey = getArgs.getString("roomKey", "");
            myTestRgTestType = getArgs.getInt("testOption", 0);
            myTestMaxCount = getArgs.getInt("maxCount", 0);
            myTestTitle = getArgs.getString("title", "");
            myTestHost = getArgs.getString("host", "");
        }
        myArr = new String[myTestMaxCount];
        answerArr = new String[myTestMaxCount];
        readDBListItem();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        setTestList();

        //setFirstItem();
        //showWord(0);
        return getArgs.getRoot();
    }

    private void init() {
        getArgs.tvWordTestProgressText.setText("1/" + myTestMaxCount);
        getArgs.tvTestTitle.setText(myTestTitle);
        getArgs.tvTestHostName.setText(myTestHost + "님의 테스트");
        getArgs.pgWordTestProgress.setMax(myTestMaxCount);
        getArgs.ibPreviousWord.setVisibility(View.INVISIBLE);

        getArgs.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArr[currentIdx] = getArgs.etWordAnswer.getText().toString();
                answerArr = answerList.toArray(new String[0]);
                for (int i = 0; i < answerArr.length; i++) {
                    Log.e("s", answerArr[i] + " " + myArr[i]);
                    if (answerArr[i].equals(myArr[i])) {
                        myScore += 1;
                    }
                }
                Toast.makeText(getContext(), "너님의 점수는" + myScore + "/" + dictMaxCount, Toast.LENGTH_SHORT).show();
                sendDataToActivity.sendTestResult(dictMaxCount, myScore, myArr, answerArr, testList);
                activity.changeFragment(Const.TEST_RESULT_FRAGMENT);
            }
        });

    }

    public void showWord(int pos) {
        if (pos == 0) {
            getArgs.ibPreviousWord.setVisibility(View.INVISIBLE);
        } else {
            getArgs.ibPreviousWord.setVisibility(View.VISIBLE);
        }
        if (pos == dictMaxCount - 1) {
            getArgs.ibNextWord.setVisibility(View.INVISIBLE);
            getArgs.btnSubmit.setVisibility(View.VISIBLE);
        } else {
            getArgs.ibNextWord.setVisibility(View.VISIBLE);
            getArgs.btnSubmit.setVisibility(View.INVISIBLE);
        }


            getArgs.tvWordQuestion.setText(mylist.get(pos));

    }


    private void setTestList() {
        // 다음 문제 버튼을 눌렀을때
        getArgs.ibNextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArr[currentIdx] = getArgs.etWordAnswer.getText().toString();
//                mylist.add(mb.etWordAnswer.getText().toString());
//                Log.e(TAG, "onClick: "+mb.etWordAnswer.getText().toString() );
//                Log.e(TAG, "onClick: "+mylist );
                currentIdx += 1;
                getArgs.tvWordTestProgressText.setText((currentIdx + 1) + "/" + dictMaxCount);
                getArgs.pgWordTestProgress.setProgress(currentIdx + 1);
                Log.e(TAG, "onClick: " + currentIdx);
                showWord(currentIdx);

                getArgs.etWordAnswer.setText("");


            }
        });

        //이전문제 버튼을 눌렀을때
        getArgs.ibPreviousWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArr[currentIdx] = getArgs.etWordAnswer.getText().toString();
                currentIdx -= 1;
                getArgs.tvWordTestProgressText.setText((currentIdx + 1) + "/" + dictMaxCount);
                getArgs.pgWordTestProgress.setProgress(currentIdx + 1);
                Toast.makeText(getContext(), currentIdx + "", Toast.LENGTH_SHORT).show();
                showWord(currentIdx);
                Log.e(TAG, "onClick: " + mylist);
                getArgs.etWordAnswer.setText(myArr[currentIdx]);
            }
        });

    }

    public void readDBListItem() {
        Util.myRefWord.child(myTestUserid).child(myTestRoomKey).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()) {
                    DictionaryWordItem item = data.getValue(DictionaryWordItem.class);
                    if (item!=null){
                        if(myTestRgTestType == Const.KOR2ENG){
                            mylist.add(item.getKor());
                            answerList.add(item.getEng());
                        }else if(myTestRgTestType == Const.ENG2KOR){
                            mylist.add(item.getEng());
                            answerList.add(item.getKor());
                        }else {
                            Random random = new Random();
                            int getValue = random.nextInt(2);
                            // 랜덤함수 0또는 1만 랜덤으로 출력하게하여 랜덤으로 문제 넣기
                            if(getValue==0){
                                mylist.add(item.getEng());
                                answerList.add(item.getKor());
                            }else{
                                mylist.add(item.getKor());
                                answerList.add(item.getEng());
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}
