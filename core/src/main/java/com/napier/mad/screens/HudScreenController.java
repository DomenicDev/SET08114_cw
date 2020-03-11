package com.napier.mad.screens;

import javax.annotation.Nonnull;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class HudScreenController implements ScreenController {

    private Screen screen;
    private GuiEventListener listener;

    public HudScreenController(GuiEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.screen = screen;
    }

    public void setScore(int score) {
        if (screen == null) return;
        Element scoreElement =  screen.findElementById("score");
        if (scoreElement == null) return;
        TextRenderer textRenderer = scoreElement.getRenderer(TextRenderer.class);
        if (textRenderer != null)  {
            textRenderer.setText(String.valueOf(score));
        }

    }

    public void onClickPauseGame() {
        listener.pauseGame();
    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}
