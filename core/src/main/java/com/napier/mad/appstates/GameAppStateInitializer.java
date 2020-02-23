package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;


public class GameAppStateInitializer extends BaseAppState {

    private EntityDataAppState entityDataAppState;
    private CameraAppState cameraAppState;
    private PhysicsAppState physicsAppState;
    private PhysicsPushAppState physicsPushAppState;
    private VisualisationAppState visualisationAppState;
    private InputAppState inputAppState;

    private AppStateManager stateManager;

    @Override
    protected void initialize(Application app) {
        AppStateManager stateManager = app.getStateManager();
        this.stateManager = stateManager;

        this.entityDataAppState = new EntityDataAppState();
        this.cameraAppState = new CameraAppState();
        this.physicsAppState = new PhysicsAppState();
        this.physicsPushAppState = new PhysicsPushAppState();
        this.visualisationAppState = new VisualisationAppState();
        this.inputAppState = new InputAppState();

        stateManager.attach(entityDataAppState);
        stateManager.attach(cameraAppState);
        stateManager.attach(physicsAppState);
        stateManager.attach(physicsPushAppState);
        stateManager.attach(visualisationAppState);
        stateManager.attach(inputAppState);

        // for now...
        stateManager.attach(new GameStarterAppState());

    }

    @Override
    protected void cleanup(Application app) {
        this.stateManager.detach(entityDataAppState);
        this.stateManager.detach(cameraAppState);
        this.stateManager.detach(visualisationAppState);
        this.stateManager.detach(physicsAppState);
        this.stateManager.detach(physicsPushAppState);
        this.stateManager.detach(inputAppState);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
