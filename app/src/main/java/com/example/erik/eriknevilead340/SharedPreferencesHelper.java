package com.example.erik.eriknevilead340;

import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    static final String KEY_ENTRY = "text";

    private final SharedPreferences mSharedPreferences;


    public SharedPreferencesHelper(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public boolean saveEntry(String msg) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString(KEY_ENTRY, msg);

        return editor.commit();
    }

    public String getKeyEntry() {
        return mSharedPreferences.getString(KEY_ENTRY, "");
    }
}
