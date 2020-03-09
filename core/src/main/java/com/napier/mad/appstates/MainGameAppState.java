package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.game.GameEventListener;
import com.napier.mad.game.PlayerStatistics;

import java.util.ArrayList;
import java.util.List;

public class MainGameAppState extends BaseAppState {

    private int score = 0;
    private GameAppStateInitializer initializer;
    private NiftyAppState niftyAppState;
    private Application app;

    private List<GameEventListener> gameEventListeners = new ArrayList<>();

    @Override
    protected void initialize(Application app) {
        this.app = app;
        this.initializer = new GameAppStateInitializer();
        getStateManager().attach(initializer);

        this.niftyAppState = new NiftyAppState();
        getStateManager().attach(niftyAppState);
    }

    public void addGameEventListener(GameEventListener listener) {
        this.gameEventListeners.add(listener);
    }

    public void removeGameEventListener(GameEventListener listener) {
        this.gameEventListeners.remove(listener);
    }

    public void setNewScore(int newScore) {
        this.score = newScore;
        for (GameEventListener listener : gameEventListeners) {
            listener.onScoreChanged(getScore());
        }
    }

    public int getScore() {
        return score;
    }

    public void startGame() {
        for (GameEventListener listener : gameEventListeners) {
            listener.onGameStarted();
        }
    }

    public void restartGame() {
        getStateManager().detach(initializer);
        this.initializer = new GameAppStateInitializer();
        getStateManager().attach(initializer);
        startGame();
    }

    public void setGameOver() {
        initializer.setEnabled(false);
        PlayerStatistics playerStats = new PlayerStatistics(getScore(), 1000);
        for (GameEventListener listener : gameEventListeners) {
            listener.onGameOver(playerStats);
        }
    }


    public void togglePauseGame() {
        initializer.setEnabled(!initializer.isEnabled());
    }

    public void stopGame() {
        app.stop();
    }

    @Override
    protected void cleanup(Application app) {
        getStateManager().detach(initializer);
        getStateManager().detach(niftyAppState);
    }

    @Override
    protected void onEnable() {
        startGame();
    }

    @Override
    protected void onDisable() {

    }

}
