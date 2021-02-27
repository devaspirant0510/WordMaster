package com.example.wordmaster.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wordmaster.R;
import com.example.wordmaster.databinding.ActivityMainBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.fragment.DictionaryFragment;
import com.example.wordmaster.fragment.HomeFragment;
import com.example.wordmaster.fragment.MyInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = ActivityMainBinding.inflate(getLayoutInflater());
        View root = mb.getRoot();
        setContentView(root);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame,new DictionaryFragment()).commit();

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
    public void changeFragment(int n){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fr = null;
        switch (n){
            case 0:
                Toast.makeText(getApplicationContext(),"df",Toast.LENGTH_SHORT).show();
                ft.replace(R.id.frame,new HomeFragment()).commit();
                break;
            case 1:
                ft.replace(R.id.frame,new DictionaryFragment()).commit();
                break;
            case 2:
                ft.replace(R.id.frame,new MyInfoFragment()).commit();
                break;
            default:
                break;
        }
    }
}