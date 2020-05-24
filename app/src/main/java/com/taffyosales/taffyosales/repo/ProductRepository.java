package com.taffyosales.taffyosales.repo;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.taffyosales.taffyosales.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {
    private OnTaskComplete onTaskComplete;
    //initialize variables
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "StoreRepository";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    //add data




    //    private CollectionReference productRef = firebaseFirestore.collection("Products");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("product_images").child(System.currentTimeMillis() + ".jpg");
    public void addProduct(String uid, Product product, String store_id, String imgUri) {

        Uri uri = Uri.parse(imgUri);
        WriteBatch batch = firebaseFirestore.batch();
        storageReference.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    Uri uris = uri1;
                    product.setImageUri(uris.toString());
                    Log.e(TAG, "addProduct: " + uris);
                    firebaseFirestore.collection("products").document(store_id).collection(product.getProduct_type()).document(product.getProduct_id())
                            .set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Map<String, Object> productMap = new HashMap<>();
                                productMap.put("store_id", store_id);
                                productMap.put("last_update", FieldValue.serverTimestamp());
                                productMap.put("product_name", product.getProduc_name());
                                productMap.put("quantity", product.getQuantity());
                                productMap.put("product_type", product.getProduct_type());
                                productMap.put("product_id", product.getProduct_id());

                                firebaseFirestore.collection("Products").document(store_id).set(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.e(TAG, "addProduct: " + uris);
                                        }
                                    }
                                });

                                Log.e(TAG, "addProduct: " + task.getResult());
                            }

                        }
                    });


                });
            }
        });


//        products->store_id->soap->soap_id->detol-details_of_product->lastupdate


    }


    public void getProduct(){

    }

    //show data
    public void getProductList(String store_id) {
        DocumentReference productRef = FirebaseFirestore.getInstance().collection("Products").document();

//
//        userRef.get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Log.e(TAG, "onComplete: Task Complete");
//                onTaskComplete.allUserList(task.getResult().toObjects(User.class));
//            } else {
//                Log.e(TAG, "onComplete: Task  Not Complete");
//                onTaskComplete.onError(task.getException());
//            }
//        });

    }

    interface OnTaskComplete {
        void showAllProducts(List<Product> productList);
    }





}
