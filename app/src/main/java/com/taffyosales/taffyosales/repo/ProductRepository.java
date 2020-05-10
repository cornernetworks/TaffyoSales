package com.taffyosales.taffyosales.repo;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.taffyosales.taffyosales.model.Product;

public class ProductRepository {
    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    private static final String TAG = "StoreRepository";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference productRef = firebaseFirestore.collection("Products");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("product_images").child(System.currentTimeMillis() + ".jpg");


    public void addProduct(String uid, Product product, String store_id) {

        Uri uri= Uri.parse(product.getImageUri());
        WriteBatch batch = firebaseFirestore.batch();
        storageReference.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    Uri uris = uri1;
                    Log.e(TAG, "addProduct: "+uris);
                });
            }
        });



//        products->store_id->soap->soap_id->detol-details_of_product->lastupdate


    }
}
