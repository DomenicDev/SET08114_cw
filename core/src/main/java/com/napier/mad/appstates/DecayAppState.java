package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.DecayComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

public class DecayAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet decayingEntities;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.decayingEntities = entityData.getEntities(DecayComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.decayingEntities.applyChanges();

        for (Entity e : decayingEntities) {
            DecayComponent decayComponent = e.get(DecayComponent.class);
            float newTimeToLive = decayComponent.getTimeToLive() - tpf;
            if (newTimeToLive <= 0) {
                entityData.removeEntity(e.getId());
            } else {
                entityData.setComponent(e.getId(), new DecayComponent(newTimeToLive));
            }
        }

    }

    @Override
    protected void cleanup(Application app) {
        this.decayingEntities.release();
        this.decayingEntities.clear();
        this.decayingEntities = null;
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
