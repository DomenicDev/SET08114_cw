package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.SideMovementComponent;
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
        int type = sideMovementComponent.getMovement();
        if (type == SideMovementComponent.STAND) {
            return;
        }
        float movedDistance = tpf * speed;

        Vector3f newLocation = new Vector3f(currentLocation);
        if (type == SideMovementComponent.LEFT) {
            float newX = currentLocation.x + movedDistance;
            if (newX >= limit) {
                newX = limit;
            }
            newLocation.setX(newX);
        } else if (type == SideMovementComponent.RIGHT) {
            float newX = currentLocation.x - movedDistance;
            if (newX <= -limit) {
                newX = -limit;
            }
            newLocation.setX(newX);
        }
        entityData.setComponent(e.getId(), new LocalTransformComponent(newLocation));
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
