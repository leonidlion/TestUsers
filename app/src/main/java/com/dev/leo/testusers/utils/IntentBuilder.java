package com.dev.leo.testusers.utils;

import android.content.Context;
import android.content.Intent;

import com.dev.leo.testusers.data.models.Result;
import com.dev.leo.testusers.enums.BundleKeys;
import com.dev.leo.testusers.ui.user_detail.UserDetailActivity;

public class IntentBuilder {

    public static Intent getUserDetailIntent(Context context, Result result){
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(BundleKeys.KEY_USER.name(), result);
        return intent;
    }
}
