package com.gingerbread.speedykeys;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguageManager {

    private static final String PREF_NAME = "UserLanguagePrefs";
    private static final String KEY_SELECTED_LANGUAGE = "selectedLanguage";
    private final SharedPreferences sharedPreferences;

    public LanguageManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveLanguage(String languageCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SELECTED_LANGUAGE, languageCode);
        editor.apply();
    }

    public String getSelectedLanguage() {
        return sharedPreferences.getString(KEY_SELECTED_LANGUAGE, "en");
    }
}
