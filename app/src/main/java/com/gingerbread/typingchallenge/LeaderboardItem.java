package com.gingerbread.typingchallenge;

public class LeaderboardItem {
    private String playerName;
    private int score;

    public LeaderboardItem(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
