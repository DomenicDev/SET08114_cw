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
        initGameStarter();

        addToStateManager();
    }

    private void initEntityAppStates() {
        add(new EntityDataAppState());
    }

    private void initGameAppStates() {
        add(new DecayAppState());
        add(new StartFollowPathAppState());
        add(new AutoFollowMovementAppState());
        add(new AnchorMovementAppState());
        add(new NewMovementAppState());
        add(new RandomAutoPathExtender());
        add(new PathDecayAppState());
        add(new SideMovementAppState());
        add(new CollisionAppState());
        add(new JumpAppState());
        add(new ItemGeneratorAppState());
        add(new ScoreAppState());
        add(new PlayerAppState());
        add(new DeleteAttachedEntitiesAppState());
        add(new ItemCollectorAppState());
        add(new ObstacleCollisionAppState());
        add(new PlayerAliveAppState());
        add(new WorldTransformAppState());
    }

    private void initViewAppStates() {
        add(new SceneAppState());
        add(new SkyAppState());
        add(new ModelLoaderAppState());
        add(new ModelAttachAppState());
        add(new LocalTransformAppState());
        add(new CameraAppState());
        add(new ItemRotationAppState());
        add(new HumanAnimationAppState());
        add(new DebugCollisionBoxAppState());
    }

    private void initInputAppStates() {
        add(new GameInputAppState());
    }

    private void initGameStarter() {
        add(new GameStarterAppState());
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
        for (AppState appState : appStates) {
            appState.setEnabled(true);
        }
    }

    @Override
    protected void onDisable() {
        for (AppState appState : appStates) {
            appState.setEnabled(false);
        }
    }
}
