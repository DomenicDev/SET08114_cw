package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.napier.mad.screens.MainGameScreenController;

import de.lessvoid.nifty.Nifty;

public class NiftyAppState extends BaseAppState {

    private NiftyJmeDisplay niftyDisplay;
    private Nifty nifty;

    @Override
    protected void initialize(Application app) {
        this.niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        this.nifty = niftyDisplay.getNifty();
        this.nifty.fromXml("Interface/Screens/screens.xml", "start",
                new MainGameScreenController());

        nifty.setDebugOptionPanelColors(true);
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
}
