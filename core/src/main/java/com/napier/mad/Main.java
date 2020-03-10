package com.napier.mad;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.Collidable;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.napier.mad.appstates.GameAppStateInitializer;
import com.napier.mad.appstates.MainGameAppState;
import com.napier.mad.game.GameEventListener;

public class Main extends SimpleApplication {

    private GameEventListener gameEventListener;

    public Main() {
    }

    public Main(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    @Override
    public void simpleInitApp() {
        getFlyByCamera().setEnabled(false);

        // create main game app state
        MainGameAppState mainGameAppState = new MainGameAppState();
        if (gameEventListener != null) mainGameAppState.addGameEventListener(gameEventListener);
        stateManager.attach(mainGameAppState);
    }

    @Override
    public void simpleUpdate(float tpf) {}

    @Override
    public void simpleRender(RenderManager rm) {}
}
