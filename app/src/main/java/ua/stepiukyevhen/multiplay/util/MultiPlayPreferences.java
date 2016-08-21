package ua.stepiukyevhen.multiplay.util;


import android.content.SharedPreferences;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

public class MultiPlayPreferences {

    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String IS_FIRST_LAUNCH = "is_first_launch";
    private static final String SAVED_USERNAME = "saved_username";
    private static final String SAVED_PASSWORD = "saved_password";

    private SharedPreferences prefs;

    public MultiPlayPreferences(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public String getRefreshToken() {
        return getString(REFRESH_TOKEN, null);
    }

    public void setRefreshToken(String refreshToken) {
        putString(REFRESH_TOKEN, refreshToken);
    }

    public boolean isFirstLaunch() {
        return getBoolean(IS_FIRST_LAUNCH, true);
    }

    public void setIsFirstLaunch(boolean isFirstLaunch) {
        putBoolean(IS_FIRST_LAUNCH, isFirstLaunch);
    }

    public String getSavedUsername() {
        return getString(SAVED_USERNAME, null);
    }

    public void setSavedUsername(String username) {
        putString(SAVED_USERNAME, username);
    }

    public String getSavedPassword() {
        return getString(SAVED_PASSWORD, null);
    }

    public void setSavedPassword(String password) {
        putString(SAVED_PASSWORD, password);
    }

    /*
    Delegated methods
     */
    private String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    private Set<String> getStringSet(String key, Set<String> defValues) {
        return prefs.getStringSet(key, defValues);
    }

    private int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    private long getLong(String key, long defValue) {
        return prefs.getLong(key, defValue);
    }

    private float getFloat(String key, float defValue) {
        return prefs.getFloat(key, defValue);
    }

    private boolean getBoolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    private void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    private void putFloat(String key, float value) {
        prefs.edit().putFloat(key, value).apply();
    }

    private void putLong(String key, long value) {
        prefs.edit().putLong(key, value).apply();
    }

    private void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    private void putStringSet(String key, Set<String> values) {
        prefs.edit().putStringSet(key, values).apply();
    }

    private void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }
}
