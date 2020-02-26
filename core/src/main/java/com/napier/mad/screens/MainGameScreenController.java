package com.napier.mad.screens;

import javax.annotation.Nonnull;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainGameScreenController implements ScreenController {

    private ScreenEventListener listener;


    public MainGameScreenController(ScreenEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        Button btnLeft = screen.findNiftyControl("leftDrift", Button.class);
    }

    public void onClickLeft() {
        System.out.println("LEFT BUTTON CLICKED");
        this.listener.onSwipeLeft();
    }

    public void onClickRight() {
        System.out.println("RIGHT BUTTON CLICKED");
        this.listener.onSwipeRight();
    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}