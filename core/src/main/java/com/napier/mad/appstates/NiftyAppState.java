package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.napier.mad.game.GameEventListener;
import com.napier.mad.screens.HudScreenController;
import com.napier.mad.screens.StartScreenController;

import de.lessvoid.nifty.Nifty;

public class NiftyAppState extends BaseAppState implements GameEventListener {

    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;

    private StartScreenController startScreenController;
    private HudScreenController hudScreenController;

    @Override
    protected void initialize(Application app) {
        this.niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();

        // create controller instances
        this.startScreenController = new StartScreenController(this);
        this.hudScreenController = new HudScreenController();

        // setup nifty screens
        nifty.fromXml("Interface/Screens/screens.xml", "start",
                startScreenController,
                hudScreenController);

        // make nifty visible
        nifty.setDebugOptionPanelColors(false);
        app.getGuiViewPort().addProcessor(niftyDisplay);

        // start listening on game events
        getState(MainGameAppState.class).addGameEventListener(this);
    }

    public void startGame() {
        MainGameAppState mainGameAppState = getState(MainGameAppState.class);
        if (mainGameAppState == null) {
            return;
        }
        mainGameAppState.startGame();
        nifty.gotoScreen("hud");
    }

    @Override
    protected void cleanup(Application app) {
        app.getGuiViewPort().removeProcessor(niftyDisplay);
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

    @Override
    public void onGameStarted() {

    }

    @Override
    public void onScoreChanged(int newScore) {
        this.hudScreenController.setScore(newScore);
    }

    @Override
    public void onGameOver() {

    }
}
