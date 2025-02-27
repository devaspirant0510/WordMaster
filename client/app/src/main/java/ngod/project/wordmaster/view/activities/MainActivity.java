package ngod.project.wordmaster.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import ngod.project.wordmaster.BuildConfig;
import ngod.project.wordmaster.Define.Const;
import ngod.project.wordmaster.R;
import ngod.project.wordmaster.adapter.DictionaryViewPageAdapter;
import ngod.project.wordmaster.callback.BottomSheetCallBack;
import ngod.project.wordmaster.callback.DictionaryFragmentCallBack;
import ngod.project.wordmaster.callback.InfoFragmentDialogCallback;
import ngod.project.wordmaster.callback.SendDataToActivity;
import ngod.project.wordmaster.databinding.ActivityMainBinding;
import ngod.project.wordmaster.model.firebase.UserDictionary;
import ngod.project.wordmaster.model.recycler.DictionaryListItem;
import ngod.project.wordmaster.model.recycler.DictionaryWordItem;
import ngod.project.wordmaster.model.recycler.OnlineTestItem;
import ngod.project.wordmaster.view.fragment.DictionaryInfoFragment;
import ngod.project.wordmaster.view.fragment.HomeFragment;
import ngod.project.wordmaster.view.fragment.MyTestFragment;
import ngod.project.wordmaster.view.fragment.ProfileFragment;
import ngod.project.wordmaster.view.fragment.SearchFragment;
import ngod.project.wordmaster.view.fragment.SearchInfoFragment;
import ngod.project.wordmaster.view.fragment.TestFragment;
import ngod.project.wordmaster.view.fragment.TestResultFragment;
import ngod.project.wordmaster.view.fragment.TestWaitingInfoFragment;
import ngod.project.wordmaster.view.fragment.TestWaitingRoomFragment;
import ngod.project.wordmaster.view.fragment.viewpager.MyDictionaryFragment;
import ngod.project.wordmaster.view.fragment.viewpager.OtherDictionaryFragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SendDataToActivity {
    private ActivityMainBinding mb;
    private BottomSheetCallBack bottomSheetCallBack;
    private DictionaryFragmentCallBack dictionaryListCallBack;
    private InfoFragmentDialogCallback infoFragmentDialogCallback;
    private OnlineTestItem onlineTest2Join, onlineTest2Info;
    // 단어장에서 단어장 세부로 이동할때 넘길 객체
    private DictionaryListItem Dict2InfoItem;
    private static final String TAG = "MainActivity";
    private String dictTitle, dictOption, dictDescription, dictHost, testingLimitTime, testingTitle, testingHost;
    private int dictCount, testingMaxCount, testingRgType, testingRgOption;
    private int testMaxCount, testCurrentCount;
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
        AdRequest adRequest = new AdRequest.Builder().build();
        mb.adView.loadAd(adRequest);
        changeFragment(Const.HOME_FRAGMENT);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }


    private void init() {
        // 바텀네비게이션뷰 아이템 클릭스 프레그먼트 트랜잭션
        mb.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navi_item_home) {
                    changeFragment(Const.HOME_FRAGMENT);
                } else if (itemId == R.id.navi_item_my_dict) {
                    changeFragment(Const.DICTIONARY_FRAGMENT);
                } else if (itemId == R.id.navi_item_test) {
                    changeFragment(Const.TEST_FRAGMENT);
                } else if (itemId == R.id.navi_item_search) {
                    changeFragment(Const.SEARCH_FRAGMENT);
                } else if (itemId == R.id.navi_item_profile) {
                    changeFragment(Const.MY_INFO_FRAGMENT);
                }
                return true;
            }
        });

    }

    // 바텀네비에서 보여줄 화면이 프레그먼트일경우 뷰페어저,탭레이아웃 Gone 처리
    public int showFragment() {
        mb.frame.setVisibility(View.VISIBLE);
        mb.testViewPager.setVisibility(View.GONE);
        mb.viewPagerTabLayout.setVisibility(View.GONE);
        return Const.SHOW_FRAGMENT;

    }

    // 바텀네비에서 보여줄 화면이 뷰페이저일경우 프레그먼트 Gone 처리
    public int showViewPager() {
        mb.frame.setVisibility(View.GONE);
        mb.testViewPager.setVisibility(View.VISIBLE);
        mb.viewPagerTabLayout.setVisibility(View.VISIBLE);
        return Const.SHOW_VIEW_PAGER;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            changeFragment(Const.DICTIONARY_FRAGMENT); // 원하는 프래그먼트 번호 넣기
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 프레그먼트
    private Fragment fr = null;

    /**
     * Define.java 에서 정의된 상수값 받아서 프레그먼트 체인지
     *
     * @param n 상수값
     */
    public void changeFragment(int n) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        int viewState = 0;

        if (n == Const.HOME_FRAGMENT) {
            fr = new HomeFragment();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            viewState = showFragment();
            fm.popBackStack();
        } else if (n == Const.DICTIONARY_FRAGMENT) {
            viewState = showViewPager();
            fm.popBackStack();
            DictionaryViewPageAdapter dictionaryViewPageAdapter = new DictionaryViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            dictionaryViewPageAdapter.addItem(new MyDictionaryFragment());
            mb.testViewPager.setAdapter(dictionaryViewPageAdapter);
        } else if (n == Const.TEST_FRAGMENT) {
            MyTestFragment myTestFragment = new MyTestFragment();
            myTestFragment.setListener(this);
            fm.popBackStack();
            fr = myTestFragment;
            viewState = showFragment();
        } else if (n == Const.SEARCH_FRAGMENT) {
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setOnSendToActivityListener(this);
            fm.popBackStack();
            fr = searchFragment;
            viewState = showFragment();
        } else if (n == Const.MY_INFO_FRAGMENT) {
            fr = new ProfileFragment();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            fm.popBackStack();
            viewState = showFragment();
        } else if (n == Const.DICTIONARY_INFO_FRAGMENT) {
            DictionaryInfoFragment infoFragment = new DictionaryInfoFragment();
            infoFragment.setSendDataToActivity(this);
            Log.e(TAG, "changeFragment: DICTIONARY_INFO_FRAGMENT");
            fr = infoFragment;
            fm.popBackStack();
            viewState = showFragment();
            Bundle infoArg = new Bundle();
            infoArg.putString("Title", Dict2InfoItem.getDictionaryTitle());
            infoArg.putString("Option", Dict2InfoItem.getDictOption());
            infoArg.putString("Description", Dict2InfoItem.getDictionaryDescription());
//            infoArg.putStringArray("HashTag", Dict2InfoItem.getDictHashTag().toArray(new String[0]));
            infoArg.putString("RoomKey", Dict2InfoItem.getDictRoomKey());
            infoArg.putInt("Count", Integer.parseInt(Dict2InfoItem.getDictionaryMaxCount()));
            fr.setArguments(infoArg);
        } else if (n == Const.TESTING_FRAGMENT) {
            Log.e(TAG, "testing");
            fr = new TestFragment();
            fm.popBackStack();
            Bundle testingArg = new Bundle();
            testingArg.putInt("testMaxCount", testingMaxCount);
            testingArg.putString("testLimitTime", testingLimitTime);
            testingArg.putInt("rgTestType", testingRgType);
            testingArg.putInt("rgTestTimeOption", testingRgOption);
            testingArg.putString("tvTestTitle", testingTitle);
            testingArg.putString("tvTestHost", testingHost);
            fr.setArguments(testingArg);
            viewState = showFragment();
        } else if (n == Const.TEST_RESULT_FRAGMENT) {
            fr = new TestResultFragment();
            fm.popBackStack();
            Bundle bundle = new Bundle();
            bundle.putInt("testMaxCount", testMaxCount);
            bundle.putInt("testCurrentCount", testCurrentCount);
            bundle.putStringArray("myArr", myArr);
            bundle.putStringArray("answerArr", answerArr);
            bundle.putSerializable("list", list);
            fr.setArguments(bundle);
            viewState = showFragment();
        } else if (n == Const.TEST_MY_FRAGMENT) {
            fr = new TestFragment();
            fm.popBackStack();
            Bundle myTestArgs = new Bundle();
            myTestArgs.putString("userId", myTestUserid);
            myTestArgs.putString("roomKey", myTestRoomKey);
            myTestArgs.putInt("testOption", myTestRgTestType);
            myTestArgs.putInt("maxCount", myTestMaxCount);
            myTestArgs.putString("title", myTestTitle);
            myTestArgs.putString("host", myTestHost);
            fr.setArguments(myTestArgs);
            viewState = showFragment();
        } else if (n == Const.SEARCH_INFO_FRAGMENT) {
            SearchInfoFragment searchInfoFragment = new SearchInfoFragment(search2SearchInfo.getString("id"));
            searchInfoFragment.setArguments(search2SearchInfo);
            fm.popBackStack();
            fr = searchInfoFragment;
            viewState = showFragment();
        } else if (n == Const.OTHER_DICT2DICTIONARY_INFO) {
            DictionaryInfoFragment dictionaryInfoFragment = new DictionaryInfoFragment(true);
            fm.popBackStack();
            fr = dictionaryInfoFragment;
            Bundle dict2info = new Bundle();
            dict2info.putString("userId", this.Dict2InfoItem.getUserId());
            dict2info.putString("title", this.Dict2InfoItem.getDictionaryTitle());
            dict2info.putString("option", this.Dict2InfoItem.getDictOption());
            dict2info.putString("roomKey", this.Dict2InfoItem.getDictRoomKey());
            dict2info.putInt("maxCount", Integer.parseInt(this.Dict2InfoItem.getDictionaryMaxCount()));
            dictionaryInfoFragment.setArguments(dict2info);
            viewState = showFragment();
        } else if (n == Const.TEST_WAITING_ROOM) {
            TestWaitingRoomFragment testWaitingRoomFragment = new TestWaitingRoomFragment();
            testWaitingRoomFragment.setOnWaitingRoomListener(this);
            Bundle args = new Bundle();
            fr = testWaitingRoomFragment;
            args.putSerializable("item", onlineTest2Join);
            testWaitingRoomFragment.setArguments(args);
            viewState = showFragment();
        } else if (n == Const.TEST_WAITING_INFO) {
            TestWaitingInfoFragment testWaitingInfoFragment = new TestWaitingInfoFragment();
            testWaitingInfoFragment.setListener(this);
            Bundle test2info = new Bundle();
            test2info.putSerializable("item", onlineTest2Info);
            testWaitingInfoFragment.setArguments(test2info);
            fr = testWaitingInfoFragment;
            viewState = showFragment();
        }

        if (viewState == Const.SHOW_FRAGMENT) {
            ft.replace(R.id.frame, fr);
            ft.addToBackStack(null);
            ft.commit();
        }
    }


    public void BottomSheetCallBack(BottomSheetCallBack callBack) {
        this.bottomSheetCallBack = callBack;
    }

    public void sendCreateDictDialog(UserDictionary userDictionary) {
        bottomSheetCallBack.createDialogGetData(userDictionary);
    }

    public void setDictionaryListCallBack(DictionaryFragmentCallBack callBack) {
        this.dictionaryListCallBack = callBack;
    }

    @Override
    public void sendDictData(DictionaryListItem item) {
        this.Dict2InfoItem = item;
    }


    private String myTestUserid, myTestRoomKey, myTestHost, myTestTitle;
    private int myTestMaxCount, myTestRgTestType;

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
    public void sendTestResult(int maxCount, int trueCount, String[] myAnswer, String[] answer, ArrayList<DictionaryWordItem> list) {
        Log.e(TAG, "sendTestResult: " + maxCount + "/" + trueCount);
        this.testMaxCount = maxCount;
        this.testCurrentCount = trueCount;
        this.myArr = myAnswer;
        this.answerArr = answer;
        this.list = list;

    }

    @Override
    public void sendSearchInfoData(Bundle bundle) {
        Log.e(TAG, "sendSearchInfoData: " + bundle);
        Log.e(TAG, "sendSearchInfoData: " + bundle.get("title"));
        this.search2SearchInfo = bundle;

    }

    @Override
    public void otherDict2Info(String title, String option, int maxCount, String roomKey, String userId, String userName) {
        otherDict2Info = new Bundle();
        Log.e(TAG, "otherDict2Info: " + title + " " + userId);
        otherDict2Info.putString("title", title);
        otherDict2Info.putString("option", option);
        otherDict2Info.putInt("maxCount", maxCount);
        otherDict2Info.putString("roomKey", roomKey);
        otherDict2Info.putString("userId", userId);
        otherDict2Info.putString("userName", userName);


    }

    @Override
    public void onlineTest2testJoin(OnlineTestItem item) {
        this.onlineTest2Join = item;
        Log.e(TAG, "onlineTest2testJoin: " + item.getDescription());
    }

    @Override
    public void onlineTest2testInfo(OnlineTestItem item) {
        this.onlineTest2Info = item;
    }
}