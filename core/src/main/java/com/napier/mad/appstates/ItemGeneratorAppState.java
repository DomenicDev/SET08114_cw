package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.constants.Constants;
import com.napier.mad.factory.EntityFactory;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.Random;

public class ItemGeneratorAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet movables;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.movables = entityData.getEntities(MovableComponent.class, ModelComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (this.movables.applyChanges()) {

            for (Entity e : movables.getAddedEntities()) {
                produceItems(e);
            }

        }
    }

    private void produceItems(Entity e) {
        EntityId movableId = e.getId();
        ModelComponent modelComponent = e.get(ModelComponent.class);
        ModelType modelType = modelComponent.getType();

        double random = Math.random(); // change to some better random method

        if (random < 0.4) {
            return;
        }

        if (modelType == ModelType.Road_Straight) {
            // generate coins
            generateCoinsInLine(movableId, modelType);
        }

    }

    private void generateCoinsInLine(EntityId movableId, ModelType modelType) {
        Random r = new Random();
        float x = 1.5f - r.nextInt(3);
        for (int i = 0; i < 4; i++) {
            EntityFactory.createCoin(entityData, movableId, new Vector3f(x, 1.5f, (-Constants.TILE_LENGTH / 2) + i * Constants.TILE_LENGTH / 4f));
        }
    }

    @Override
    protected void cleanup(Application app) {
        this.movables.release();
        this.movables.clear();
        this.movables = null;
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}