package com.napier.mad.screens;

import javax.annotation.Nonnull;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class HudScreenController implements ScreenController {

    private Element textElement;

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.textElement = screen.findElementById("score");
    }

    public void setScore(int score) {
        this.textElement.getRenderer(TextRenderer.class).setText("Score: " + score);
    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}
