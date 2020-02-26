package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.components.PlayerMovementComponent;
import com.napier.mad.components.RigidBodyComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class PhysicalCharacterMovement extends BaseAppState {

    private EntitySet movingEntities;
    private PhysicsAppState physicsAppState;

    @Override
    protected void initialize(Application app) {
        EntityData entityData = app.getStateManager().getState(EntityDataAppState.class).getEntityData();
        this.movingEntities = entityData.getEntities(RigidBodyComponent.class, PlayerMovementComponent.class);

        this.physicsAppState = app.getStateManager().getState(PhysicsAppState.class);

    }

    @Override
    public void update(float tpf) {
        if (movingEntities.applyChanges()) {

            for (Entity e : movingEntities.getAddedEntities()) {
                updateMovement(e);
            }

            for (Entity e : movingEntities.getChangedEntities()) {
                updateMovement(e);
            }

            for (Entity e : movingEntities.getRemovedEntities()) {
                resetMovement(e);
            }
        }
    }

    private void updateMovement(Entity e) {
        // get movement information
        PlayerMovementComponent movementComponent = e.get(PlayerMovementComponent.class);
        Vector3f direction = movementComponent.getDirection();
        float speed = movementComponent.getSpeed();

        // get physics control
        RigidBodyControl rigidBodyControl = physicsAppState.getRigidBodyControl(e.getId());

        if (rigidBodyControl != null) {
            rigidBodyControl.setLinearVelocity(direction.mult(speed));
        }
    }

    private void resetMovement(Entity e) {
        EntityId entityId = e.getId();
        RigidBodyControl control = physicsAppState.getRigidBodyControl(entityId);
        if (control != null) {
            control.setLinearVelocity(Vector3f.ZERO.clone());
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
