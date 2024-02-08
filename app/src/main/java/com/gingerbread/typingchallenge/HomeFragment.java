package com.gingerbread.typingchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment{
    private Button play_game_button;
    private UserLoginManager userLoginManager;
    String userId;
    String username;
    String highscore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        userLoginManager = new UserLoginManager(getContext());
        play_game_button = view.findViewById(R.id.play_game_button);

        userId = userLoginManager.getUserId();
        username = userLoginManager.getUsername();
        highscore = userLoginManager.getUsersHighScore();

        play_game_button.setText(String.valueOf(highscore));

        play_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainGameActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        return view;
    }
}
