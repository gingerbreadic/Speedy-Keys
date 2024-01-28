package com.gingerbread.typingchallenge;

import android.content.DialogInterface;
import android.content.Intent;
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
    TextView login_usernameEditText, login_passwordEditText, signUpText, guestMode;
    Button login_button;
    ProgressBar progressBar;
    private UserLoginManager userLoginManager;
    String userId, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userLoginManager = new UserLoginManager(this);

        login_usernameEditText = findViewById(R.id.login_username);
        login_passwordEditText = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);
        signUpText = findViewById(R.id.signUpText);

        guestMode = findViewById(R.id.guestMode);

        progressBar = findViewById(R.id.progress);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, registerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if (userLoginManager.isLoggedIn()) {
            username = userLoginManager.getUsername();

            Intent intent = new Intent(loginActivity.this, MainScreen.class);
            startActivity(intent);
            finish();
        }

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;

                username = String.valueOf(login_usernameEditText.getText());
                password = String.valueOf(login_passwordEditText.getText());

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(loginActivity.this, "Make sure to fill all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
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
                                            Toast.makeText(loginActivity.this, result, Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(loginActivity.this, result, Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(loginActivity.this, "Invalid server response format", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(loginActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
        });

        guestMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, MainScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
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
}
