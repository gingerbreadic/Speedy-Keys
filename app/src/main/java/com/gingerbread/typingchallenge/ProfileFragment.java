package com.gingerbread.typingchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button logoutButton = view.findViewById(R.id.logoutButton);
        UserLoginManager userLoginManager = new UserLoginManager(requireContext());

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
