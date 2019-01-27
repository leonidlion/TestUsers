package com.dev.leo.testusers.data;

import android.content.Context;

import com.dev.leo.testusers.data.api.UserApiHelper;

public class RepositoryProvider {
    private static UserRepository userRepository;
    private static SharedData sharedData;

    public static void initUserRepository(UserApiHelper apiHelper){
        if (userRepository == null)
            userRepository = new UserRepository(apiHelper);
    }

    public static void initSharedData(Context context){
        if (sharedData == null)
            sharedData = new SharedData(context);
    }

    public static UserRepository getUserRepository(){
        return userRepository;
    }

    public static SharedData getSharedData(){
        return sharedData;
    }
}
