package com.napier.mad.android.input;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.napier.mad.appstates.GameInputAppState;

public class JumpBySwipeInputAppState extends BaseAppState implements TouchListener {

    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private static final String TOUCH_ALL = "TOUCH_ALL";
    private GameInputAppState gameInputAppState;
    private InputManager inputManager;

    @Override
    protected void initialize(Application app) {
        this.gameInputAppState = getState(GameInputAppState.class);
        this.inputManager = app.getInputManager();
        this.inputManager.addMapping(TOUCH_ALL, new TouchTrigger(TouchInput.ALL));
        this.inputManager.addListener(this, TOUCH_ALL);
    }

    private void jump() {
        gameInputAppState.jump();
    }

    @Override
    protected void cleanup(Application app) {
        this.inputManager.deleteMapping(TOUCH_ALL);
        this.inputManager.removeListener(this);
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}


    @Override
    public void onTouch(String name, TouchEvent event, float tpf) {
        if (event.getType().equals(TouchEvent.Type.FLING)) {
            float deltaY = event.getDeltaY();
            float movementY = Math.abs(event.getDeltaY());
            if (movementY >= SWIPE_VELOCITY_THRESHOLD) {
                if (deltaY < 0) {
                    jump();
                }
            }
        }
    }
}
