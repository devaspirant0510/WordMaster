package com.example.wordmaster.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.wordmaster.R;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.adapter.TestViewPagerAdapter;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.InfoFragmentDialogCallback;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.ActivityMainBinding;
import com.example.wordmaster.fragment.DictionaryFragment;
import com.example.wordmaster.fragment.DictionaryInfoFragment;
import com.example.wordmaster.fragment.HomeFragment;
import com.example.wordmaster.fragment.MyInfoFragment;
import com.example.wordmaster.fragment.SearchFragment;
import com.example.wordmaster.fragment.TestFragment;
import com.example.wordmaster.fragment.TestResultFragment;
import com.example.wordmaster.fragment.viewpager.MyTestFragment;
import com.example.wordmaster.fragment.viewpager.OnlineTestFragment;
import com.example.wordmaster.Define.Define;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SendDataToActivity {
    private ActivityMainBinding mb;
    private BottomSheetCallBack bottomSheetCallBack;
    private DictionaryFragmentCallBack dictionaryListCallBack;
    private InfoFragmentDialogCallback infoFragmentDialogCallback;
    private static final String TAG = "MainActivity";
    private String dictTitle,dictOption,dictDescription,dictHost,testingLimitTime,testingTitle,testingHost;
    private int dictCount,testingMaxCount,testingRgType,testingRgOption;
    private int testMaxCount,testCurrentCount;
    private String[] myArr;
    private String[] answerArr;
    private ArrayList<DictionaryWordItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mb.getRoot());
        changeFragment(Define.HOME_FRAGMENT);
        init();
    }

    private void init() {
        // 바텀네비게이션뷰 아이템 클릭스 프레그먼트 트랜잭션
        mb.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navi_item_home:
                        changeFragment(Define.HOME_FRAGMENT);
                        break;
                    case R.id.navi_item_my_dict:
                        changeFragment(Define.DICTIONARY_FRAGMENT);
                        break;
                    case R.id.navi_item_test:
                        changeFragment(Define.TEST_FRAGMENT);
                        break;
                    case R.id.navi_item_search:
                        changeFragment(Define.SEARCH_FRAGMENT);
                        break;
                    case R.id.navi_item_profile:
                        changeFragment(Define.MY_INFO_FRAGMENT);
                        break;

                }
                return true;
            }
        });
    }
    public int showFragment(){
        mb.frame.setVisibility(View.VISIBLE);
        mb.testViewPager.setVisibility(View.GONE);
        mb.viewPagerTabLayout.setVisibility(View.GONE);
        return Define.SHOW_FRAGMENT;

    }
    public int showViewPager(){
        mb.frame.setVisibility(View.GONE);
        mb.testViewPager.setVisibility(View.VISIBLE);
        mb.viewPagerTabLayout.setVisibility(View.VISIBLE);
        return Define.SHOW_VIEW_PAGER;

    }
    Fragment fr = null;

    /**
     * Define.java 에서 정의된 상수값 받아서 프레그먼트 체인지
     * @param n 상수값
     */
    public void changeFragment(int n){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int viewState=0;
        switch (n){
            case Define.HOME_FRAGMENT:
                fr = new HomeFragment();
                viewState = showFragment();
                break;
            case Define.DICTIONARY_FRAGMENT:
                fr = new DictionaryFragment();

                viewState = showFragment();
                break;
            case Define.TEST_FRAGMENT:
                viewState = showViewPager();
                TestViewPagerAdapter adapter = new TestViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                MyTestFragment myTestFragment = new MyTestFragment();
                myTestFragment.setListener(this);
                adapter.addItem(myTestFragment);
                adapter.addItem(new OnlineTestFragment());
                mb.testViewPager.setAdapter(adapter);
                break;
            case Define.SEARCH_FRAGMENT:
                fr = new SearchFragment();
                viewState = showFragment();
                break;
            case Define.MY_INFO_FRAGMENT:
                fr = new MyInfoFragment();
                viewState = showFragment();
                break;
            case Define.DICTIONARY_INFO_FRAGMENT:
                fr = new DictionaryInfoFragment();
                viewState = showFragment();
                Log.e(TAG, "changeFragment: "+342 );
                Bundle infoArg = new Bundle();
                infoArg.putString("Title",dictTitle);
                infoArg.putString("Option",dictOption);
                infoArg.putString("Description",dictDescription);
                infoArg.putString("HashTag",dictHost);
                infoArg.putInt("Count",dictCount);
                fr.setArguments(infoArg);
                break;
            case Define.TESTING_FRAGMENT:
                Log.e(TAG,"testing");
                fr = new TestFragment();

                Bundle testingArg = new Bundle();
                testingArg.putInt("testMaxCount",testingMaxCount);
                testingArg.putString("testLimitTime",testingLimitTime);
                testingArg.putInt("rgTestType",testingRgType);
                testingArg.putInt("rgTestTimeOption",testingRgOption);
                testingArg.putString("tvTestTitle",testingTitle);
                testingArg.putString("tvTestHost",testingHost);
                fr.setArguments(testingArg);
                viewState = showFragment();
                break;
            case Define.TEST_RESULT_FRAGMENT:
                fr=new TestResultFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("testMaxCount",testMaxCount);
                bundle.putInt("testCurrentCount",testCurrentCount);
                bundle.putStringArray("myArr",myArr);
                bundle.putStringArray("answerArr",answerArr);
                bundle.putSerializable("list",list);
                fr.setArguments(bundle);
                viewState = showFragment();
                break;
            default:
                break;
        }
        if (viewState == Define.SHOW_FRAGMENT){
            ft.replace(R.id.frame,fr);
            ft.commit();
        }
    }

    public void BottomSheetCallBack(BottomSheetCallBack callBack){
        this.bottomSheetCallBack = callBack;
    }
    public void sendCreateDictDialog(String title,int count,int currentCount,String description,String hashTag,String DictOption){
        bottomSheetCallBack.createDialogGetData(title,count,currentCount,description,hashTag,DictOption);
    }
    public void setDictionaryListCallBack(DictionaryFragmentCallBack callBack){
        this.dictionaryListCallBack = callBack;
    }
    public void setSendTestResult(DictionaryFragmentCallBack callBack){
        this.dictionaryListCallBack = callBack;
    }
    public void sendTestResultCallback(int maxCount, int trueCount, String[] myAnswer,String[] answer){

    }
    public void sendInfoData(String title,String option,int count){
        dictionaryListCallBack.sendInfoData(title,option,count);
    }
    public void setInfoFragmentDialogCallback(InfoFragmentDialogCallback callback){
        this.infoFragmentDialogCallback = callback;
    }
    public void sendInfoData(ArrayList<DictionaryWordItem> list, DictionaryInfoAdapter adapter, String title){
        infoFragmentDialogCallback.send(list,adapter,title);
    }



    @Override
    public void sendDictData(String title, String option, String hashTag, int count) {
        this.dictTitle = title;
        this.dictOption = option;
        this.dictHost = hashTag;
        this.dictCount = count;

    }

    @Override
    public void sendTestingData(int maxCount, String limitTime, int rgTestType, int rgTestTimeOption,String host,String title) {
        this.testingLimitTime = limitTime;
        this.testingMaxCount = maxCount;
        this.testingRgOption = rgTestTimeOption;
        this.testingRgType = rgTestType;
        this.testingTitle = title;
        this.testingHost = host;

    }

    @Override
    public void sendTestResult(int maxCount, int trueCount, String[] myAnswer, String[] answer,ArrayList<DictionaryWordItem> list) {
        Log.e(TAG, "sendTestResult: "+maxCount+"/"+trueCount );
        this.testMaxCount = maxCount;
        this.testCurrentCount = trueCount;
        this.myArr = myAnswer;
        this.answerArr = answer;
        this.list = list;

    }
}