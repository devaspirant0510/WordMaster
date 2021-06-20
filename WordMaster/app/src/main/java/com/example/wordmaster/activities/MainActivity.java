package com.example.wordmaster.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.R;
import com.example.wordmaster.adapter.DictionaryViewPageAdapter;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.InfoFragmentDialogCallback;
import com.example.wordmaster.callback.SendDataToActivity;
import com.example.wordmaster.databinding.ActivityMainBinding;
import com.example.wordmaster.fragment.DictionaryInfoFragment;
import com.example.wordmaster.fragment.HomeFragment;
import com.example.wordmaster.fragment.ProfileFragment;
import com.example.wordmaster.fragment.SearchFragment;
import com.example.wordmaster.fragment.SearchInfoFragment;
import com.example.wordmaster.fragment.TestFragment;
import com.example.wordmaster.fragment.TestResultFragment;
import com.example.wordmaster.fragment.viewpager.MyDictionaryFragment;
import com.example.wordmaster.fragment.viewpager.MyTestFragment;
import com.example.wordmaster.fragment.viewpager.OtherDictionaryFragment;
import com.example.wordmaster.model.firebase.UserDictionary;
import com.example.wordmaster.model.recycler.DictionaryListItem;
import com.example.wordmaster.model.recycler.DictionaryWordItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SendDataToActivity {
    private ActivityMainBinding mb;
    private BottomSheetCallBack bottomSheetCallBack;
    private DictionaryFragmentCallBack dictionaryListCallBack;
    private InfoFragmentDialogCallback infoFragmentDialogCallback;
    // 단어장에서 단어장 세부로 이동할때 넘길 객체
    private DictionaryListItem Dict2InfoItem;
    private static final String TAG = "MainActivity";
    private String dictTitle,dictOption,dictDescription,dictHost,testingLimitTime,testingTitle,testingHost;
    private int dictCount,testingMaxCount,testingRgType,testingRgOption;
    private int testMaxCount,testCurrentCount;
    private String[] myArr;
    private Bundle search2SearchInfo;
    private String[] answerArr;
    private ArrayList<DictionaryWordItem> list;
    private Bundle otherDict2Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mb.getRoot());
        setSupportActionBar(mb.toolBar);

        // 화면켰을때 처음화면은 홈화면으로
        changeFragment(Const.HOME_FRAGMENT);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        // 바텀네비게이션뷰 아이템 클릭스 프레그먼트 트랜잭션
        mb.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navi_item_home:
                        changeFragment(Const.HOME_FRAGMENT);
                        break;
                    case R.id.navi_item_my_dict:
                        changeFragment(Const.DICTIONARY_FRAGMENT);
                        break;
                    case R.id.navi_item_test:
                        changeFragment(Const.TEST_FRAGMENT);
                        break;
                    case R.id.navi_item_search:
                        changeFragment(Const.SEARCH_FRAGMENT);
                        break;
                    case R.id.navi_item_profile:
                        changeFragment(Const.MY_INFO_FRAGMENT);
                        break;
                }
                return true;
            }
        });
    }
    // 바텀네비에서 보여줄 화면이 프레그먼트일경우 뷰페어저,탭레이아웃 Gone 처리
    public int showFragment(){
        mb.frame.setVisibility(View.VISIBLE);
        mb.testViewPager.setVisibility(View.GONE);
        mb.viewPagerTabLayout.setVisibility(View.GONE);
        return Const.SHOW_FRAGMENT;

    }
    // 바텀네비에서 보여줄 화면이 뷰페이저일경우 프레그먼트 Gone 처리
    public int showViewPager(){
        mb.frame.setVisibility(View.GONE);
        mb.testViewPager.setVisibility(View.VISIBLE);
        mb.viewPagerTabLayout.setVisibility(View.VISIBLE);
        return Const.SHOW_VIEW_PAGER;

    }
    // 프레그먼트
    private Fragment fr = null;
    /**
     * Define.java 에서 정의된 상수값 받아서 프레그먼트 체인지
     * @param n 상수값
     */
    public void changeFragment(int n){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int viewState=0;

        switch (n){
            // 홈화면일때 HomeFragment() 보여주고 viewState 는 프레그먼트
            case Const.HOME_FRAGMENT:
                fr = new HomeFragment();
                viewState = showFragment();
                fm.popBackStack();
                break;
            // 사전 화면일때
            case Const.DICTIONARY_FRAGMENT:
                //fr = new DictionaryFragment();
                viewState = showViewPager();
                fm.popBackStack();
                DictionaryViewPageAdapter dictionaryViewPageAdapter = new DictionaryViewPageAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
                dictionaryViewPageAdapter.addItem(new MyDictionaryFragment());
                dictionaryViewPageAdapter.addItem(new OtherDictionaryFragment());
                mb.testViewPager.setAdapter(dictionaryViewPageAdapter);

                break;
            case Const.TEST_FRAGMENT:
                MyTestFragment myTestFragment = new MyTestFragment();
                myTestFragment.setListener(this);
                fm.popBackStack();
                fr = myTestFragment;
                viewState = showFragment();
                break;
            case Const.SEARCH_FRAGMENT:
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setOnSendToActivityListener(this);
                fm.popBackStack();
                fr = searchFragment;
                viewState = showFragment();
                break;
            case Const.MY_INFO_FRAGMENT:
                fr = new ProfileFragment();
                fm.popBackStack();
                viewState = showFragment();
                break;
            case Const.DICTIONARY_INFO_FRAGMENT:
                DictionaryInfoFragment infoFragment = new DictionaryInfoFragment();
                infoFragment.setSendDataToActivity(this);
                fr = infoFragment;
                fm.popBackStack();
                viewState = showFragment();
                Bundle infoArg = new Bundle();
                infoArg.putString("Title",Dict2InfoItem.getDictionaryTitle());
                infoArg.putString("Option",Dict2InfoItem.getDictOption());
                infoArg.putString("Description",Dict2InfoItem.getDictionaryDescription());
                infoArg.putStringArray("HashTag",Dict2InfoItem.getDictHashTag().toArray(new String[0]));
                infoArg.putString("RoomKey",Dict2InfoItem.getDictRoomKey());
                infoArg.putInt("Count",Integer.parseInt(Dict2InfoItem.getDictionaryMaxCount()));
                fr.setArguments(infoArg);
                break;
            case Const.TESTING_FRAGMENT:
                Log.e(TAG,"testing");
                fr = new TestFragment();
                fm.popBackStack();
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
            case Const.TEST_RESULT_FRAGMENT:
                fr=new TestResultFragment();
                fm.popBackStack();
                Bundle bundle = new Bundle();
                bundle.putInt("testMaxCount",testMaxCount);
                bundle.putInt("testCurrentCount",testCurrentCount);
                bundle.putStringArray("myArr",myArr);
                bundle.putStringArray("answerArr",answerArr);
                bundle.putSerializable("list",list);
                fr.setArguments(bundle);
                viewState = showFragment();
                break;
            case Const.TEST_MY_FRAGMENT:
                fr = new TestFragment();
                fm.popBackStack();
                Bundle myTestArgs = new Bundle();
                myTestArgs.putString("userId",myTestUserid);
                myTestArgs.putString("roomKey",myTestRoomKey);
                myTestArgs.putInt("testOption",myTestRgTestType);
                myTestArgs.putInt("maxCount",myTestMaxCount);
                myTestArgs.putString("title",myTestTitle);
                myTestArgs.putString("host",myTestHost);
                fr.setArguments(myTestArgs);
                viewState = showFragment();
                break;
            case Const.SEARCH_INFO_FRAGMENT:
                SearchInfoFragment searchInfoFragment = new SearchInfoFragment(search2SearchInfo.getString("id"));
                searchInfoFragment.setArguments(search2SearchInfo);
                fm.popBackStack();
                fr =searchInfoFragment;
                viewState = showFragment();
                break;
            case Const.OTHER_DICT2DICTIONARY_INFO:
                DictionaryInfoFragment dictionaryInfoFragment = new DictionaryInfoFragment(true);
                fm.popBackStack();
                fr = dictionaryInfoFragment;
                dictionaryInfoFragment.setArguments(otherDict2Info);
                viewState = showFragment();
                break;


            default:
                break;
        }
        if (viewState == Const.SHOW_FRAGMENT){
            ft.replace(R.id.frame,fr);
            ft.commit();
        }
    }

    public void BottomSheetCallBack(BottomSheetCallBack callBack){
        this.bottomSheetCallBack = callBack;
    }
    public void sendCreateDictDialog(UserDictionary userDictionary){
        bottomSheetCallBack.createDialogGetData(userDictionary);
    }
    public void setDictionaryListCallBack(DictionaryFragmentCallBack callBack){
        this.dictionaryListCallBack = callBack;
    }
    @Override
    public void sendDictData(DictionaryListItem item) {
        this.Dict2InfoItem = item;
    }

    @Override
    public void sendTestingData(int pos, int maxCount, String limitTime, int rgTestType, int rgTestTimeOption, String host, String title) {

        this.testingLimitTime = limitTime;
        this.testingMaxCount = maxCount;
        this.testingRgOption = rgTestTimeOption;
        this.testingRgType = rgTestType;
        this.testingTitle = title;
        this.testingHost = host;
    }

    private String myTestUserid,myTestRoomKey,myTestHost,myTestTitle;
    private int myTestMaxCount,myTestRgTestType;
    @Override
    public void sendTestingData(String userid, String roomKey, int maxCount, int rgTestType, String host, String title) {
        this.myTestUserid = userid;
        this.myTestRoomKey = roomKey;
        this.myTestMaxCount = maxCount;
        this.myTestRgTestType = rgTestType;
        this.myTestHost = host;
        this.myTestTitle = title;

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

    @Override
    public void sendSearchInfoData(Bundle bundle) {
        Log.e(TAG, "sendSearchInfoData: "+bundle );
        Log.e(TAG, "sendSearchInfoData: "+bundle.get("title") );
        this.search2SearchInfo = bundle;

    }

    @Override
    public void otherDict2Info(String title, String option, int maxCount, String roomKey, String userId, String userName) {
        otherDict2Info = new Bundle();
        otherDict2Info.putString("title",title);
        otherDict2Info.putString("option",option);
        otherDict2Info.putInt("maxCount",maxCount);
        otherDict2Info.putString("roomKey",roomKey);
        otherDict2Info.putString("userId",userId);
        otherDict2Info.putString("userName",userName);


    }
}