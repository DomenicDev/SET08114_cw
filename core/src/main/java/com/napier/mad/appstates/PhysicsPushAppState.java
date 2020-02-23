package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.napier.mad.components.PhysicsPushComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class PhysicsPushAppState extends BaseAppState {

    private PhysicsAppState physicsAppState;
    private EntitySet pushedEntities;
    private EntityData entityData;


    @Override
    protected void initialize(Application app) {
        AppStateManager stateManager = app.getStateManager();
        this.physicsAppState = stateManager.getState(PhysicsAppState.class);

        this.entityData = stateManager.getState(EntityDataAppState.class).getEntityData();
        this.pushedEntities = entityData.getEntities(PhysicsPushComponent.class);


    }

    @Override
    public void update(float tpf) {
        if (pushedEntities.applyChanges()) {

            for (Entity e : pushedEntities.getAddedEntities()) {
                applyPush(e);
            }

        }

        // all pushed were applied, we remove the component so that it only applies once
        for (Entity entity : pushedEntities) {
            entityData.removeComponent(entity.getId(), PhysicsPushComponent.class);
        }
    }

    private void applyPush(Entity e) {
        PhysicsPushComponent pushComponent = e.get(PhysicsPushComponent.class);
        EntityId targetEntity = pushComponent.getTargetEntity();

        // get rigid body control
        RigidBodyControl control = this.physicsAppState.getRigidBodyControl(targetEntity);

        if (control == null) {
            return;
        }

        // apply force
        Vector3f direction = pushComponent.getDirection().normalizeLocal();
        float strength = pushComponent.getStrength();
        control.setLinearVelocity(direction.mult(strength));
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
