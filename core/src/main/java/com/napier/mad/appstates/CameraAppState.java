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

    private static final Vector3f START_LOCATION = new Vector3f(0, 0.5f, 10);

    private EntityId entityToChase;
    private Spatial spatialToChase;

    private VisualisationAppState visualisationAppState;

    @Override
    protected void initialize(Application app) {
        this.inputManager = app.getInputManager();

        // init camera and apply settings
        this.cam = app.getCamera();
        this.cam.setLocation(START_LOCATION);
        this.cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        this.cam.setParallelProjection(false);
        this.cam.setFrustumPerspective(90f, 19.5f/9.0f, 0.2f, 50f);
        this.cam.resize(cam.getWidth(), cam.getHeight(), true);

        // get application states for later use
        this.visualisationAppState = getStateManager().getState(VisualisationAppState.class);
    }

    public void chase(EntityId entityId) {
        this.entityToChase = entityId;
    }

    @Override
    public void update(float tpf) {
        if (entityToChase != null) {
            if (spatialToChase == null) {
                // load reference to spatial to chase
                spatialToChase = this.visualisationAppState.getModel(entityToChase);
                // chase
                this.camNode = new CameraNode("Camera Node", cam);
//This mode means that camera copies the movements of the target:
                camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
//Attach the camNode to the target:
                ((Node) spatialToChase).attachChild(camNode);
//Move camNode, e.g. behind and above the target:
                camNode.setLocalTranslation(new Vector3f(-0, 5, -8));
//Rotate the camNode to look at the target:
                camNode.lookAt(spatialToChase.getLocalTranslation(), Vector3f.UNIT_Y);
                /*
                ChaseCamera chaseCamera = new ChaseCamera(cam, spatialToChase, inputManager);
                chaseCamera.setSmoothMotion(true);
                chaseCamera.setDragToRotate(false);
                chaseCamera.setMinDistance(5);
                chaseCamera.setZoomSensitivity(1000);
                chaseCamera.setDownRotateOnCloseViewOnly(false);
                chaseCamera.setChasingSensitivity(300);
                chaseCamera.setDefaultDistance(5);
                chaseCamera.setMaxDistance(5);
                chaseCamera.setTrailingEnabled(false);

                 */
                return;
            } else {

                if (camNode != null) {

                }


            }


        }
    }


    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
