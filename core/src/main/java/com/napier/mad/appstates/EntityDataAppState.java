package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.simsilica.es.EntityData;
import com.simsilica.es.base.DefaultEntityData;

public class EntityDataAppState extends BaseAppState {

    private EntityData entityData;

    public EntityDataAppState(EntityData entityData) {
        this.entityData = entityData;
    }

    @Override
    protected void initialize(Application app) {

    }

    @Override
    protected void cleanup(Application app) {
        this.entityData.close();
    }

    public EntityData getEntityData() {
        return entityData;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
