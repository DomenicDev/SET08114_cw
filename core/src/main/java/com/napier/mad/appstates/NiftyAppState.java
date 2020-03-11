package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.napier.mad.game.GameEventListener;
import com.napier.mad.game.PlayerStatistics;
import com.napier.mad.screens.GameOverScreenController;
import com.napier.mad.screens.GuiEventListener;
import com.napier.mad.screens.HudScreenController;
import com.napier.mad.screens.StartScreenController;

import de.lessvoid.nifty.Nifty;

public class NiftyAppState extends BaseAppState implements GameEventListener {

    private MainGameAppState mainGameAppState;
    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;

    private StartScreenController startScreenController;
    private HudScreenController hudScreenController;
    private GameOverScreenController gameOverScreenController;

    private GuiEventListener niftyListener = new NiftyGuiEventHandler();

    @Override
    protected void initialize(Application app) {
        this.mainGameAppState = getState(MainGameAppState.class);
        this.niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();

        // create controller instances
        this.startScreenController = new StartScreenController(niftyListener);
        this.hudScreenController = new HudScreenController(niftyListener);
        this.gameOverScreenController = new GameOverScreenController(niftyListener);

        // setup nifty screens
        nifty.fromXml("Interface/Screens/screens.xml", "hud",
                startScreenController,
                hudScreenController,
                gameOverScreenController);

        // make nifty visible
        nifty.setDebugOptionPanelColors(false);
        app.getGuiViewPort().addProcessor(niftyDisplay);

        // start listening on game events
        this.mainGameAppState.addGameEventListener(this);
    }

    @Override
    protected void cleanup(Application app) {
        app.getGuiViewPort().removeProcessor(niftyDisplay);
        mainGameAppState.removeGameEventListener(this);
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @Override
    public void onGameStarted() {
        nifty.gotoScreen("hud");
    }

    @Override
    public void onScoreChanged(int newScore) {
        this.hudScreenController.setScore(newScore);
    }

    @Override
    public void onGameOver(PlayerStatistics playerStatistics) {
        nifty.gotoScreen("game_over");
        gameOverScreenController.setScore(playerStatistics.getScore());
    }

    private class NiftyGuiEventHandler implements GuiEventListener {

        @Override
        public void startGame() {
            mainGameAppState.startGame();
        }

        @Override
        public void restartGame() {
            mainGameAppState.restartGame();
        }

        @Override
        public void pauseGame() {
            mainGameAppState.togglePauseGame();
        }

        @Override
        public void stopGame() {
            mainGameAppState.stopGame();
        }
    }
}
