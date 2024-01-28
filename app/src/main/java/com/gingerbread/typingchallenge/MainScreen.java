package com.gingerbread.typingchallenge;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainScreen extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    RelativeLayout main_layout;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        LanguageManager languageManager = new LanguageManager(this);
        String savedLanguage = languageManager.getSelectedLanguage();
        if (savedLanguage.equals("Armenian")) {
            setLocale("hy");
        }else {
            setLocale("en");
        }

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.game_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.leaderboard_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.profile_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.settings_img));

        bottomNavigation.show(2, true);
        currentFragment = new LeaderboardFragment();
        replaceFragment(currentFragment);

        main_layout = findViewById(R.id.main_layout);

        meowNavigation();
    }

    private void meowNavigation() {
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        currentFragment = new HomeFragment();
                        break;
                    case 2:
                        currentFragment = new LeaderboardFragment();
                        break;
                    case 3:
                        currentFragment = new ProfileFragment();
                        break;
                    case 4:
                        currentFragment = new SettingsFragment();
                        break;
                }
                replaceFragment(currentFragment);
                return null;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof HomeFragment) {
            showExitDialog();
        } else {
            currentFragment = new HomeFragment();
            bottomNavigation.show(1, true);
            replaceFragment(currentFragment);
        }
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        getResources().getConfiguration().setLocale(locale);
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.commit();
    }
}
