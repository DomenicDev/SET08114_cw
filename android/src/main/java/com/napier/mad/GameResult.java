package com.napier.mad;

public class GameResult {

    private String playerName;
    private Long score;

    public GameResult(String playerName, Long score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Long getScore() {
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
