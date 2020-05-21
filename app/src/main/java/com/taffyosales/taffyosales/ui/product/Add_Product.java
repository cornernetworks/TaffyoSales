package com.taffyosales.taffyosales.ui.product;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.taffyosales.taffyosales.R;
import com.taffyosales.taffyosales.databinding.ActivityAddProductBinding;
import com.taffyosales.taffyosales.databinding.ActivityMainBinding;
import com.taffyosales.taffyosales.model.Product;
import com.taffyosales.taffyosales.ui.home.MainActivity;
import com.taffyosales.taffyosales.ui.store.Add_Store;
import com.taffyosales.taffyosales.ui.store.uploadSignature;
import com.taffyosales.taffyosales.viewmodel.ProductViewModel;
import com.taffyosales.taffyosales.viewmodel.StoreViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Add_Product extends AppCompatActivity {

    ActivityAddProductBinding binding;
    private static final String TAG = "Add_Product";
    private Uri imageUri;
    private String productType;
    FirebaseAuth mAuth;
    ProductViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add__product);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        imageUri = null;

        productType = null;


        mAuth = FirebaseAuth.getInstance();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.product_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.productType.setAdapter(adapter);


        binding.productType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                Log.e(TAG, "onItemSelected: " + item);

                if (!item.equals("Select_Product_type")) {
                    productType = item;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(Add_Product.this);
            }
        });

        binding.updateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null) {
                    Log.e(TAG, "onClick: " + imageUri);
                    String productId = binding.productId.getEditText().getText().toString();
                    String productName = binding.productName.getEditText().getText().toString();
                    String quantity = binding.quantity.getEditText().getText().toString();
                    String sellingPrice = binding.sellingPrice.getEditText().getText().toString();
                    String cost = binding.cost.getEditText().getText().toString();


                    if (productId.isEmpty()) {
                        binding.productId.setError("Provide your productId!");
                        binding.productId.requestFocus();
                    } else if (productName.isEmpty()) {
                        binding.productName.setError("Provide your productName first!");
                        binding.productName.requestFocus();
                    } else if (quantity.isEmpty()) {
                        binding.quantity.setError("Provide your quantity first!");
                        binding.quantity.requestFocus();
                    } else if (sellingPrice.isEmpty()) {
                        binding.sellingPrice.setError("Provide your sellingPrice first!");
                        binding.sellingPrice.requestFocus();
                    } else if (cost.isEmpty()) {
                        binding.cost.setError("Provide your cost first!");
                        binding.cost.requestFocus();
                    } else if (productId.isEmpty() && productName.isEmpty() && quantity.isEmpty() && sellingPrice.isEmpty() && cost.isEmpty()) {
                        Toast.makeText(Add_Product.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                    } else if (!productId.isEmpty() && !productName.isEmpty() && !quantity.isEmpty() && !sellingPrice.isEmpty() && !cost.isEmpty()) {

                        int costInt = Integer.parseInt(cost);
                        int sellingPriceInt = Integer.parseInt(sellingPrice);
                        int quantityInt = Integer.parseInt(quantity);
                        Product product = new Product(productId, productName, quantityInt, sellingPriceInt, costInt, FieldValue.serverTimestamp());

                        FirebaseFirestore.getInstance().collection("stores").document(mAuth.getUid())
                                .collection("store").whereEqualTo("user_id", mAuth.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    String store_id = queryDocumentSnapshots.getDocuments().get(0).getString("store_id");
                                    Log.e(TAG, "onEvent: " + store_id);

                                    if (!productType.equals("Select_Product_type")) {
                                        Log.e(TAG, "onEvent: " + productType);
                                        product.setProduct_type(productType);
                                        viewModel.add_Product(mAuth.getUid(), product, store_id,imageUri.toString());

                                        Intent intent=new Intent(Add_Product.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }


                                }

                            }
                        });


                    }


                } else {
                    Toast.makeText(Add_Product.this, "Upload Image", Toast.LENGTH_SHORT).show();
                }

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

                imageUri = resultUri;
                Log.e(TAG, "onActivityResult: " + resultUri);

                Glide.with(Add_Product.this).asBitmap().load(resultUri).into(binding.productImage);
//
//                viewModel.uploadUserImg(FirebaseAuth.getInstance().getUid(), resultUri);
//                imageUri = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }


    }

}
