package com.napier.mad;

import android.content.Context;

import com.napier.mad.game.GameEventListener;
import com.napier.mad.game.PlayerStatistics;

public class AndroidGameEventHandler implements GameEventListener {

    private PlayerStatsSQLiteDBHelper db;
    private FirebaseGameScoreHandler globalDbHandler;

    AndroidGameEventHandler(Context context) {
        this.db = new PlayerStatsSQLiteDBHelper(context);
        this.globalDbHandler = new FirebaseGameScoreHandler(context);
    }

    @Override
    public void onGameStarted() {

    }

    @Override
    public void onScoreChanged(int newScore) {

    }

    @Override
    public void onGameOver(PlayerStatistics playerStatistics) {
        // we want to upload the players score to a database
        storeOnLocalDatabase(playerStatistics);

        // upload the score on firebase database
        uploadScoreToGlobalDatabase(playerStatistics);
    }

    private void storeOnLocalDatabase(PlayerStatistics playerStatistics) {
        String username = "Domenic"; // todo: read name from settings or so
        this.db.insertResult(username, playerStatistics.getScore());
        System.out.println(this.db.getResults());
    }

    private void uploadScoreToGlobalDatabase(PlayerStatistics playerStatistics) {
        String username = "Domenic";
        this.globalDbHandler.uploadScore(username, playerStatistics.getScore());
    }

}
