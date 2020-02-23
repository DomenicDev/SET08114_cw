package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public class CameraAppState extends BaseAppState {

    private Camera cam;

    private static final Vector3f START_LOCATION = new Vector3f(0, 0.5f, 10);

    @Override
    protected void initialize(Application app) {
        this.cam = app.getCamera();
        this.cam.setLocation(START_LOCATION);
        this.cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        this.cam.setParallelProjection(false);
        this.cam.setFrustumPerspective(90f, 19.5f/9.0f, 0.2f, 50f);
        this.cam.resize(cam.getWidth(), cam.getHeight(), true);
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
