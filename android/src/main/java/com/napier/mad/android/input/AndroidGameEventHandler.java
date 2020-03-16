package com.napier.mad.android.input;

import android.app.Activity;

import com.jme3.app.Application;
import com.napier.mad.appstates.GameInputAppState;
import com.napier.mad.game.GameEventListener;
import com.napier.mad.game.PlayerStatistics;
import com.napier.mad.android.persistence.FirebaseGameScoreHandler;
import com.napier.mad.android.persistence.PlayerStatsSQLiteDBHelper;
import com.napier.mad.android.persistence.SharedPreferencesHandler;

public class AndroidGameEventHandler implements GameEventListener, GameSensorManager.GameSensorListener {

    private final GameSensorManager gameSensorManager;
    private PlayerStatsSQLiteDBHelper db;
    private FirebaseGameScoreHandler globalDbHandler;
    private Activity activity;
    private Application app;

    private JumpBySwipeInputAppState jumpAppState;

    public AndroidGameEventHandler(Application app, Activity activity) {
        // assign global attributes
        this.app = app;
        this.activity = activity;

        // init persistence objects
        this.db = new PlayerStatsSQLiteDBHelper(activity);
        this.globalDbHandler = new FirebaseGameScoreHandler(activity);

        // setup game sensor for swiping control
        this.gameSensorManager = new GameSensorManager(activity, this);
    }

    @Override
    public void onGameStarted() {
        if (jumpAppState != null) {
            app.getStateManager().detach(jumpAppState);
        }
        this.jumpAppState = new JumpBySwipeInputAppState();
        app.getStateManager().attach(jumpAppState);
    }

    @Override
    public void onScoreChanged(int newScore) {}

    @Override
    public void onGameOver(PlayerStatistics playerStatistics) {
        // we want to upload the players score to a database
        storeOnLocalDatabase(playerStatistics);

        // upload the score on firebase database
        uploadScoreToGlobalDatabase(playerStatistics);
    }

    @Override
    public void onGameStop() {
        this.gameSensorManager.cleanup();
        app.getStateManager().detach(jumpAppState);
    }

    private void storeOnLocalDatabase(PlayerStatistics playerStatistics) {
        String username = getPlayerName();
        this.db.insertResult(username, playerStatistics.getScore());
    }

    private void uploadScoreToGlobalDatabase(PlayerStatistics playerStatistics) {
        String username = getPlayerName();
        this.globalDbHandler.uploadScore(username, playerStatistics.getScore());
    }

    private String getPlayerName() {
        return SharedPreferencesHandler.getInstance().readPlayerName(this.activity);
    }

    private GameInputAppState getGameInputAppState() {
        return app.getStateManager().getState(GameInputAppState.class);
    }

    @Override
    public void onAccelerationChanged(float acceleration) {
        GameInputAppState gameInputAppState = getGameInputAppState();
        if (gameInputAppState == null) {
            return;
        }
        gameInputAppState.moveSidewards(acceleration);
    }

}
