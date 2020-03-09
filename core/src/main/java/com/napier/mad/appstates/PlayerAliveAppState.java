package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.AliveStateComponent;
import com.napier.mad.components.PlayerControlled;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

public class PlayerAliveAppState extends BaseAppState {

    private EntitySet playerControlled;

    private MainGameAppState mainGameAppState;

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.playerControlled = entityData.getEntities(PlayerControlled.class, AliveStateComponent.class);
        this.mainGameAppState = getState(MainGameAppState.class);
    }

    @Override
    public void update(float tpf) {
        if (playerControlled.applyChanges()) {

            for (Entity e : playerControlled.getAddedEntities()) {
                handle(e);
            }

            for (Entity e : playerControlled.getChangedEntities()) {
                handle(e);
            }

        }
    }

    private void handle(Entity e) {
        AliveStateComponent aliveStateComponent = e.get(AliveStateComponent.class);
        if (!aliveStateComponent.isAlive()) {
            // for now we set game over
            mainGameAppState.setGameOver();
        }
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
