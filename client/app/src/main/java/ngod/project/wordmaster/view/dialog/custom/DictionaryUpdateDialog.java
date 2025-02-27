package ngod.project.wordmaster.view.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import ngod.project.wordmaster.Define.Const;
import ngod.project.wordmaster.callback.DialogUpdateCallback;
import ngod.project.wordmaster.databinding.DialogUpdateDictBinding;

public class DictionaryUpdateDialog extends Dialog {
    private DialogUpdateDictBinding mb;
    private DialogUpdateCallback dialogUpdateCallback = null;
    private int type;
    public DictionaryUpdateDialog(@NonNull Context context,int type) {
        super(context);
        this.type = type;
    }
    public void setDialogUpdateCallback(DialogUpdateCallback callback){
        this.dialogUpdateCallback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mb = DialogUpdateDictBinding.inflate(getLayoutInflater());
        setContentView(mb.getRoot());
        init();
    }

    private void init() {

        mb.updateDictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateCallback.setOnClickUpdateButton();
                dismiss();
            }
        });
        mb.deleteDictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("fds","sdaf");
                dialogUpdateCallback.setOnClickDeleteButton();
                dismiss();

            }
        });
    }

}
