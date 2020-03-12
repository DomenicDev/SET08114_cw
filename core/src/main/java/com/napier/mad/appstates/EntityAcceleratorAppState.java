package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.AccelerateComponent;
import com.napier.mad.components.MovementSpeedComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

public class EntityAcceleratorAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet movingEntities;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.movingEntities = entityData.getEntities(MovementSpeedComponent.class, AccelerateComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.movingEntities.applyChanges();

        for (Entity e : movingEntities) {
            AccelerateComponent accelerateComponent = e.get(AccelerateComponent.class);
            float acceleration = accelerateComponent.getAcceleration();
            float currentSpeed = e.get(MovementSpeedComponent.class).getSpeed();
            float newSpeed = currentSpeed + tpf * acceleration;
            entityData.setComponent(e.getId(), new MovementSpeedComponent(newSpeed));
        }
    }

    @Override
    protected void cleanup(Application app) {
        this.movingEntities.release();
        this.movingEntities.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
