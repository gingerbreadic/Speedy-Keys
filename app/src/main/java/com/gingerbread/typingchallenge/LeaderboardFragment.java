package com.gingerbread.typingchallenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LeaderboardFragment extends Fragment {
    String putDataResult;
    String[] result_usernames;
    String[] result_scores;
    String[] splittedResult;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;
    private ArrayList<LeaderboardItem> leaderboardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        progressBar = view.findViewById(R.id.leaderboard_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.INVISIBLE);
        leaderboardList = new ArrayList<>();
        leaderboardAdapter = new LeaderboardAdapter(leaderboardList);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(leaderboardAdapter);

        // Populate leaderboard data (you need to replace this with your data retrieval logic)

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        populateLeaderboardData();
                    }
                });
            }
        }, 500);


        return view;
    }

    private void populateLeaderboardData() {
        PutData putData = new PutData("https://koryun.gaboyan.am/app1/login/top.php", "POST", new String[]{""}, new String[]{""});
        if (putData.startPut()) {
            // Loop until data retrieval is complete
            while (!putData.onComplete()) {
                try {
                    // Add a delay to avoid continuous checking
                    Thread.sleep(1000); // Adjust the delay time as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Once data retrieval is complete
            putDataResult = putData.getResult();
            splittedResult = putDataResult.split(",");
            result_usernames = new String[splittedResult.length];
            result_scores = new String[splittedResult.length];
            for (int i = 0; i < splittedResult.length; i++) {
                String[] parts = splittedResult[i].split(";");
                result_usernames[i] = parts[0];
                result_scores[i] = parts[1];
            }

            // Move UI updates to the main UI thread
            if (isAdded() && getActivity() != null) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        for (int i = 0; i < splittedResult.length; i++) {
                            leaderboardList.add(new LeaderboardItem(result_usernames[i], result_scores[i]));
                        }
                        // Notify adapter after data population
                        leaderboardAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }
}
