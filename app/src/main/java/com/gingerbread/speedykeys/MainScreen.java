package com.gingerbread.speedykeys;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.util.Locale;

public class MainScreen extends AppCompatActivity {
    private static final int LEADERBOARD_ID = 2;
    private static final long NAVIGATION_DISABLE_DURATION = 1500; // 1.5 seconds
    private MeowBottomNavigation bottomNavigation;
    private RelativeLayout mainLayout;
    private Fragment currentFragment;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initializeLanguageSettings();

        bottomNavigation = findViewById(R.id.bottomNavigation);
        initializeBottomNavigation();

        currentFragment = new HomeFragment();
        replaceFragment(currentFragment);

        mainLayout = findViewById(R.id.main_layout);
        mainLayout.setBackgroundResource(R.drawable.game_back);

        handler = new Handler(); // Initialize handler only once

        meowNavigation();
        bottomNavigation.setEnabled(false);
    }

    private void initializeLanguageSettings() {
        LanguageManager languageManager = new LanguageManager(this);
        String savedLanguage = languageManager.getSelectedLanguage();
        setLocale(savedLanguage.equals("Armenian") ? "hy" : "en");
    }

    private void initializeBottomNavigation() {
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.game_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(LEADERBOARD_ID, R.drawable.leaderboard_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.profile_img));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.info_img));

        bottomNavigation.show(1, true); // Set default tab
    }

    private void meowNavigation() {
        bottomNavigation.setOnClickMenuListener(model -> {
            if (this == null) {
                Log.e("MainScreen", "Activity context is null. Cannot switch fragment.");
                return null;
            }

            switch (model.getId()) {
                case 1:
                    currentFragment = new HomeFragment();
                    mainLayout.setBackgroundResource(R.drawable.game_back);
                    break;
                case LEADERBOARD_ID:
                    currentFragment = new LeaderboardFragment();
                    temporarilyDisableBottomNavigation(); // Disable navigation for safety
                    mainLayout.setBackgroundResource(R.drawable.leaderboard_item_bg);
                    break;
                case 3:
                    currentFragment = new ProfileFragment();
                    mainLayout.setBackgroundResource(R.drawable.account_bg_gradient);
                    break;
                case 4:
                    currentFragment = new SettingsFragment();
                    mainLayout.setBackgroundResource(R.drawable.settings_background);
                    break;
                default:
                    return null; // Unknown tab, do nothing
            }

            replaceFragment(currentFragment);
            return null; // No further action required
        });
    }

    private void temporarilyDisableBottomNavigation() {
        bottomNavigation.setEnabled(false);
        bottomNavigation.setClickable(false);

        handler.postDelayed(() -> {
            bottomNavigation.setEnabled(true);
            bottomNavigation.setClickable(true);
        }, NAVIGATION_DISABLE_DURATION); // Re-enable after delay
    }

    private void replaceFragment(Fragment fragment) {
        if (getSupportFragmentManager() != null && fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.framelayout, fragment);
            transaction.commit();
        } else {
            Log.e("MainScreen", "Unable to replace fragment: Fragment or FragmentManager is null.");
        }
    }

    @Override
    public void onBackPressed() {
        if (currentFragment instanceof HomeFragment) {
            showExitDialog();
        } else {
            currentFragment = new HomeFragment();
            bottomNavigation.show(1, true); // Go back to Home tab
            replaceFragment(currentFragment);
        }
    }

    private void showExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }

    private void setLocale(String langCode) {
        Locale locale = new Locale(langCode);
        getResources().getConfiguration().setLocale(locale);
        getResources().updateConfiguration(getResources().getConfiguration(), getResources().getDisplayMetrics());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Clean up to avoid memory leaks or null reference issues
    }
}
