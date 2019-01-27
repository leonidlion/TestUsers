package com.dev.leo.testusers.data.api;

import android.support.annotation.IntRange;

import com.dev.leo.testusers.data.models.UsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET(".")
    Call<UsersResponse> getUserList(@Query("page") @IntRange(from = 1) int page,
                                    @Query("results") @IntRange(from = 5) int results,
                                    @Query("seed") String seed);
}
