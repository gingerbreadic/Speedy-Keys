package com.gingerbread.speedykeys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class loginActivity extends AppCompatActivity {
    TextView login_username, login_password, signUpText, guestMode, testMode;
    Button login_button;
    ProgressBar progressBar;
    private UserLoginManager userLoginManager;
    String userId, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLoginManager = new UserLoginManager(this);

        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signUpText = findViewById(R.id.signUpText);

        guestMode = findViewById(R.id.guestMode);
        testMode = findViewById(R.id.testMode);

        progressBar = findViewById(R.id.progress);

        // Add checks to ensure views are not null
        if (login_username == null) throw new NullPointerException("login_username is null");
        if (login_password == null) throw new NullPointerException("login_password is null");
        if (login_button == null) throw new NullPointerException("login_button is null");
        if (signUpText == null) throw new NullPointerException("signUpText is null");
        if (guestMode == null) throw new NullPointerException("guestMode is null");
        if (testMode == null) throw new NullPointerException("testMode is null");
        if (progressBar == null) throw new NullPointerException("progressBar is null");


        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(loginActivity.this, registerActivity.class);
            startActivity(intent);
            finish();
        });

        if (userLoginManager.isLoggedIn()) {
            username = userLoginManager.getUsername();

            Intent intent = new Intent(loginActivity.this, MainScreen.class);
            startActivity(intent);
            finish();
        }

        login_button.setOnClickListener(v -> {
            String username, password;

            username = String.valueOf(login_username.getText());
            password = String.valueOf(login_password.getText());

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(loginActivity.this, "Make sure to fill all fields!", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                Handler handler = new Handler();
                handler.post(() -> {
                    String[] field = {"username", "password"};
                    String[] data = {username, password};

                    PutData putData = new PutData("https://koryun.gaboyan.am/app1/login/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = putData.getResult();
                            String[] parts = result.split(":");

                            if (parts.length >= 2) {
                                String loginSuccess = parts[0];
                                userId = parts[1];

                                if (loginSuccess.equals("Login Success")) {
                                    userLoginManager.setLoggedIn(true, userId, username);
                                    Intent intent = new Intent(loginActivity.this, MainScreen.class);
                                    startActivity(intent);
                                    Toast.makeText(loginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(loginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(loginActivity.this, "Invalid server response format", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(loginActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        guestMode.setOnClickListener(v -> {
            Intent intent = new Intent(loginActivity.this, MainScreen.class);
            startActivity(intent);
            finish();
        });

        testMode.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            Handler handler = new Handler();
            handler.post(() -> {
                String[] field = {"username", "password"};
                String[] data = {"sictst1@gmail.com", "Samsung2023"};

                PutData putData = new PutData("https://koryun.gaboyan.am/app1/login/login.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = putData.getResult();
                        String[] parts = result.split(":");

                        if (parts.length >= 2) {
                            String loginSuccess = parts[0];
                            userId = parts[1];

                            if (loginSuccess.equals("Login Success")) {
                                userLoginManager.setLoggedIn(true, userId, "sictst1@gmail.com");
                                Intent intent = new Intent(loginActivity.this, MainScreen.class);
                                startActivity(intent);
                                Toast.makeText(loginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(loginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(loginActivity.this, "Invalid server response format", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(loginActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}
