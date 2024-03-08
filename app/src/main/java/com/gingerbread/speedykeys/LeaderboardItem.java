package com.gingerbread.speedykeys;

public class LeaderboardItem {
    private String playerName;
    private int score;

    public LeaderboardItem(String playerName, String score) {
        this.playerName = playerName;
        this.score = Integer.parseInt(score);
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
