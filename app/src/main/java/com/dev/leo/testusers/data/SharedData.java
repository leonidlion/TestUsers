package com.dev.leo.testusers.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {
    private static final String PREF_NAME = "PREF_NAME";
    private static final String KEY_SEED = "KEY_SEED";

    private SharedPreferences preferences;

    SharedData(Context context){
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveSeed(String seed){
        preferences.edit().putString(KEY_SEED, seed).apply();
    }

    public String getSeed(){
        return preferences.getString(KEY_SEED, "");
    }
}
