package com.napier.mad;

import com.napier.mad.game.GameEventListener;
import com.napier.mad.game.PlayerStatistics;

public class AndroidGameEventHandler implements GameEventListener {

    @Override
    public void onGameStarted() {

    }

    @Override
    public void onScoreChanged(int newScore) {

    }

    @Override
    public void onGameOver(PlayerStatistics playerStatistics) {
        // TODO
        // we want to upload the players score to a database

    }

}
