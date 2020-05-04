package com.taffyosales.taffyosales.viewmodel;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.WriteBatch;
import com.taffyosales.taffyosales.model.Store;
import com.taffyosales.taffyosales.repo.StoreRepository;
import com.taffyosales.taffyosales.repo.UserRepository;

public class StoreViewModel extends ViewModel {
    private StoreRepository storeRepository=new StoreRepository();


    public void uploadImg(String user_id, Uri uri,String storeId){
        storeRepository.uploadImg(user_id,uri,storeId);
    }

    public void setShopDetails(String uid, Store store) {
        storeRepository.addStore(uid,store);
    }
}
