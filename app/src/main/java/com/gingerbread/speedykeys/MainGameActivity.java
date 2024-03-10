package com.gingerbread.speedykeys;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;


public class MainGameActivity extends AppCompatActivity {
    TextView wroten_text;
    TextView score_textView;
    TextView health_textView;
    TextView word;
    int screenHeight;
    int numberLine;
    int speed;
    int score = 0;
    int health = 10;
    int[] range;
    String words_file;
    String range_file;
    StringBuilder text = new StringBuilder();
    Animation fallingAnimation;
    ConstraintLayout armenian_layout, game_over_screen, english_layout, global;
    String id;
    TextView score_gameOver;
    UserLoginManager userLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        text.append("");

        userLoginManager = new UserLoginManager(getApplicationContext());
        id = userLoginManager.getUserId();
        english_layout = findViewById(R.id.layout_english);
        game_over_screen = findViewById(R.id.game_over_screen);
        armenian_layout = findViewById(R.id.layout_armenian);
        global = findViewById(R.id.global_layout);
        wroten_text = findViewById(R.id.wroten_text);
        word = findViewById(R.id.word);
        health_textView = findViewById(R.id.health_textView);
        score_textView = findViewById(R.id.score_textView);

        LanguageManager languageManager = new LanguageManager(this);
        String savedLanguage = languageManager.getSelectedLanguage();
        if (savedLanguage != null) {
            if (savedLanguage.equals("Armenian")) {
                armenian_layout.setVisibility(View.VISIBLE);
                global.setVisibility(View.VISIBLE);
                english_layout.setVisibility(View.GONE);

                range_file = "arm_range.txt";
                words_file = "arm_words.txt";
            } else {
                english_layout.setVisibility(View.VISIBLE);
                global.setVisibility(View.VISIBLE);
                armenian_layout.setVisibility(View.GONE);

                range_file = "eng_range.txt";
                words_file = "eng_words.txt";
            }
        } else {
            armenian_layout.setVisibility(View.GONE);
            global.setVisibility(View.VISIBLE);
            english_layout.setVisibility(View.VISIBLE);

            range_file = "eng_range.txt";
            words_file = "eng_words.txt";
        }

        speed = 7000;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        game_over_screen.setVisibility(View.INVISIBLE);
        score_gameOver = findViewById(R.id.score_gameOver);

        fallingAnimation = createFallingAnimation();

