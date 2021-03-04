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

import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.adapter.DictionaryListAdapter;
import com.example.wordmaster.callback.BottomSheetCallBack;
import com.example.wordmaster.callback.DictionaryFragmentCallBack;
import com.example.wordmaster.callback.DictionaryListCallBack;
import com.example.wordmaster.data.recycler.DictionaryListItem;
import com.example.wordmaster.databinding.FragmentDictionaryBinding;
import com.example.wordmaster.define.Define;
import com.example.wordmaster.dialog.bottomsheet.CreateDictionarySheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DictionaryFragment extends Fragment implements BottomSheetCallBack,DictionaryFragmentCallBack {
    private FragmentDictionaryBinding mb;
    private static final String TAG = "DictionaryFragment";
    private CreateDictionarySheetDialog dialog;
    private MainActivity activity;
    private DictionaryListAdapter adapter;
    private SendDictData sendDictData = null;

    public interface SendDictData {
        void sendDictData(String title,String option,String description,String hashTag,int count);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)getActivity();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SendDictData){
            sendDictData = (SendDictData)context;

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity.BottomSheetCallBack(this);
        activity.setDictionaryListCallBack(this);
        mb = FragmentDictionaryBinding.inflate(getLayoutInflater());
        init();

        View root = mb.getRoot();
        return root;

    }

    private void firebaseDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(Define.USER);

    }

    private void init() {
        adapter = new DictionaryListAdapter(getContext());
        Log.e(TAG, "init: "+adapter.dictList);
        mb.dictionaryList.setAdapter(adapter);
        //추가버튼 눌렀을때
        mb.createDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog = new CreateDictionarySheetDialog(activity);
                if (getFragmentManager()!=null){
                    activity.sendInfoData("딥러닝논문 AlexNet 단어정리","public",150);
                    dialog.show(getFragmentManager(),"dd");
                }
            }
        });
        // 리사이클러뷰의 아이템을 클릭했을때
        adapter.setDictionaryListCallBack(new DictionaryListCallBack() {
            @Override
            public void onClick(View v, int pos) {
                // 해당 단어장의 세부정보 보여주는 화면으로 전환
                DictionaryListItem item = adapter.getItem(pos);
                Toast.makeText(getContext(),item.getDictionaryTitle(),Toast.LENGTH_SHORT).show();
                sendDictData.sendDictData(item.getDictionaryTitle(),item.getDictOption(),item.getDictionaryDescription(),item.getDictionaryHost(),Integer.parseInt(item.getDictionaryMaxCount()));
                activity.changeFragment(Define.DICTIONARY_INFO_FRAGMENT);
            }
        });
    }


    @Override
    public void createDialogGetData(String title, int count, String description, String hashTag,String DictOption) {
        Log.e(TAG,title);
        adapter.addItem(new DictionaryListItem(title,String.valueOf(count),description,hashTag,DictOption,1));
        adapter.notifyDataSetChanged();

    }


    @Override
    public void sendInfoData(String title, String option, int count) {

    }
}
