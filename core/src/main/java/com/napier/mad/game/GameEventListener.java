package com.napier.mad.game;

public interface GameEventListener {

    void onGameStarted();

    void onScoreChanged(int newScore);

    void onGameOver();

}