        try {
            getRandomWord(range_file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        startFallingAnimation();

        int keyHeight = (int) (screenHeight * 0.07);

        LinearLayout firstRow_en = findViewById(R.id.first_row_en);
        setKeyHeightForLinearLayout(firstRow_en, keyHeight);

        LinearLayout secondRow_en = findViewById(R.id.second_row_en);
        setKeyHeightForLinearLayout(secondRow_en, keyHeight);

        LinearLayout thirdRow_en = findViewById(R.id.third_row_en);
        setKeyHeightForLinearLayout(thirdRow_en, keyHeight);

        LinearLayout forthRow_en = findViewById(R.id.forth_row_en);
        setKeyHeightForLinearLayout(forthRow_en, keyHeight);

        //Armenian keyboard layout
        //Row first
        TextView es_TextView_hy = findViewById(R.id.es_button_hy);
        TextView th_TextView_hy = findViewById(R.id.th_button_hy);
        TextView ph_TextView_hy = findViewById(R.id.ph_button_hy);
        TextView dz_TextView_hy = findViewById(R.id.dz_button_hy);
        TextView j_TextView_hy = findViewById(R.id.j_button_hy);
        TextView ev_TextView_hy = findViewById(R.id.ev_button_hy);
        TextView rh_TextView_hy = findViewById(R.id.rh_button_hy);
        TextView ch_TextView_hy = findViewById(R.id.ch_button_hy);
        TextView tch_TextView_hy = findViewById(R.id.tch_button_hy);
        TextView jh_TextView_hy = findViewById(R.id.jh_button_hy);
        TextView ts_TextView_hy = findViewById(R.id.ts_button_hy);

        //Row second
        TextView q_TextView_hy = findViewById(R.id.q_button_hy);
        TextView vo_TextView_hy = findViewById(R.id.vo_button_hy);
        TextView e_TextView_hy = findViewById(R.id.e_button_hy);
        TextView r_TextView_hy = findViewById(R.id.r_button_hy);
        TextView t_TextView_hy = findViewById(R.id.t_button_hy);
        TextView yh_TextView_hy = findViewById(R.id.yh_button_hy);
        TextView u_TextView_hy = findViewById(R.id.u_button_hy);
        TextView i_TextView_hy = findViewById(R.id.i_button_hy);
        TextView o_TextView_hy = findViewById(R.id.o_button_hy);
        TextView p_TextView_hy = findViewById(R.id.p_button_hy);

        //Row third
        TextView a_TextView_hy = findViewById(R.id.a_button_hy);
        TextView s_TextView_hy = findViewById(R.id.s_button_hy);
        TextView d_TextView_hy = findViewById(R.id.d_button_hy);
        TextView f_TextView_hy = findViewById(R.id.f_button_hy);
        TextView g_TextView_hy = findViewById(R.id.g_button_hy);
        TextView h_TextView_hy = findViewById(R.id.h_button_hy);
        TextView y_TextView_hy = findViewById(R.id.y_button_hy);
        TextView k_TextView_hy = findViewById(R.id.k_button_hy);
        TextView l_TextView_hy = findViewById(R.id.l_button_hy);
        TextView kh_TextView_hy = findViewById(R.id.kh_button_hy);

        //Row forth
        TextView z_TextView_hy = findViewById(R.id.z_button_hy);
        TextView gh_TextView_hy = findViewById(R.id.gh_button_hy);
        TextView c_TextView_hy = findViewById(R.id.c_button_hy);
        TextView v_TextView_hy = findViewById(R.id.v_button_hy);
        TextView b_TextView_hy = findViewById(R.id.b_button_hy);
        TextView n_TextView_hy = findViewById(R.id.n_button_hy);
        TextView m_TextView_hy = findViewById(R.id.m_button_hy);
        TextView sh_TextView_hy = findViewById(R.id.sh_button_hy);

        //English keyboard layout
        //Row first
        TextView q_TextView_en = findViewById(R.id.q_button);
        TextView w_TextView_en = findViewById(R.id.w_button);
        TextView e_TextView_en = findViewById(R.id.e_button);
        TextView r_TextView_en = findViewById(R.id.r_button);
        TextView t_TextView_en = findViewById(R.id.t_button);
        TextView y_TextView_en = findViewById(R.id.y_button);
        TextView u_TextView_en = findViewById(R.id.u_button);
        TextView i_TextView_en = findViewById(R.id.i_button);
        TextView o_TextView_en = findViewById(R.id.o_button);
        TextView p_TextView_en = findViewById(R.id.p_button);

        //Row second
        TextView a_TextView_en = findViewById(R.id.a_button);
        TextView s_TextView_en = findViewById(R.id.s_button);
        TextView d_TextView_en = findViewById(R.id.d_button);
        TextView f_TextView_en = findViewById(R.id.f_button);
        TextView g_TextView_en = findViewById(R.id.g_button);
        TextView h_TextView_en = findViewById(R.id.h_button);
        TextView j_TextView_en = findViewById(R.id.j_button);
        TextView k_TextView_en = findViewById(R.id.k_button);
        TextView l_TextView_en = findViewById(R.id.l_button);

        //Row third
        TextView z_TextView_en = findViewById(R.id.z_button);
        TextView x_TextView_en = findViewById(R.id.x_button);
        TextView c_TextView_en = findViewById(R.id.c_button);
        TextView v_TextView_en = findViewById(R.id.v_button);
        TextView b_TextView_en = findViewById(R.id.b_button);
        TextView n_TextView_en = findViewById(R.id.n_button);
        TextView m_TextView_en = findViewById(R.id.m_button);

        //Special
        TextView backspace_TextView_en = findViewById(R.id.backspace_button);
        TextView space_TextView_en = findViewById(R.id.space_button);
        TextView backspace_TextView_hy = findViewById(R.id.backspace_button_hy);
        TextView space_TextView_hy = findViewById(R.id.space_button_hy);

        View.OnClickListener textViewClickListener = v -> {
            if (v instanceof TextView) {
                TextView clickedTextView = (TextView) v;
                String buttonText = clickedTextView.getText().toString().toLowerCase();
                text.append(buttonText);
                wroten_text.setText(text.toString());
                if (checkLastStrings()) {
                    text.setLength(0);
                    wroten_text.setText(" ");
                    startFallingAnimation();
                    score_textView.setText(getString(R.string.score_label) + score);
                }
            }
        };

        backspace_TextView_en.setOnClickListener(v -> {
            if (text.length() > 0) {
                text.deleteCharAt(text.length() - 1);
                wroten_text.setText(text.toString());
            }
        });

        space_TextView_en.setOnClickListener(v -> {
            text.append(" ");
            wroten_text.setText(text.toString());
        });

        backspace_TextView_hy.setOnClickListener(v -> {
            if (text.length() > 0) {
                text.deleteCharAt(text.length() - 1);
                wroten_text.setText(text.toString());
            }
        });

        space_TextView_hy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.append(" ");
                wroten_text.setText(text.toString());
            }
        });

        //Row first
        q_TextView_en.setOnClickListener(textViewClickListener);
        w_TextView_en.setOnClickListener(textViewClickListener);
        e_TextView_en.setOnClickListener(textViewClickListener);
        r_TextView_en.setOnClickListener(textViewClickListener);
        t_TextView_en.setOnClickListener(textViewClickListener);
        y_TextView_en.setOnClickListener(textViewClickListener);
        u_TextView_en.setOnClickListener(textViewClickListener);
        i_TextView_en.setOnClickListener(textViewClickListener);
        o_TextView_en.setOnClickListener(textViewClickListener);
        p_TextView_en.setOnClickListener(textViewClickListener);

        //Row second
        a_TextView_en.setOnClickListener(textViewClickListener);
        s_TextView_en.setOnClickListener(textViewClickListener);
        d_TextView_en.setOnClickListener(textViewClickListener);
        f_TextView_en.setOnClickListener(textViewClickListener);
        g_TextView_en.setOnClickListener(textViewClickListener);
        h_TextView_en.setOnClickListener(textViewClickListener);
        j_TextView_en.setOnClickListener(textViewClickListener);
        k_TextView_en.setOnClickListener(textViewClickListener);
        l_TextView_en.setOnClickListener(textViewClickListener);

        //Row third
        z_TextView_en.setOnClickListener(textViewClickListener);
        x_TextView_en.setOnClickListener(textViewClickListener);
        c_TextView_en.setOnClickListener(textViewClickListener);
        v_TextView_en.setOnClickListener(textViewClickListener);
        b_TextView_en.setOnClickListener(textViewClickListener);
        n_TextView_en.setOnClickListener(textViewClickListener);
        m_TextView_en.setOnClickListener(textViewClickListener);

        //Arm
        es_TextView_hy.setOnClickListener(textViewClickListener);
        th_TextView_hy.setOnClickListener(textViewClickListener);
        ph_TextView_hy.setOnClickListener(textViewClickListener);
        dz_TextView_hy.setOnClickListener(textViewClickListener);
        j_TextView_hy.setOnClickListener(textViewClickListener);
        ev_TextView_hy.setOnClickListener(textViewClickListener);
        rh_TextView_hy.setOnClickListener(textViewClickListener);
        ch_TextView_hy.setOnClickListener(textViewClickListener);
        tch_TextView_hy.setOnClickListener(textViewClickListener);
        jh_TextView_hy.setOnClickListener(textViewClickListener);
        ts_TextView_hy.setOnClickListener(textViewClickListener);

        q_TextView_hy.setOnClickListener(textViewClickListener);
        vo_TextView_hy.setOnClickListener(textViewClickListener);
        e_TextView_hy.setOnClickListener(textViewClickListener);
        r_TextView_hy.setOnClickListener(textViewClickListener);
        t_TextView_hy.setOnClickListener(textViewClickListener);
        yh_TextView_hy.setOnClickListener(textViewClickListener);
        u_TextView_hy.setOnClickListener(textViewClickListener);
        i_TextView_hy.setOnClickListener(textViewClickListener);
        o_TextView_hy.setOnClickListener(textViewClickListener);
        p_TextView_hy.setOnClickListener(textViewClickListener);

        a_TextView_hy.setOnClickListener(textViewClickListener);
        s_TextView_hy.setOnClickListener(textViewClickListener);
        d_TextView_hy.setOnClickListener(textViewClickListener);
        f_TextView_hy.setOnClickListener(textViewClickListener);
        g_TextView_hy.setOnClickListener(textViewClickListener);
        h_TextView_hy.setOnClickListener(textViewClickListener);
        y_TextView_hy.setOnClickListener(textViewClickListener);
        k_TextView_hy.setOnClickListener(textViewClickListener);
        l_TextView_hy.setOnClickListener(textViewClickListener);
        kh_TextView_hy.setOnClickListener(textViewClickListener);

        z_TextView_hy.setOnClickListener(textViewClickListener);
        gh_TextView_hy.setOnClickListener(textViewClickListener);
        c_TextView_hy.setOnClickListener(textViewClickListener);
        v_TextView_hy.setOnClickListener(textViewClickListener);
        b_TextView_hy.setOnClickListener(textViewClickListener);
        n_TextView_hy.setOnClickListener(textViewClickListener);
        m_TextView_hy.setOnClickListener(textViewClickListener);
        sh_TextView_hy.setOnClickListener(textViewClickListener);
    }

    protected void getRandomWord(String range_file) throws IOException {
        if (score <= 1000){
            range = getRangeFromAssets(range_file, 1);
            numberLine = generateNumberLine(range[0], range[1]);
        } else if (score > 1000 && score <= 2500) {
            range = getRangeFromAssets(range_file, 2);
            numberLine = generateNumberLine(range[0], range[1]);
            speed -= 10;
       } else if (score >= 2500 && score <= 5000) {
            range = getRangeFromAssets(range_file, 3);
            numberLine = generateNumberLine(range[0], range[1]);
            speed -= 15;
        } else if (score >= 5000 && score <= 6500) {
            range = getRangeFromAssets(range_file, 4);
            numberLine = generateNumberLine(range[0], range[1]);
            speed -= 20;
        } else {
            range = getRangeFromAssets(range_file, 5);
            numberLine = generateNumberLine(range[0], range[1]);
            speed -= 25;
        }

        word.setText(readRandomWord(words_file, numberLine));
        startFallingAnimation();
    }

    protected void startFallingAnimation() {
        if (!fallingAnimation.hasStarted() || fallingAnimation.hasEnded()) {
            word.clearAnimation();
            fallingAnimation = createFallingAnimation();
            word.startAnimation(fallingAnimation);
        }
    }

    private boolean checkLastStrings() {
        String typedText = text.toString();
        int wordLength = word.length();
        String currentWord = word.getText().toString();

        if (typedText.length() >= wordLength) {
            String lastTypedSubstring = typedText.substring(typedText.length() - wordLength);
            if (lastTypedSubstring.equalsIgnoreCase(currentWord)) {
                try {
                    getRandomWord(range_file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                score += lastTypedSubstring.length() * 10;
                word.clearAnimation();
                word.startAnimation(fallingAnimation);
                return true;
            }
        }
        return false;
    }

    //Animation
    private Animation createFallingAnimation() {
        TranslateAnimation animation = new TranslateAnimation(
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, 0f,
                TranslateAnimation.ABSOLUTE, (float) (screenHeight * 0.62));

        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(speed);
        animation.setRepeatCount(Animation.INFINITE);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // No changes made here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    getRandomWord(range_file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                try {
                    getRandomWord(range_file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                health--;
                health_textView.setText(getString(R.string.health) + health);
                wroten_text.setText(" ");
                text.setLength(0);
                if (health == 0) {
                    word.clearAnimation();
                    animation.setAnimationListener(null);
                    english_layout.setVisibility(View.GONE);
                    armenian_layout.setVisibility(View.GONE);
                    game_over_screen.setVisibility(View.VISIBLE);
                    score_gameOver.setText(getString(R.string.game_over));
                    userLoginManager.saveHighscore(score);
                    if (!userLoginManager.getUserId().equals("")) {
                        if (score > 500) {
                            String[] field = new String[2];
                            field[0] = "id";
                            field[1] = "score";
                            String[] data = new String[2];
                            data[0] = id;
                            data[1] = String.valueOf(score);

                            PutData putData = new PutData("https://koryun.gaboyan.am/app1/login/record.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    Toast.makeText(MainGameActivity.this, "New record saved!", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            word.clearAnimation();
                            animation.setAnimationListener(null);
                            english_layout.setVisibility(View.GONE);
                            armenian_layout.setVisibility(View.GONE);
                            global.setVisibility(View.GONE);
                            game_over_screen.setVisibility(View.VISIBLE);
                            score_gameOver.setText(R.string.game_over);
                            Toast.makeText(MainGameActivity.this, "You need to get more than 500 score to try to be in the leaderboard.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        word.clearAnimation();
                        animation.setAnimationListener(null);
                        english_layout.setVisibility(View.GONE);
                        armenian_layout.setVisibility(View.GONE);
                        global.setVisibility(View.GONE);
                        game_over_screen.setVisibility(View.VISIBLE);
                        score_gameOver.setText(R.string.game_over);
                        Toast.makeText(MainGameActivity.this, "Log-in to save your record!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return animation;
    }

    private void setKeyHeightForLinearLayout(LinearLayout linearLayout, int height) {
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = linearLayout.getChildAt(i);
            if (child instanceof TextView) {
                child.getLayoutParams().height = height;
            }
        }
    }

    public int[] getRangeFromAssets(String fileName, int range_line) throws IOException {
        try (InputStream inputStream = getAssets().open(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            int currentLine = 0;

            while ((line = br.readLine()) != null) {
                currentLine++;
                if (currentLine == range_line) {
                    String[] range = line.split("-");
                    return new int[]{Integer.parseInt(range[0].trim()), Integer.parseInt(range[1].trim())};
                }
            }

            // If the specified line is not found
            throw new IOException("Line " + range_line + " not found in file: " + fileName);
        } catch (IOException e) {
            // Handle any IOException that may occur
            throw new IOException("Error reading from assets: " + fileName, e);
        } catch (NumberFormatException e) {
            // Handle the case where parsing to int fails
            throw new IOException("Invalid number format in the file", e);
        }
    }


    private String readRandomWord(String file_name, int lineNumber) throws IOException {
        try (InputStream inputStream = getAssets().open(file_name);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            for (int i = 0; i < lineNumber; i++) {
                br.readLine();
            }
            return br.readLine();
        } catch (IOException e) {
            throw new IOException("Error reading from assets: " + file_name, e);
        }
    }

    public static int generateNumberLine(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}

