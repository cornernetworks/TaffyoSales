package com.taffyosales.taffyosales.viewmodel;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.taffyosales.taffyosales.model.Users;
import com.taffyosales.taffyosales.repo.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository=new UserRepository();
    public void AddUserData(Users user){
        userRepository.addUser(user);
    }

    public void uploadUserImg(String user_id, Uri uri){
        userRepository.uploadImg(user_id,uri);
    }

}
