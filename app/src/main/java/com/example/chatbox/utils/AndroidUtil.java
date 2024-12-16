package com.example.chatbox.utils;

import android.content.Intent;

import com.example.chatbox.UserModel;

public class AndroidUtil {
    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("user_name",model.getUserName());
        intent.putExtra("user_email",model.getUserEmail());
        intent.putExtra("user_id",model.getUserId());
    }
    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel model = new UserModel();
        model.setUserName(intent.getStringExtra("user_name"));
        model.setUserEmail(intent.getStringExtra("user_email"));
        model.setUserId(intent.getStringExtra("user_id"));
        return model;
    }
}
