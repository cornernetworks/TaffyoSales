package com.taffyosales.taffyosales.repo;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.taffyosales.taffyosales.model.Product;
import com.taffyosales.taffyosales.model.Users;

import java.util.List;
import java.util.Random;


public class UserRepository {

//    private OnTaskComplete onTaskComplete;


    //initialize variables
    private static final String TAG = "UserRepository";
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference userRef = firebaseFirestore.collection("users");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_images").child(getSaltString() + ".jpg");




//    //initialize interface
//    public UserRepository(OnTaskComplete taskComplete) {
//        this.onTaskComplete = taskComplete;
//    }









    //add data
    public Task<Void> addUser(Users users) {
        DocumentReference userRefs = userRef.document(users.getUser_id());
        WriteBatch batch = firebaseFirestore.batch();
        batch.set(userRefs, users);
        return batch.commit();

    }

    public Task<Void> uploadImg(String user_id, Uri uri) {
        DocumentReference userRefs = userRef.document(user_id);
        WriteBatch batch = firebaseFirestore.batch();

        Log.e(TAG, "uploadImg: "+storageReference );
        storageReference.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri1 -> {
                    Uri uris = uri1;
                    batch.update(userRefs, "user_image", uris.toString());
                    batch.commit();

//                    Log.e(TAG, "user download uri is " + uris);
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




    //get data






//    //initialize all method you pass in viewmodel here
//    interface OnTaskComplete {
//        void allUserList(List<Product> productList);
//        void onError(Exception e);
//    }

}
