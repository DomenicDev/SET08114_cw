package com.napier.mad.screens;

import javax.annotation.Nonnull;
import javax.xml.soap.Text;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class GameOverScreenController implements ScreenController {

    private GuiEventListener eventListener;
    private Element scoreText;

    private int score = -1;

    public GameOverScreenController(GuiEventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void bind(@Nonnull Nifty nifty, @Nonnull Screen screen) {
        this.scoreText = screen.findElementById("game_over_score_result");
        updateScore();
    }

    public void setScore(int score) {
        this.score = score;
        updateScore();
    }

    private void updateScore() {
        if (scoreText != null) {
            TextRenderer textRenderer = scoreText.getRenderer(TextRenderer.class);
            if (textRenderer != null) {
                textRenderer.setText("Your score: " + score);
            }
        }
    }

    @Override
    public void onStartScreen() {
        updateScore();
    }

    public void onClickRestart() {
        this.eventListener.restartGame();
    }

    public void onClickBackToMenu() {
        this.eventListener.stopGame();
    }

    @Override
    public void onEndScreen() {}
}
