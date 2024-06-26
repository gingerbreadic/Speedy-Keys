package com.gingerbread.speedykeys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class registerActivity extends AppCompatActivity {

    private TextView usernameEditText, registration_passEditText, registration_confirmpassEditText, registration_emailEditText, verification_registration;
    private String verification_code;
    private ProgressBar progressBar;
    private boolean emailSent = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        registration_passEditText = findViewById(R.id.registration_pass);
        registration_confirmpassEditText = findViewById(R.id.registration_confirmpass);
        registration_emailEditText = findViewById(R.id.registration_email);
        TextView loginText = findViewById(R.id.loginText);
        verification_registration = findViewById(R.id.verification_registration);

        verification_registration.setVisibility(View.INVISIBLE);

        Button verify_email_button = findViewById(R.id.verify_email_button);
        progressBar = findViewById(R.id.progress);

        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(registerActivity.this, loginActivity.class);
            startActivity(intent);
            finish();
        });

        verify_email_button.setOnClickListener(v -> {
            String username, password, confirm_password, email;

            username = String.valueOf(usernameEditText.getText());
            password = String.valueOf(registration_passEditText.getText());
            confirm_password = String.valueOf(registration_confirmpassEditText.getText());
            email = String.valueOf(registration_emailEditText.getText());

            if (!username.isEmpty() && !password.isEmpty() && !confirm_password.isEmpty() && !email.isEmpty()) {
                if (password.length() >= 6) {
                    if (password.equals(confirm_password)) {
                        if (!emailSent) {
                            generateVerificationCode();
                            buttonSendEmail(email, "E-mail confirmation", "Code for registration in Speedy Keys \n <h2>" + verification_code + "<h2/>");
                            Toast.makeText(this, "Please check your email for verification code!", Toast.LENGTH_LONG).show();
                            emailSent = true;
                            verification_registration.setVisibility(View.VISIBLE);
                        } else {
                            String enteredCode = verification_registration.getText().toString();
                            if (enteredCode.equals(verification_code)) {
                                progressBar.setVisibility(View.VISIBLE);
                                Handler handler = new Handler();
                                handler.post(() -> {
                                    String[] field = new String[3];
                                    field[0] = "username";
                                    field[1] = "email";
                                    field[2] = "password";
                                    String[] data = new String[3];
                                    data[0] = username;
                                    data[1] = email;
                                    data[2] = password;
                                    PutData putData = new PutData("https://koryun.gaboyan.am/app1/login/signup.php", "POST", field, data);
                                    buttonSendEmail(email, "Registration success", "Welcome to Speedy Keys, " + username + "!\nYour registration is now complete. Get ready to test your typing skills and embark on exciting challenges!\nLet the typing games begin!");
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            progressBar.setVisibility(View.GONE);
                                            String result = putData.getResult();
                                            if (result.equals("Sign Up Success")) {
                                                Intent intent = new Intent(registerActivity.this, loginActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(registerActivity.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();
                                                finish();
                                            } else {
                                                Toast.makeText(registerActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(registerActivity.this, "This verification code is not correct", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(registerActivity.this, "Your password does not match confirm password field!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(registerActivity.this, "Password must be at least 6 characters long!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(registerActivity.this, "Make sure to fill all fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(registerActivity.this, loginActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("StaticFieldLeak")
    public void buttonSendEmail(final String email, final String subject, final String content) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                String sender = "koryun@gaboyan.am";
                String senderPass = "v5K$8c0t#zgf";

                String host = "smtp.yandex.ru";

                Properties properties = System.getProperties();
                properties.setProperty("mail.smtp.host", host);
                properties.setProperty("mail.smtp.port", "465");
                properties.setProperty("mail.smtp.ssl.enable", "true");
                properties.setProperty("mail.smtp.auth", "true");
                javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sender, senderPass);
                    }
                });
                try {
                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(sender));
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                    message.setSubject(subject);
                    message.setContent(content, "text/html");

                    Transport.send(message);
                    System.out.println("Mail successfully sent");
                } catch (MessagingException mex) {
                    mex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        }.execute();
    }

    private void generateVerificationCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int range = max - min + 1;
        verification_code = String.valueOf(random.nextInt(range) + min);
    }
}
