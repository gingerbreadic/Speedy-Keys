// HomeFragment.java
package com.gingerbread.speedykeys;

import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class HomeFragment extends Fragment {
    private Button playGameButton;
    private RadioGroup languageRadioGroup;
    private LanguageManager languageManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize UI elements
        playGameButton = view.findViewById(R.id.play_game_button);
        languageRadioGroup = view.findViewById(R.id.languageRadioGroup);


        // Set up language selection
        languageManager = new LanguageManager(getContext());

        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                if (radioButton != null) {
                    Drawable backgroundDrawable = radioButton.getBackground();
                    if (backgroundDrawable != null && backgroundDrawable.getConstantState() != null) {
                        if (backgroundDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.hy_icon).getConstantState())) {
                            String selectedLanguage = "Armenian";
                            languageManager.saveLanguage(selectedLanguage);
                            setLocale(getLocaleCode(selectedLanguage));
                        } else {
                            String selectedLanguage = "English";
                            languageManager.saveLanguage(selectedLanguage);
                            setLocale(getLocaleCode(selectedLanguage));
                        }
                    }
                }
            }
        });



        // Set click listener for play game button
        playGameButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainGameActivity.class);
            startActivity(intent);
        });

        return view;
    }


    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        getResources().getConfiguration().setLocale(locale);
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());

        // Refresh the UI
        getActivity().recreate();
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
