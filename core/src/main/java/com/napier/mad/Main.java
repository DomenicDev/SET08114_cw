package com.napier.mad;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.napier.mad.appstates.GameAppStateInitializer;

public class Main extends SimpleApplication {

    @Override
    public void simpleInitApp() {
        getFlyByCamera().setEnabled(false);
        stateManager.attach(new GameAppStateInitializer());
    }

    @Override
    public void simpleUpdate(float tpf) {}

    @Override
    public void simpleRender(RenderManager rm) {}
}
