package com.taffyosales.taffyosales.ui.store;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taffyosales.taffyosales.R;
import com.taffyosales.taffyosales.databinding.ActivityAddStoreBinding;
import com.taffyosales.taffyosales.databinding.ActivityUploadSignatureBinding;
import com.taffyosales.taffyosales.model.Store;
import com.taffyosales.taffyosales.ui.home.MainActivity;
import com.taffyosales.taffyosales.viewmodel.StoreViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class uploadSignature extends AppCompatActivity {

    FirebaseAuth mAuth;
    StoreViewModel viewModel;
    private Uri imageUri;

    ActivityUploadSignatureBinding binding;
    Store store = new Store();
    String storeId;

    private static final String TAG = "uploadSignature";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_upload_signature);
        binding = ActivityUploadSignatureBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        Bundle bundle = getIntent().getExtras();
        storeId = bundle.getString("store_id");
        Log.e(TAG, "StoreId is  " + storeId);


        binding.signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(2, 1)
                        .start(uploadSignature.this);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                viewModel.uploadImg(FirebaseAuth.getInstance().getUid(), resultUri, storeId);
                Log.e(TAG, "StoreId is 1 " + storeId);
                Intent intent=new Intent(uploadSignature.this, MainActivity.class);
                startActivity(intent);
                finish();


                imageUri = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

}
