package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.JumpComponent;
import com.napier.mad.components.PlayerControlled;
import com.napier.mad.components.SideMovementComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

public class GameInputAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet playerControlledEntities;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.playerControlledEntities = entityData.getEntities(PlayerControlled.class);
    }

    @Override
    public void update(float tpf) {
        this.playerControlledEntities.applyChanges();
    }


    public void moveSidewards(float targetX) {
        if (!isInitialized()) {
            return;
        }
        refreshSideMovementComponent(targetX);
    }

    public void jump() {
        if (!isInitialized()) {
            return;
        }
        for (Entity e : playerControlledEntities) {
            JumpComponent jumpComponent = entityData.getComponent(e.getId(), JumpComponent.class);
            if (jumpComponent == null) {
                entityData.setComponent(e.getId(), new JumpComponent(true));
            } else {
                if (!jumpComponent.isJumping()) {
                    entityData.setComponent(e.getId(), new JumpComponent(true));
                }
            }
        }
    }

    private void refreshSideMovementComponent(float targetX) {
        for (Entity e : playerControlledEntities) {
            this.entityData.setComponent(e.getId(), new SideMovementComponent(targetX));
        }
    }

    @Override
    protected void cleanup(Application app) {
        this.playerControlledEntities.release();
        this.playerControlledEntities.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
