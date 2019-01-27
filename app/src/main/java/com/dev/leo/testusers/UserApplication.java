package com.dev.leo.testusers;

import android.app.Application;

import com.dev.leo.testusers.data.RepositoryProvider;
import com.dev.leo.testusers.data.api.UserApiHelper;

public class UserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDataProvider();
    }

    private void initDataProvider(){
        RepositoryProvider.initUserRepository(UserApiHelper.init());
        RepositoryProvider.initSharedData(this);
    }
}
