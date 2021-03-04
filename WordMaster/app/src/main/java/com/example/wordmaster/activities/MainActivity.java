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
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.databinding.ActivityMainBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.fragment.DictionaryFragment;
import com.example.wordmaster.fragment.DictionaryInfoFragment;
import com.example.wordmaster.fragment.HomeFragment;
import com.example.wordmaster.fragment.MyInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements DictionaryFragment.SendDictData {
    private ActivityMainBinding mb;
    private BottomSheetCallBack bottomSheetCallBack;
    private DictionaryFragmentCallBack dictionaryListCallBack;
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

    private void init() {
        mb.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navi_item_home:
                        changeFragment(Define.HOME_FRAGMENT);
                        break;
                    case R.id.navi_item_my_dict:
                        //Toast.makeText(getApplicationContext(),"d",Toast.LENGTH_SHORT).show();
                        changeFragment(Define.DICTIONARY_FRAGMENT);
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
            case 0:
                fr = new HomeFragment();
                break;
            case 1:
                fr = new DictionaryFragment();
                break;
            case 2:
                fr = new MyInfoFragment();
                break;
            case 3:
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


    @Override
    public void sendDictData(String title, String option, String description, String hashTag, int count) {

        dictTitle = title;
        dictOption = option;
        dictDescription = description;
        dictHost = hashTag;
        dictCount = count;
    }
}