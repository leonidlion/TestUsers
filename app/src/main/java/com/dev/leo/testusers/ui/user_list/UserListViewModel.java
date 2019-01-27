package com.dev.leo.testusers.ui.user_list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.databinding.ObservableField;

import com.dev.leo.testusers.Constants;
import com.dev.leo.testusers.adapter.user_data.UserDataFactory;
import com.dev.leo.testusers.adapter.user_data.UserDataSource;
import com.dev.leo.testusers.data.RepositoryProvider;
import com.dev.leo.testusers.data.api.NetworkState;
import com.dev.leo.testusers.data.models.Result;

import java.util.concurrent.Executors;

public class UserListViewModel extends ViewModel {
    private LiveData<PagedList<Result>> resultLiveData;
    private LiveData<NetworkState> networkStateLiveData;
    private LiveData<UserDataSource> userDataSourceLiveData;

    private ObservableField<String> seedField = new ObservableField<>();
    private ObservableField<String> queryField= new ObservableField<>();

    public UserListViewModel(){
        initPagingData();
    }

    private void initPagingData(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(Constants.ITEM_COUNT)
                .build();



        UserDataFactory factory = new UserDataFactory(RepositoryProvider.getUserRepository().getApiHelper());

        userDataSourceLiveData = factory.getLiveData();

        networkStateLiveData = Transformations.switchMap(factory.getLiveData(), UserDataSource::getNetworkState);

        resultLiveData = new LivePagedListBuilder<>(factory, config)
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();

        seedField.set(RepositoryProvider.getSharedData().getSeed());
    }

    public void changeSeed(){
        UserDataSource.setSeed(seedField.get());
        if (userDataSourceLiveData.getValue() != null)
            userDataSourceLiveData.getValue().invalidate();
    }

    public void searchUserByNames(){
        UserDataSource.setQuery(queryField.get());
        if (userDataSourceLiveData.getValue() != null)
            userDataSourceLiveData.getValue().invalidate();
    }

    public void clearQuery(){
        queryField.set(null);
        UserDataSource.setQuery(queryField.get());
        if (userDataSourceLiveData.getValue() != null)
            userDataSourceLiveData.getValue().invalidate();
    }

    public ObservableField<String> getSeedField() {
        return seedField;
    }

    public ObservableField<String> getQueryField() {
        return queryField;
    }

    public LiveData<PagedList<Result>> getResultLiveData() {
        return resultLiveData;
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }
}
