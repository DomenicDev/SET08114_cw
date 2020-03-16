package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.ChaseCameraAppState;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.simsilica.es.EntityId;

public class CameraAppState extends BaseAppState {

    private Camera cam;
    private InputManager inputManager;
    private CameraNode camNode;

    private static final Vector3f START_LOCATION = new Vector3f(0, 5f, -10);

    private Spatial spatialToChase;

    @Override
    protected void initialize(Application app) {
        this.inputManager = app.getInputManager();

        // init camera and apply settings
        this.cam = app.getCamera();
        this.cam.setLocation(START_LOCATION);
        this.cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        this.cam.setParallelProjection(false);
        this.cam.setFrustumPerspective(95f, (float) cam.getWidth() / cam.getHeight(), 0.2f, 150f);
        this.cam.resize(cam.getWidth(), cam.getHeight(), true);
    }

    public void chase(Spatial spatialToChase) {
        this.camNode = new CameraNode("CamNode", cam);
        this.spatialToChase = spatialToChase;
        ((Node) spatialToChase).attachChild(camNode);
        this.camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        this.camNode.setLocalTranslation(0, 2.0f, -1);
        this.camNode.setEnabled(true);
        this.camNode.lookAt(spatialToChase.getWorldTranslation(), Vector3f.UNIT_Y);
    }

    @Override
    public void update(float tpf) {
        if (spatialToChase == null) {
            return;
        }
        // update location and look at to avoid camera issues
        this.camNode.setLocalTranslation(0, 2.8f, -2.2f);
        this.camNode.lookAt(spatialToChase.getWorldTranslation().add(0, 2.0f, 0), Vector3f.UNIT_Y);
    }


    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
