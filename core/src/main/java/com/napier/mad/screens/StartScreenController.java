package com.napier.mad.screens;

import javax.annotation.Nonnull;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class StartScreenController implements ScreenController {

    private GuiEventListener eventListener;

    public StartScreenController(GuiEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {

    }

    /**
     * Called from GUI
     */
    public void onClickStartGame() {
        eventListener.startGame();
    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}
