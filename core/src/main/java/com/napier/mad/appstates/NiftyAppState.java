package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.napier.mad.screens.MainGameScreenController;
import com.napier.mad.screens.ScreenEventListener;

import de.lessvoid.nifty.Nifty;

public class NiftyAppState extends BaseAppState implements ScreenEventListener {

    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;

    private InputAppState inputAppState;

    @Override
    protected void initialize(Application app) {
        this.inputAppState = getState(InputAppState.class);

        this.niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        this.nifty = niftyDisplay.getNifty();
        this.nifty.fromXml("Interface/Screens/screens.xml", "start",
                new MainGameScreenController(this));

        nifty.setDebugOptionPanelColors(false);
        app.getGuiViewPort().addProcessor(niftyDisplay);
    }

    @Override
    protected void cleanup(Application app) {
        app.getGuiViewPort().removeProcessor(niftyDisplay);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void onSwipeLeft() {
        this.inputAppState.onSwipeLeft();
    }

    @Override
    public void onSwipeRight() {
        this.inputAppState.onSwipeRight();
    }
}
