package ngod.project.wordmaster.view.dialog.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import ngod.project.wordmaster.databinding.DialogImageSelectionBinding;

/**
 * @author : seungHo
 * @since : 2021-09-20
 * class : ImageSelectDialog.java
 * github : devaspirant0510
 * email : seungho020510@gmail.com
 * description :
 */
public class ImageSelectDialog extends Dialog {
    private static final String TAG = ImageSelectDialog.class.getSimpleName();
    private DialogImageSelectionBinding mBinding;
    private Context mContext;

    public ImageSelectDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DialogImageSelectionBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        init();
        event();
    }

    private void init() {}

    private void event() {
        // 갤러리에서 이미지 선택
        mBinding.tvGallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncher.launch(intent);
        });

        // 카메라 촬영
        mBinding.tvSelectCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        });
    }

    // 갤러리에서 이미지 선택
    private final ActivityResultLauncher<Intent> galleryLauncher = ((androidx.fragment.app.FragmentActivity) mContext)
            .registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        Toast.makeText(mContext, "갤러리 선택됨: " + selectedImageUri, Toast.LENGTH_SHORT).show();
                    }
                }
            });

    // 카메라 촬영
    private final ActivityResultLauncher<Intent> cameraLauncher = ((androidx.fragment.app.FragmentActivity) mContext)
            .registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == android.app.Activity.RESULT_OK && result.getData() != null) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        Toast.makeText(mContext, "카메라 촬영 완료", Toast.LENGTH_SHORT).show();
                    }
                }
            });
}
