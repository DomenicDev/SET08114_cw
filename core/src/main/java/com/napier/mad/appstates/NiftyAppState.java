package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;

public class NiftyAppState extends BaseAppState {

    private NiftyJmeDisplay niftyDisplay;

    @Override
    protected void initialize(Application app) {
        this.niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(), app.getInputManager(), app.getAudioRenderer(), app.getGuiViewPort());
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/Screens/screens.xml", "start");

        nifty.setDebugOptionPanelColors(false);
        app.getGuiViewPort().addProcessor(niftyDisplay);
    }

    @Override
    protected void cleanup(Application app) {
        app.getGuiViewPort().removeProcessor(niftyDisplay);
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}

}
