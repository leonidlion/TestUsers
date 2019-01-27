package com.dev.leo.testusers.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dev.leo.testusers.data.api.UserApiHelper;
import com.dev.leo.testusers.data.models.Result;
import com.dev.leo.testusers.data.models.UsersResponse;

import java.util.List;

public class UserRepository {
    private UserApiHelper apiHelper;

    UserRepository(UserApiHelper apiHelper){
        this.apiHelper = apiHelper;
    }

    public LiveData<List<Result>> getUserList(@IntRange(from = 1) int page, @Nullable Integer results, @NonNull String seed){
        MutableLiveData<List<Result>> liveData = new MutableLiveData<>();
        apiHelper.getUserList(page, results != null ? results : 10, seed, new BaseCallback<UsersResponse>() {
            @Override
            public void onSuccess(UsersResponse data) {
                liveData.setValue(data.getResults());
            }

            @Override
            public void onError(int code, String message) {
                liveData.setValue(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    public UserApiHelper getApiHelper() {
        return apiHelper;
    }
}
