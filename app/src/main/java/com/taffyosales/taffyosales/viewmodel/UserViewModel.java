package com.taffyosales.taffyosales.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taffyosales.taffyosales.model.Product;
import com.taffyosales.taffyosales.model.Users;
import com.taffyosales.taffyosales.repo.UserRepository;

import io.grpc.internal.SharedResourceHolder;

public class UserViewModel extends ViewModel {
    MutableLiveData<String> store_id=new MutableLiveData<>();

    private UserRepository userRepository=new UserRepository();
    public void AddUserData(Users user){
        userRepository.addUser(user);
    }

    public void uploadUserImg(String user_id, Uri uri){
        userRepository.uploadImg(user_id,uri);
    }


    public void setStoerId(String store_id){
        this.store_id.setValue(store_id);
    }
    public LiveData<String> getStoreId(){
        return store_id;
    }

}
