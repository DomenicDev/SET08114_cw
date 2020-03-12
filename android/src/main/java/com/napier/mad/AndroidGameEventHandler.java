package com.napier.mad;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.jme.game.R;
import com.napier.mad.game.GameEventListener;
import com.napier.mad.game.PlayerStatistics;

public class AndroidGameEventHandler implements GameEventListener {

    private PlayerStatsSQLiteDBHelper db;
    private FirebaseGameScoreHandler globalDbHandler;
    private Activity activity;

    AndroidGameEventHandler(Activity activity) {
        this.activity = activity;
        this.db = new PlayerStatsSQLiteDBHelper(activity);
        this.globalDbHandler = new FirebaseGameScoreHandler(activity);
    }

    @Override
    public void onGameStarted() {}

    @Override
    public void onScoreChanged(int newScore) {}

    @Override
    public void onGameOver(PlayerStatistics playerStatistics) {
        // we want to upload the players score to a database
        storeOnLocalDatabase(playerStatistics);

        // upload the score on firebase database
        uploadScoreToGlobalDatabase(playerStatistics);
    }

    private void storeOnLocalDatabase(PlayerStatistics playerStatistics) {
        String username = getPlayerName();
        this.db.insertResult(username, playerStatistics.getScore());
        System.out.println(this.db.getResults());
    }

    private void uploadScoreToGlobalDatabase(PlayerStatistics playerStatistics) {
        String username = getPlayerName();
        this.globalDbHandler.uploadScore(username, playerStatistics.getScore());
    }

    private String getPlayerName() {
        return SharedPreferencesHandler.getInstance().readPlayerName(this.activity);
    }

}
