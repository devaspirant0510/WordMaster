package com.example.wordmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.wordmaster.R;
import com.example.wordmaster.adapter.DictionaryInfoAdapter;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.InfoFragmentDialogCallback;
import com.example.wordmaster.data.recycler.DictionaryWordItem;
import com.example.wordmaster.databinding.ActivityMainBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.dialog.bottomsheet.CreateDictionarySheetDialog;
import com.example.wordmaster.dialog.custom.CreateWordDialog;
import com.example.wordmaster.fragment.DictionaryFragment;
import com.example.wordmaster.fragment.DictionaryInfoFragment;
import com.example.wordmaster.fragment.HomeFragment;
import com.example.wordmaster.fragment.MyInfoFragment;
import com.example.wordmaster.fragment.SearchFragment;
import com.example.wordmaster.fragment.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DictionaryFragment.SendDictData {
    private ActivityMainBinding mb;
    private BottomSheetCallBack bottomSheetCallBack;
    private DictionaryFragmentCallBack dictionaryListCallBack;
    private InfoFragmentDialogCallback infoFragmentDialogCallback;
    private static final String TAG = "MainActivity";
    private String dictTitle,dictOption,dictDescription,dictHost;
    private int dictCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = ActivityMainBinding.inflate(getLayoutInflater());
        View root = mb.getRoot();
        setContentView(root);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }

    private void init() {
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
    Fragment fr = null;
    public void changeFragment(int n){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (n){
            case Define.HOME_FRAGMENT:
                fr = new HomeFragment();
                break;
            case Define.DICTIONARY_FRAGMENT:
                fr = new DictionaryFragment();
                break;
            case Define.TEST_FRAGMENT:
                fr = new TestFragment();
                break;
            case Define.SEARCH_FRAGMENT:
                fr = new SearchFragment();
                break;
            case Define.MY_INFO_FRAGMENT:
                fr = new MyInfoFragment();
                break;
            case Define.DICTIONARY_INFO_FRAGMENT:
                fr = new DictionaryInfoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Title",dictTitle);
                bundle.putString("Option",dictOption);
                bundle.putString("Description",dictDescription);
                bundle.putString("HashTag",dictHost);
                bundle.putInt("Count",dictCount);
                fr.setArguments(bundle);

            default:
                break;
        }
        ft.replace(R.id.frame,fr);
        ft.commit();

    }

    public void BottomSheetCallBack(BottomSheetCallBack callBack){
        this.bottomSheetCallBack = callBack;
    }
    public void sendCreateDictDialog(String title,int count,String description,String hashTag,String DictOption){
        bottomSheetCallBack.createDialogGetData(title,count,description,hashTag,DictOption);
    }
    public void setDictionaryListCallBack(DictionaryFragmentCallBack callBack){
        this.dictionaryListCallBack = callBack;
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
    public void sendDictData(String title, String option, String description, String hashTag, int count) {

        dictTitle = title;
        dictOption = option;
        dictDescription = description;
        dictHost = hashTag;
        dictCount = count;
    }
}