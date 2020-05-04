package com.taffyosales.taffyosales.ui.store;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.taffyosales.taffyosales.R;
import com.taffyosales.taffyosales.databinding.ActivityAddStoreBinding;
import com.taffyosales.taffyosales.databinding.ActivityMainBinding;
import com.taffyosales.taffyosales.model.Store;
import com.taffyosales.taffyosales.ui.auth.RegisterActivity;
import com.taffyosales.taffyosales.ui.home.MainActivity;
import com.taffyosales.taffyosales.viewmodel.StoreViewModel;
import com.taffyosales.taffyosales.viewmodel.UserViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Add_Store extends AppCompatActivity {


    FirebaseAuth mAuth;
    StoreViewModel viewModel;
    private Uri imageUri;

    private static final String TAG = "Add_Store";
    ActivityAddStoreBinding binding;
    Store store = new Store();
    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add__store);

        binding = ActivityAddStoreBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();

        storeId = FirebaseFirestore.getInstance().collection("stores").document(mAuth.getUid()).collection("store").document().getId();
        viewModel = new ViewModelProvider(this).get(StoreViewModel.class);

        store = new Store();
        Log.e("Stores ", "StoreId is1  " + storeId);
        Log.e(TAG, "onCreate: " + imageUri);


        binding.addShop.setOnClickListener(v -> {

            String shopName = binding.shopName.getEditText().getText().toString();
            String ownerName = binding.ownerName.getEditText().getText().toString();
            String areaName = binding.areaName.getEditText().getText().toString();
            String cityName = binding.cityName.getEditText().getText().toString();
            String address = binding.address.getEditText().getText().toString();
            String pancard = binding.pancard.getEditText().getText().toString();
            String gstno = binding.gstno.getEditText().getText().toString();
            String contactNo = binding.contactNo.getEditText().getText().toString();


            if (shopName.isEmpty()) {
                binding.shopName.setError("Provide your shopName!");
                binding.shopName.requestFocus();
            } else if (ownerName.isEmpty()) {
                binding.ownerName.setError("Provide your ownerName first!");
                binding.ownerName.requestFocus();
            } else if (areaName.isEmpty()) {
                binding.areaName.setError("Provide your areaName first!");
                binding.areaName.requestFocus();
            } else if (cityName.isEmpty()) {
                binding.cityName.setError("Provide your cityName first!");
                binding.cityName.requestFocus();
            } else if (address.isEmpty()) {
                binding.address.setError("Provide your address first!");
                binding.address.requestFocus();
            } else if (pancard.isEmpty()) {
                binding.pancard.setError("Provide your pancard first!");
                binding.pancard.requestFocus();
            } else if (gstno.isEmpty()) {
                binding.gstno.setError("Provide your gstno first!");
                binding.gstno.requestFocus();
            } else if (contactNo.isEmpty()) {
                binding.contactNo.setError("Provide your contactNo first!");
                binding.contactNo.requestFocus();
            } else if (shopName.isEmpty() && ownerName.isEmpty() && areaName.isEmpty() && areaName.isEmpty() && cityName.isEmpty() && address.isEmpty() && pancard.isEmpty() && gstno.isEmpty() && contactNo.isEmpty()) {
                Toast.makeText(Add_Store.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
            } else if (!shopName.isEmpty() && !ownerName.isEmpty() && !areaName.isEmpty() && !areaName.isEmpty() && !cityName.isEmpty() && !address.isEmpty() && !pancard.isEmpty() && !gstno.isEmpty() && !contactNo.isEmpty()) {

                store.setStore_id(storeId);
                store.setShopname(shopName);
                store.setOwner_name(ownerName);
                store.setArea(areaName);
                store.setAddress(address);
                store.setCity_name(cityName);
                store.setPancard(pancard);
                store.setUser_id(mAuth.getUid());
                store.setEmail_id(mAuth.getCurrentUser().getEmail());
                store.setGst_no(gstno);
                store.setContact_no(contactNo);
                viewModel.setShopDetails(mAuth.getUid(), store);


                Intent intent=new Intent(Add_Store.this,uploadSignature.class);
                intent.putExtra("store_id",storeId);
                startActivity(intent);

            }

        });


    }


}
