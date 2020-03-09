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

    private Random createRandom = new Random();
    private Random randomCoinLine = new Random();

    // we want to wait a little before the first obstacle is created
    // to make sure the player does not run into one at the right beginning
    private int initialObstacleWaitCounter = 5;

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

        double random = createRandom.nextDouble(); // change to some better random method

        if (random < 0.4) {
            return;
        }

        if (random < 0.7) {
            if (modelType == ModelType.Road_Straight) {
                // generate coins
                generateCoinsInLine(movableId, modelType);
            }
        }

        if (0.7 < random && random < 1) {
            if (initialObstacleWaitCounter > 0) {
                initialObstacleWaitCounter--;
            } else {
                generateObstacle(movableId, modelType);
            }
        }

    }

    private void generateObstacle(EntityId movableId, ModelType modelType) {
        if (modelType == ModelType.Road_Straight) {
            float x = generateRandomXOffset();
            float z = 0;
            EntityFactory.createObstacle(entityData, movableId, new Vector3f(x, 1.5f, z));
        }
    }

    private void generateCoinsInLine(EntityId movableId, ModelType modelType) {
        float x = generateRandomXOffset();
        for (int i = 0; i < 4; i++) {
            EntityFactory.createCoin(entityData, movableId, new Vector3f(x, 1.5f, (-Constants.TILE_LENGTH / 2) + i * Constants.TILE_LENGTH / 4f));
        }
    }

    private float generateRandomXOffset() {
        return 1.5f - randomCoinLine.nextInt(3);
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
