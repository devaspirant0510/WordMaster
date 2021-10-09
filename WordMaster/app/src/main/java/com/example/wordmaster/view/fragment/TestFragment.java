package com.example.wordmaster.view.fragment;

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
import com.example.wordmaster.view.activities.MainActivity;
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
    private FragmentTestBinding mb;
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
        mb = FragmentTestBinding.inflate(getLayoutInflater());
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
        return mb.getRoot();
    }

    private void init() {
        mb.tvWordTestProgressText.setText("1/" + myTestMaxCount);
        mb.tvTestTitle.setText(myTestTitle);
        mb.tvTestHostName.setText(myTestHost + "님의 테스트");
        mb.pgWordTestProgress.setMax(myTestMaxCount);
        mb.ibPreviousWord.setVisibility(View.INVISIBLE);

        mb.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArr[currentIdx] = mb.etWordAnswer.getText().toString();
                answerArr = answerList.toArray(new String[0]);
                for (int i = 0; i < answerArr.length; i++) {
                    Log.e("s", answerArr[i] + " " + myArr[i]);
                    if (answerArr[i].equals(myArr[i])) {
                        myScore += 1;
                    }
                }
                Toast.makeText(getContext(), "너님의 점수는" + myScore + "/" + myTestMaxCount, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onClick: "+answerArr.length+""+myArr.length );
                Log.e(TAG, "onClick: "+testList.get(0).getEng() );
                sendDataToActivity.sendTestResult(myTestMaxCount, myScore, myArr, answerArr, testList);

                activity.changeFragment(Const.TEST_RESULT_FRAGMENT);
            }
        });

    }

    public void showWord(int pos) {
        Log.e(TAG, "showWord: "+pos+"/"+myTestMaxCount );
        if (pos == 0) {
            mb.ibPreviousWord.setVisibility(View.INVISIBLE);
        } else {
            mb.ibPreviousWord.setVisibility(View.VISIBLE);
        }
        if (pos == myTestMaxCount - 1) {
            mb.ibNextWord.setVisibility(View.INVISIBLE);
            mb.btnSubmit.setVisibility(View.VISIBLE);
        } else {
            mb.ibNextWord.setVisibility(View.VISIBLE);
            mb.btnSubmit.setVisibility(View.INVISIBLE);
        }


            mb.tvWordQuestion.setText(mylist.get(pos));

    }


    private void setTestList() {
        // 다음 문제 버튼을 눌렀을때
        mb.ibNextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArr[currentIdx] = mb.etWordAnswer.getText().toString();
//                mylist.add(mb.etWordAnswer.getText().toString());
//                Log.e(TAG, "onClick: "+mb.etWordAnswer.getText().toString() );
//                Log.e(TAG, "onClick: "+mylist );
                currentIdx += 1;
                mb.tvWordTestProgressText.setText((currentIdx + 1) + "/" + myTestMaxCount);
                mb.pgWordTestProgress.setProgress(currentIdx + 1);
                Log.e(TAG, "onClick: " + currentIdx);
                showWord(currentIdx);

                mb.etWordAnswer.setText("");


            }
        });

        //이전문제 버튼을 눌렀을때
        mb.ibPreviousWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myArr[currentIdx] = mb.etWordAnswer.getText().toString();
                currentIdx -= 1;
                mb.tvWordTestProgressText.setText((currentIdx + 1) + "/" + myTestMaxCount);
                mb.pgWordTestProgress.setProgress(currentIdx + 1);
                Toast.makeText(getContext(), currentIdx + "", Toast.LENGTH_SHORT).show();
                showWord(currentIdx);
                Log.e(TAG, "onClick: " + mylist);
                mb.etWordAnswer.setText(myArr[currentIdx]);
            }
        });

    }

    public void readDBListItem() {
        Util.myRefWord.child(myTestUserid).child(myTestRoomKey).child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot data:snapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: "+data );
                    DictionaryWordItem item = data.getValue(DictionaryWordItem.class);
                    if (item!=null){
                        if(myTestRgTestType == Const.KOR2ENG){
                            mylist.add(item.getKor());
                            answerList.add(item.getEng());
                            DictionaryWordItem wordItem = new DictionaryWordItem();
                            wordItem.setEng(item.getEng());
                            wordItem.setKor(item.getKor());
                            testList.add(wordItem);
                        }else if(myTestRgTestType == Const.ENG2KOR){
                            mylist.add(item.getEng());
                            answerList.add(item.getKor());
                            DictionaryWordItem wordItem = new DictionaryWordItem();
                            wordItem.setEng(item.getEng());
                            wordItem.setKor(item.getKor());
                            testList.add(wordItem);
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
                            DictionaryWordItem wordItem = new DictionaryWordItem();
                            wordItem.setEng(item.getEng());
                            wordItem.setKor(item.getKor());
                            testList.add(wordItem);
                        }
                    }
                    showWord(0);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}
