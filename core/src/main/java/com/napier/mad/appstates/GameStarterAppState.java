package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.WorldTransformComponent;
import com.napier.mad.constants.Constants;
import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class GameStarterAppState extends BaseAppState {



    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        // init some entities
        float TILE_LENGTH = 8f;
        for (int i = 0; i < 5; i++) {
            EntityId straight = entityData.createEntity();
            entityData.setComponents(straight,
                    new WorldTransformComponent(new Vector3f(0, 0, i*TILE_LENGTH), Constants.UP),
                    new ModelComponent(ModelType.Road_Straight));
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
