package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.ItemCollectedEventComponent;
import com.napier.mad.components.ItemComponent;
import com.napier.mad.components.ScoreComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class ScoreAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet scoreEntities;
    private EntitySet collectionEventEntities;
    private EntitySet scoreItems;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.scoreEntities = entityData.getEntities(ScoreComponent.class);
        this.scoreItems = entityData.getEntities(ItemComponent.class);
        this.collectionEventEntities = entityData.getEntities(ItemCollectedEventComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.scoreEntities.applyChanges();
        this.scoreItems.applyChanges();

        if (collectionEventEntities.applyChanges()) {

            for (Entity e : collectionEventEntities.getAddedEntities()) {
                handle(e);
            }

        }
    }

    private void handle(Entity e) {
        ItemCollectedEventComponent event = e.get(ItemCollectedEventComponent.class);
        EntityId collectorId = event.getCollectorId();
        EntityId itemId = event.getItemId();
        if (scoreEntities.containsId(collectorId) && scoreItems.containsId(itemId)) {
            // player collected an item that highers his score
            ItemComponent itemComponent = scoreItems.getEntity(itemId).get(ItemComponent.class);
            int pointsToAdd = itemComponent.getPoints();
            ScoreComponent scoreComponent = scoreEntities.getEntity(collectorId).get(ScoreComponent.class);
            int newScore = scoreComponent.getScore() + pointsToAdd;

            // apply new score
            entityData.setComponent(collectorId, new ScoreComponent(newScore));

            // delete collected item, so it cannot be collected by others
            entityData.removeEntity(itemId);
        }
    }

    @Override
    protected void cleanup(Application app) {
        this.scoreItems.release();
        this.scoreItems.clear();
        this.scoreEntities.release();
        this.scoreEntities.clear();
        this.collectionEventEntities.release();
        this.collectionEventEntities.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
