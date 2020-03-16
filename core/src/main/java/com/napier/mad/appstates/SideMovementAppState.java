package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.SideMovementComponent;
import com.napier.mad.constants.GameConstants;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

public class SideMovementAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet sideMovingEntities;

    private float limit = 1.5f;
    private float speed = 3f;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.sideMovingEntities = entityData.getEntities(SideMovementComponent.class, LocalTransformComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.sideMovingEntities.applyChanges();

        for (Entity e : sideMovingEntities) {
            handleSideMovement(e, tpf);
        }

    }

    private void handleSideMovement(Entity e, float tpf) {
        SideMovementComponent sideMovementComponent = e.get(SideMovementComponent.class);
        LocalTransformComponent localTransformComponent = e.get(LocalTransformComponent.class);
        Vector3f currentLocation = localTransformComponent.getLocation();
        float acc = sideMovementComponent.getAcceleration();
        float speed = GameConstants.SIDE_MOVEMENT_SPEED;
        float max = 1.5f;
        float movedDistance = acc * speed * tpf;

        float compVal = Math.min(max, Math.abs(movedDistance));

        movedDistance = (movedDistance > 0) ? compVal : -compVal;

        float newLoc = currentLocation.x + movedDistance;
        if (newLoc < -max) {
            newLoc = -max;
        } else if (newLoc > max) {
            newLoc = max;
        }

        Vector3f newLocation = new Vector3f(currentLocation);
        newLocation.setX(newLoc);
        entityData.setComponent(e.getId(), new LocalTransformComponent(newLocation));
    }

    @Override
    protected void cleanup(Application app) {
        this.sideMovingEntities.release();
        this.sideMovingEntities.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
