package com.gingerbread.speedykeys;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLoginManager {

    private static final String PREF_NAME = "UserLoginPrefs";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERS_HIGHSCORE = "highScore";

    private final SharedPreferences sharedPreferences;

    public UserLoginManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLoggedIn(boolean isLoggedIn, String userId, String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.putString(KEY_USER_ID, userId);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, "");
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public int getUsersHighScore() {
        String highScoreString = sharedPreferences.getString(KEY_USERS_HIGHSCORE, "0");
        return Integer.parseInt(highScoreString);
    }

    public void saveHighscore(Integer highscore) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERS_HIGHSCORE, String.valueOf(highscore));
        editor.apply();
    }

    public void clearLoginState() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
