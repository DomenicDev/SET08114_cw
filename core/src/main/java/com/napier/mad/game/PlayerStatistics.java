package com.napier.mad.game;

public class PlayerStatistics {

    private int score;
    private int travelledDistance;

    public PlayerStatistics(int score, int travelledDistance) {
        this.score = score;
        this.travelledDistance = travelledDistance;
    }

    public int getScore() {
        return score;
    }

    public int getTravelledDistance() {
        return travelledDistance;
    }
}
