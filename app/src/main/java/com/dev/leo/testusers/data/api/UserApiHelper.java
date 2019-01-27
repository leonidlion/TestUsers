package com.dev.leo.testusers.data.api;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.dev.leo.testusers.BuildConfig;
import com.dev.leo.testusers.Constants;
import com.dev.leo.testusers.data.BaseCallback;
import com.dev.leo.testusers.data.models.UsersResponse;
import com.dev.leo.testusers.utils.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApiHelper {
    private static UserApiHelper instance;
    private UserService service;

    private UserApiHelper(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(UserService.class);
    }

    public static UserApiHelper init(){
        if (instance == null)
            instance = new UserApiHelper();
        return instance;
    }

    public void getUserList(@IntRange(from = 1) int page,
                            @IntRange(from = 5) int results,
                            @NonNull String seed, final BaseCallback<UsersResponse> callback){
        Call<UsersResponse> call = service.getUserList(page, results, seed);
        call.enqueue(ApiUtils.getGenericCallback(callback));
    }
}
