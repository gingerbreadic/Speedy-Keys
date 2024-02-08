package com.gingerbread.typingchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView profile_score, profile_name;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button logoutButton = view.findViewById(R.id.logoutButton);
        profile_score = view.findViewById(R.id.profile_score);
        profile_name = view.findViewById(R.id.profile_name);
        UserLoginManager userLoginManager = new UserLoginManager(requireContext());

        profile_score.setText(getString(R.string.high_score) + userLoginManager.getUsersHighScore());
        profile_name.setText(getString(R.string.username) + " : " + userLoginManager.getUsername());

        if (userLoginManager.getUserId().equals("")){
            logoutButton.setText("Log-in");
        }
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout();
            }
        });
        return view;
    }

    private void handleLogout() {
        UserLoginManager userLoginManager = new UserLoginManager(requireContext());
        userLoginManager.clearLoginState();

        Intent intent = new Intent(requireContext(), loginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}
