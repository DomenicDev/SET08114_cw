package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;


public class GameAppStateInitializer extends BaseAppState {

    private EntityDataAppState entityDataAppState;
    private CameraAppState cameraAppState;
    private PhysicsAppState physicsAppState;
    private PhysicsPushAppState physicsPushAppState;
    private PhysicalCharacterMovement physicalCharacterMovement;
    private SceneAppState sceneAppState;
    private VisualisationAppState visualisationAppState;
    private SkyAppState skyAppState;
    private InputAppState inputAppState;
    private NiftyAppState niftyAppState;

    private AppStateManager stateManager;

    @Override
    protected void initialize(Application app) {
        AppStateManager stateManager = app.getStateManager();
        this.stateManager = stateManager;

        this.entityDataAppState = new EntityDataAppState();
        this.cameraAppState = new CameraAppState();
        this.physicsAppState = new PhysicsAppState();
        this.physicalCharacterMovement = new PhysicalCharacterMovement();
        this.physicsPushAppState = new PhysicsPushAppState();
        this.sceneAppState = new SceneAppState();
        this.skyAppState = new SkyAppState();
        this.visualisationAppState = new VisualisationAppState();
        this.inputAppState = new InputAppState();
        this.niftyAppState = new NiftyAppState();

        stateManager.attach(entityDataAppState);
        stateManager.attach(physicsAppState);
        stateManager.attach(physicalCharacterMovement);
        stateManager.attach(physicsPushAppState);
        stateManager.attach(sceneAppState);
        stateManager.attach(skyAppState);
        stateManager.attach(visualisationAppState);
        stateManager.attach(inputAppState);
        stateManager.attach(cameraAppState);
        stateManager.attach(niftyAppState);

        // for now...
        stateManager.attach(new GameStarterAppState());

    }

    @Override
    protected void cleanup(Application app) {
        this.stateManager.detach(entityDataAppState);
        this.stateManager.detach(cameraAppState);
        this.stateManager.detach(visualisationAppState);
        this.stateManager.detach(physicalCharacterMovement);
        this.stateManager.detach(physicsAppState);
        this.stateManager.detach(physicsPushAppState);
        this.stateManager.detach(inputAppState);
        this.stateManager.detach(sceneAppState);
        this.stateManager.detach(skyAppState);
        this.stateManager.detach(niftyAppState);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
