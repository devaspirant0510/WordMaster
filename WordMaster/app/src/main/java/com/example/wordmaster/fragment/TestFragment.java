package com.example.wordmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.FragmentTestBinding;
import com.example.wordmaster.define.Define;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kotlin.jvm.internal.Ref;

public class TestFragment extends Fragment {
    private FragmentTestBinding mb;
    private int dictMaxCount, rgDictType, rgOption;
    private String dictTitle, dictHostName, limitTime;
    private static String TAG = "TestFragment";
    private int currentIdx = 0;
    private ArrayList<DictionaryWordItem> testList = new ArrayList<>();
    private ArrayList<String> mylist = new ArrayList<>();
    private String[] myArr;
    private String[] answerArr;
    private ArrayList<String> answerList = new ArrayList<>();
    private MainActivity activity;
    private int myScore = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = FragmentTestBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
        Bundle getArgs = getArguments();
        if (getArgs != null) {
            dictTitle = getArgs.getString("tvTestTitle");
            dictHostName = getArgs.getString("tvTestHost");
            dictMaxCount = getArgs.getInt("testMaxCount");
            limitTime = getArgs.getString("testLimitTime");
            rgDictType = getArgs.getInt("rgTestType");
            rgOption = getArgs.getInt("rgTestTimeOption");
        }
        myArr = new String[dictMaxCount];
        answerArr = new String[dictMaxCount];
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
        mb.tvWordTestProgressText.setText("1/" + dictMaxCount);
        mb.tvTestTitle.setText(dictTitle);
        mb.tvTestHostName.setText(dictHostName + "님의 테스트");
        mb.pgWordTestProgress.setMax(dictMaxCount);
        mb.ibPreviousWord.setVisibility(View.INVISIBLE);

        mb.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerArr = answerList.toArray(new String[0]);
                for (int i=0; i<answerArr.length; i++){
                    if (answerArr[i].equals(myArr[i])){
                        myScore+=1;
                    }
                }
                Toast.makeText(getContext(),"너님의 점수는"+myScore+"/"+dictMaxCount,Toast.LENGTH_SHORT).show();
                activity.changeFragment(Define.TEST_RESULT_FRAGMENT);
            }
        });

    }
    public void showWord(int pos) {
        if(pos==0){
            mb.ibPreviousWord.setVisibility(View.INVISIBLE);
        }
        else{
            mb.ibPreviousWord.setVisibility(View.VISIBLE);
        }
        if (pos==dictMaxCount-1){
            mb.ibNextWord.setVisibility(View.INVISIBLE);
            mb.btnSubmit.setVisibility(View.VISIBLE);
        }
        else{
            mb.ibNextWord.setVisibility(View.VISIBLE);
            mb.btnSubmit.setVisibility(View.INVISIBLE);
        }


        DictionaryWordItem item = testList.get(pos);
        String kor = item.getKor();
        String eng = item.getEng();
        mb.tvWordQuestion.setText(eng);

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
                currentIdx+=1;
                mb.tvWordTestProgressText.setText((currentIdx+1)+"/" + dictMaxCount);
                mb.pgWordTestProgress.setProgress(currentIdx+1);
                Log.e(TAG, "onClick: "+currentIdx );
                Toast.makeText(getContext(),currentIdx+"",Toast.LENGTH_SHORT).show();
                showWord(currentIdx);
                mb.etWordAnswer.setText("");


            }
        });

        //이전문제 버튼을 눌렀을때
        mb.ibPreviousWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                currentIdx-=1;
                mb.tvWordTestProgressText.setText((currentIdx+1)+"/" + dictMaxCount);
                mb.pgWordTestProgress.setProgress(currentIdx+1);
                Toast.makeText(getContext(),currentIdx+"",Toast.LENGTH_SHORT).show();
                showWord(currentIdx);
                Log.e(TAG, "onClick: "+mylist );
                mb.etWordAnswer.setText(myArr[currentIdx]);
            }
        });

    }

    public void setFirstItem() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dictHostName);
        myRef.child(dictTitle).child("list").child("0").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String item = snapshot.getValue().toString();
                Log.e(TAG, "onChildAdded: " + item);
                ArrayList<String> arr = new ArrayList<>();
                arr.add(snapshot.getValue().toString());

//                item = item.replaceAll("[}{]","");
//                String[] splitWord = item.split(",");
//                String strEng = splitWord[0].split("=")[1];
//                String strKor = splitWord[1].split("=")[1];
                mb.tvWordQuestion.setText(arr.get(0));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void readDBListItem() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dictHostName);
        myRef.child(dictTitle).child("list").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e(TAG, "onChildAdded: " + snapshot.getValue());
                String item = snapshot.getValue().toString();
                item = item.replaceAll("[}{]", "");
                String[] splitWord = item.split(",");
                String strEng = splitWord[0].split("=")[1];
                String strKor = splitWord[1].split("=")[1];
                answerList.add(strEng);
                testList.add(new DictionaryWordItem(strEng, strKor));
                Log.e(TAG, "onChildChanged: " + strEng);
                showWord(0);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e(TAG, "onChildAdded: " + snapshot.getValue());
                String item = snapshot.getValue().toString();
                item = item.replaceAll("[}{]", "");
                String[] splitWord = item.split(",");
                String strEng = splitWord[0].split("=")[1];
                String strKor = splitWord[1].split("=")[1];
                Log.e(TAG, "onChildChanged: " + strEng);
                testList.add(new DictionaryWordItem(strEng, strKor));
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //showWord(0);
    }


}
