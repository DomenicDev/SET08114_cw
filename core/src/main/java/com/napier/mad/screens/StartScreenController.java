package com.napier.mad.screens;

import com.napier.mad.appstates.NiftyAppState;

import javax.annotation.Nonnull;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenController implements ScreenController {

    public NiftyAppState niftyAppState;

    public StartScreenController(NiftyAppState niftyAppState) {
        this.niftyAppState = niftyAppState;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {

    }

    /**
     * Called from GUI
     */
    public void onClickStartGame() {
        niftyAppState.startGame();
    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}
