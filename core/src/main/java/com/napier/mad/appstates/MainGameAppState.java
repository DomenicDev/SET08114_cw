package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.game.GameEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainGameAppState extends BaseAppState {

    private int score = 0;
    private GameAppStateInitializer initializer;
    private NiftyAppState niftyAppState;

    private List<GameEventListener> gameEventListeners = new ArrayList<>();

    @Override
    protected void initialize(Application app) {
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

    public void addToScore(int scorePoints) {
        this.score += scorePoints;
        for (GameEventListener listener : gameEventListeners) {
            listener.onScoreChanged(getScore());
        }
    }

    public int getScore() {
        return score;
    }

    public void startGame() {
        getStateManager().attach(new GameStarterAppState());
    }

    public void setGameOver() {

    }

    @Override
    protected void cleanup(Application app) {
        getStateManager().detach(initializer);
        getStateManager().detach(niftyAppState);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
