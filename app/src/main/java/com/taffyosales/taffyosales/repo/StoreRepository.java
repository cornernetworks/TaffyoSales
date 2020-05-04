package com.taffyosales.taffyosales.repo;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.taffyosales.taffyosales.model.Store;
import com.taffyosales.taffyosales.model.Users;

import java.util.Random;
import java.util.Set;

public class StoreRepository {

    FirebaseAuth mAuth=FirebaseAuth.getInstance();

    private static final String TAG = "StoreRepository";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference storesRef = firebaseFirestore.collection("stores");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("store_images").child(getSaltString() + ".jpg");




    public Task<Void> addStore(String user_id,Store store) {

        Log.e(TAG, "StoreId 3:  "+store.getStore_id() );
        DocumentReference storeRef = storesRef.document(user_id).collection("store").document(store.getStore_id());
        WriteBatch batch = firebaseFirestore.batch();
        batch.set(storeRef,store);


//        batch.update(storeRef, "shopname",store.getShopname());
//        batch.update(storeRef, "Area",store.getArea());
//        batch.update(storeRef, "Address",store.getAddress());
//        batch.update(storeRef, "Pancard",store.getPancard());
//        batch.update(storeRef, "Gst_no",store.getGst_no());
//        batch.update(storeRef, "Contact_no",store.getContact_no());
//        batch.update(storeRef, "email_id",mAuth.getCurrentUser().getEmail());
//        batch.update(storeRef, "user_id",mAuth.getUid());
//        batch.update(storeRef, "city_name",store.getCity_name());
//        batch.update(storeRef, "Owner_name",store.getOwner_name());
        return batch.commit();
    }


    public Task<Void> uploadImg(String user_id, Uri uri,String storeId) {
        DocumentReference storeRef = storesRef.document(user_id).collection("store").document(storeId);
        WriteBatch batch = firebaseFirestore.batch();


        Log.e(TAG, "uploadImg: "+storageReference );
        Log.e(TAG, "StoreId:  "+storeId );

        storageReference.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    Uri uris = uri1;
                    batch.update(storeRef, "signature",uris.toString());
                    batch.commit();
                });
            }
        });
        return null;
    }


    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 9) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

}
