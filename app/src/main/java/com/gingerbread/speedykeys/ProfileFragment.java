package com.gingerbread.speedykeys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    TextView profile_score, profile_name;

    RelativeLayout signed, not_signed;
    UserLoginManager userLoginManager;

    Button login_profile;

    @SuppressLint("WrongViewCast")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userLoginManager = new UserLoginManager(getContext());

        login_profile = view.findViewById(R.id.login_profile);

        not_signed = view.findViewById(R.id.not_signed);
        signed = view.findViewById(R.id.signed);

        if (userLoginManager.isLoggedIn()){
            signed.setVisibility(View.VISIBLE);
            not_signed.setVisibility(View.INVISIBLE);
        }else {
            not_signed.setVisibility(View.VISIBLE);
            signed.setVisibility(View.INVISIBLE);
        }

        login_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), loginActivity.class));
            }
        });

        Button logoutButton = view.findViewById(R.id.logoutButton);
        profile_score = view.findViewById(R.id.profile_score);
        profile_name = view.findViewById(R.id.profile_name);
        UserLoginManager userLoginManager = new UserLoginManager(requireContext());


        logoutButton.setText(getString(R.string.log_out));

        profile_score.setText(getString(R.string.high_score) + userLoginManager.getUsersHighScore());
        profile_name.setText(getString(R.string.username) + " : " + userLoginManager.getUsername());

        if (userLoginManager.getUserId().equals("")){
            logoutButton.setText(getString(R.string.log_in));
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
