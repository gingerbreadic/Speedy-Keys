package com.gingerbread.typingchallenge;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class SettingsFragment extends Fragment {
    public static final String[] languages = {"English", "Armenian"};
    private static final String PREF_SELECTED_LANGUAGE = "selected_language";

    RadioGroup radioGroup;
    LanguageManager languageManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        radioGroup = view.findViewById(R.id.languageRadioGroup);

        languageManager = new LanguageManager(getContext());

        for (int i = 0; i < languages.length; i++) {
            String language = languages[i];
            int iconResourceId = (i == 0) ? R.drawable.en_icon : R.drawable.hy_icon;
            addRadioButton(language, iconResourceId);
        }

        String savedLanguage = languageManager.getSelectedLanguage();
        if (savedLanguage != null) {
            RadioButton radioButton = view.findViewWithTag(savedLanguage);
            if (radioButton != null) {
                radioButton.setChecked(true);
                setLocale(getLocaleCode(savedLanguage));
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                if (radioButton != null) {
                    String selectedLanguage = radioButton.getTag().toString();
                    languageManager.saveLanguage(selectedLanguage);
                    setLocale(getLocaleCode(selectedLanguage));
                }
            }
        });
        return view;
    }

    private void addRadioButton(String text, int iconResourceId) {
        RadioButton radioButton = new RadioButton(requireContext());
        radioButton.setText(text);
        radioButton.setTag(text); // Set tag as language for identification

        // Create and set the icon
        Drawable icon = getResources().getDrawable(iconResourceId);
        radioButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
        radioButton.setCompoundDrawablePadding(16);

        radioButton.setGravity(Gravity.CENTER_VERTICAL);

        radioGroup.addView(radioButton);
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
