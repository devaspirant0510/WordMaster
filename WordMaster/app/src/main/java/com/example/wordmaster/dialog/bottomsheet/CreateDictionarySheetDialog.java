package com.example.wordmaster.dialog.bottomsheet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wordmaster.Define.Const;
import com.example.wordmaster.Define.SharedManger;
import com.example.wordmaster.R;
import com.example.wordmaster.activities.MainActivity;
import com.example.wordmaster.databinding.DialogBottomSheetCreateDictBinding;
import com.example.wordmaster.model.firebase.UserDictionary;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

public class CreateDictionarySheetDialog extends BottomSheetDialogFragment {
    private DialogBottomSheetCreateDictBinding mb;
    private static final String TAG = "CreateDictionarySheetDialog";
    private MainActivity activity;
    private String hasTag = "";


    public CreateDictionarySheetDialog(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogBottomSheetCreateDictBinding.inflate(getLayoutInflater());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = mb.getRoot();
        init();
        return root;
    }
    private void dialogUpdateSetItem(){

    }

    private int radioGroupType = 0;
    private void init() {
        // 라디오 그룹 콜백
        mb.createDictOpenOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.test_public) {
                    radioGroupType = Const.PUBLIC;
                    mb.llPassword.setVisibility(View.GONE);
                    mb.tvLlPassword.setText("");
                }else if (checkedId == R.id.test_private){
                    radioGroupType = Const.PRIVATE;
                    mb.llPassword.setVisibility(View.VISIBLE);
                }else{
                    radioGroupType = Const.ONLY_ME;
                    mb.llPassword.setVisibility(View.GONE);
                    mb.tvLlPassword.setText("");

                }
            }
        });
        mb.btnDictCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int createDictCount = 0;
                createDictCount = Integer.parseInt(mb.createDictCount.getText().toString());
                String createDictTitle = mb.createDictTitle.getText().toString();
                String createDictDescription = mb.createDictDescription.getText().toString();
                String createDictHashTag = mb.createDictTag.getText().toString();
                String createDictPassword = mb.tvLlPassword.getText().toString();


                String DictOption = changeDictOption(radioGroupType);
                UserDictionary item = new UserDictionary();
                hasTag="";
                for (int i = 0; i < mb.cgHashTag.getChildCount()-1; i++) {
                    Chip chip = (Chip)mb.cgHashTag.getChildAt(i);
                    hasTag+=chip.getText().toString()+",";


                }
                item.init(DictOption,createDictTitle,createDictCount,
                        0,createDictDescription,hasTag,null,
                        SharedManger.loadData(Const.SHARED_USER_NAME,""),null,
                        createDictPassword,"",SharedManger.loadData(Const.SHARED_USER_ID,""));
                activity.sendCreateDictDialog(item);
                dismiss();
            }
        });
        mb.createDictTag.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    Chip chip = new Chip(getContext());
                    chip.setText(mb.createDictTag.getText().toString());
                    chip.setCloseIconVisible(true);
                    mb.cgHashTag.addView(chip,mb.cgHashTag.getChildCount()-1);

                    mb.createDictTag.setText("");
                    chip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mb.cgHashTag.removeView(chip);
                        }
                    });
                }

            }
        });
        mb.createDictTag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    if (s.charAt(s.length() - 1) == ' ' && s.length() > 1) {
                        Log.e(TAG, "afterTextChanged: "+s.charAt(s.length()-1) );
                        Chip chip = new Chip(getContext());
                        chip.setCloseIconVisible(true);
                        chip.setText(s);
                        mb.cgHashTag.addView(chip,mb.cgHashTag.getChildCount()-1);
                        mb.createDictTag.setText("");
                        chip.setOnCloseIconClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mb.cgHashTag.removeView(chip);
                            }
                        });

                    }

                }catch (Exception e){

                }

            }
        });
    }

    public String changeDictOption(int n) {
        switch (n) {
            case 1:
                return "공개";
            case 2:
                return "비공개";
            case 3:
                return "나만보기";
            default:
                return "오류";
        }
    }

}
