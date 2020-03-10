package com.napier.mad;

public class GameResult {

    private String playerName;
    private int score;

    public GameResult(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "playerName='" + playerName + '\'' +
                ", score=" + score +
                '}';
    }
}
