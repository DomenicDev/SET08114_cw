package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class ScoreComponent implements EntityComponent {

    private int score;

    public ScoreComponent() {
        this.score = 0;
    }

    public ScoreComponent(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
