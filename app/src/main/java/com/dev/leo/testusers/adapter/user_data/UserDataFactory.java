package com.dev.leo.testusers.adapter.user_data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.dev.leo.testusers.data.api.UserApiHelper;
import com.dev.leo.testusers.data.models.Result;

public final class UserDataFactory extends DataSource.Factory<Integer, Result> {
    private UserApiHelper apiHelper;
    private MutableLiveData<UserDataSource> liveData;

    public UserDataFactory(UserApiHelper apiHelper) {
        this.apiHelper = apiHelper;
        liveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Result> create() {
        UserDataSource userDataSource = new UserDataSource(apiHelper);
        liveData.postValue(userDataSource);
        return userDataSource;
    }

    public MutableLiveData<UserDataSource> getLiveData() {
        return liveData;
    }
}
