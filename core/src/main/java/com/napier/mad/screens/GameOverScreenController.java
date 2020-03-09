package com.napier.mad.screens;

import javax.annotation.Nonnull;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GameOverScreenController implements ScreenController {

    public GuiEventListener eventListener;

    public GameOverScreenController(GuiEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {

    }

    @Override
    public void onStartScreen() {}

    public void onClickRestart() {
        this.eventListener.restartGame();
    }

    public void onClickBackToMenu() {
        this.eventListener.stopGame();
    }

    @Override
    public void onEndScreen() {}
}
