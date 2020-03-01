package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.BaseAppState;

import java.util.ArrayList;
import java.util.List;

public class GameAppStateInitializer extends BaseAppState {

    private List<AppState> appStates = new ArrayList<>();

    @Override
    protected void initialize(Application app) {
        initEntityAppStates();
        initGameAppStates();
        initViewAppStates();
        initInputAppStates();
        add(new GameStarterAppState());

        addToStateManager();
    }

    private void initEntityAppStates() {
        add(new EntityDataAppState());
    }

    private void initGameAppStates() {
        add(new StartFollowPathAppState());
        add(new AutoFollowMovementAppState());
        add(new AnchorMovementAppState());
        add(new NewMovementAppState());
        add(new RandomAutoPathExtender());
        add(new DecayAppState());
        add(new PathDecayAppState());
        add(new SideMovementAppState());
        add(new ItemGeneratorAppState());
        add(new DeleteAttachedEntitiesAppState());
    }

    private void initViewAppStates() {
        add(new SceneAppState());
        add(new SkyAppState());
        add(new ModelLoaderAppState());
        add(new ModelAttachAppState());
        add(new LocalTransformAppState());
        add(new CameraAppState());
        add(new ItemRotationAppState());
    }

    private void initInputAppStates() {
        add(new GameInputAppState());
    }

    private void add(AppState appState) {
        this.appStates.add(appState);
    }

    private void addToStateManager() {
        for (AppState appState : appStates) {
            getStateManager().attach(appState);
        }
    }

    private void removeFromStateManager() {
        for (AppState appState : appStates) {
            getStateManager().detach(appState);
        }
    }

    @Override
    protected void cleanup(Application app) {
        removeFromStateManager();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
