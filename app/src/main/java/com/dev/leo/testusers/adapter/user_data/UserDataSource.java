package com.dev.leo.testusers.adapter.user_data;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dev.leo.testusers.Constants;
import com.dev.leo.testusers.data.BaseCallback;
import com.dev.leo.testusers.data.api.NetworkState;
import com.dev.leo.testusers.data.api.UserApiHelper;
import com.dev.leo.testusers.data.models.Result;
import com.dev.leo.testusers.data.models.UsersResponse;

import java.util.ArrayList;
import java.util.List;

public final class UserDataSource extends PageKeyedDataSource<Integer, Result> {
    private static String seed;
    private static String query;

    private UserApiHelper apiHelper;

    private MutableLiveData<NetworkState> networkState;

    UserDataSource(UserApiHelper apiHelper){
        this.apiHelper = apiHelper;
        networkState = new MutableLiveData<>();
    }

    public static void setSeed(String s){
        seed = s;
    }

    public static void setQuery(String q){
        query = q;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Result> callback) {
        if (TextUtils.isEmpty(seed)) return;
        networkState.postValue(NetworkState.LOADING);

        apiHelper.getUserList(Constants.FIRST_PAGE, TextUtils.isEmpty(query) ? Constants.ITEM_COUNT : Constants.ITEM_COUNT_SEARCH, seed, new BaseCallback<UsersResponse>() {
            @Override
            public void onSuccess(UsersResponse data) {
                if (!data.getResults().isEmpty()) {
                    networkState.postValue(NetworkState.LOADED);
                    if (!TextUtils.isEmpty(query)){
                        List<Result> filteredData = new ArrayList<>();
                        for (Result x : data.getResults()){
                            if (x.getName().getFirst().contains(query) || x.getName().getLast().contains(query)) filteredData.add(x);
                        }
                        callback.onResult(filteredData, null, Constants.FIRST_PAGE + 1);
                    }else callback.onResult(data.getResults(), null, Constants.FIRST_PAGE + 1);
                }else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, Constants.EMPTY_DATA_MSG));
                }
            }

            @Override
            public void onError(int code, String message) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, message));
            }

            @Override
            public void onFailure(Throwable throwable) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getMessage()));
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Result> callback) {
        networkState.postValue(NetworkState.LOADING);
        apiHelper.getUserList(params.key, TextUtils.isEmpty(query) ? Constants.ITEM_COUNT : Constants.ITEM_COUNT_SEARCH, seed, new BaseCallback<UsersResponse>() {
            @Override
            public void onSuccess(UsersResponse data) {
                if (!data.getResults().isEmpty()) {
                    networkState.postValue(NetworkState.LOADED);
                    if (!TextUtils.isEmpty(query)) {
                        List<Result> filteredData = new ArrayList<>();
                        for (Result x : data.getResults()) {
                            if (x.getName().getFirst().contains(query) || x.getName().getLast().contains(query)) filteredData.add(x);
                        }
                        callback.onResult(filteredData, params.key + 1);
                    }else callback.onResult(data.getResults(), params.key + 1);
                }else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, Constants.EMPTY_DATA_MSG));
                }
            }

            @Override
            public void onError(int code, String message) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, message));
            }

            @Override
            public void onFailure(Throwable throwable) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getMessage()));
            }
        });
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
