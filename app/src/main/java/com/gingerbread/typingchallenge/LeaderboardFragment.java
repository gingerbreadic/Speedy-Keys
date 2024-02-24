package com.gingerbread.typingchallenge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class LeaderboardFragment extends Fragment {
    private RecyclerView recyclerView;
    private LeaderboardAdapter leaderboardAdapter;
    private ArrayList<LeaderboardItem> leaderboardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recyclerView);
        leaderboardList = new ArrayList<>();
        leaderboardAdapter = new LeaderboardAdapter(leaderboardList);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(leaderboardAdapter);

        // Populate leaderboard data (you need to replace this with your data retrieval logic)
        populateLeaderboardData();

        return view;
    }

    private void populateLeaderboardData() {
        // TODO: Fetch leaderboard data from your data source


        // Add leaderboard items to leaderboardList
        leaderboardList.add(new LeaderboardItem("Player 1", 1000));
        leaderboardList.add(new LeaderboardItem("Player 2", 900));
        leaderboardList.add(new LeaderboardItem("Player 2", 1200));
        leaderboardList.add(new LeaderboardItem("Player 3", 800));

        // Notify the adapter that the data set has changed
        leaderboardAdapter.notifyDataSetChanged();
    }
}
