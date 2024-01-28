package com.gingerbread.typingchallenge;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class SettingsFragment extends Fragment {
    public static final String[] languages = {"Select Language", "English", "Armenian"};
    private static final String PREF_SELECTED_LANGUAGE = "selected_language";

    Spinner spinner;
    LanguageManager languageManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        spinner = view.findViewById(R.id.languageSpinner);

        languageManager = new LanguageManager(getContext());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.row, R.id.language_name, languages);
        spinner.setAdapter(adapter);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView icon_language_spinner = view.findViewById(R.id.icon);

        String savedLanguage = languageManager.getSelectedLanguage();
        if (savedLanguage != null) {
            int position = adapter.getPosition(savedLanguage);
            spinner.setSelection(position);
            setLocale(getLocaleCode(savedLanguage));
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedLanguage = parentView.getItemAtPosition(position).toString();
                if (selectedLanguage.equals("English")){
                    languageManager.saveLanguage(selectedLanguage);
                    setLocale("en");
                } else if (selectedLanguage.equals("Armenian")) {
                    languageManager.saveLanguage(selectedLanguage);
                    setLocale("hy");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return view;
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        getResources().getConfiguration().setLocale(locale);
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.commit();
    }

    private String getLocaleCode(String selectedLanguage) {
        switch (selectedLanguage) {
            case "English":
                return "en";
            case "Armenian":
                return "hy";
            default:
                return "en";
        }
    }
}
