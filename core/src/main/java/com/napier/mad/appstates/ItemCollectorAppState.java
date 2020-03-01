package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.CollectorComponent;
import com.napier.mad.components.CollisionEventComponent;
import com.napier.mad.components.DecayComponent;
import com.napier.mad.components.ItemComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class ItemCollectorAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet collectors;
    private EntitySet items;
    private EntitySet collisions;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.collectors = entityData.getEntities(CollectorComponent.class);
        this.items = entityData.getEntities(ItemComponent.class);
        this.collisions = entityData.getEntities(CollisionEventComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.collectors.applyChanges();
        this.items.applyChanges();

        if (this.collisions.applyChanges()) {

            for (Entity e : this.collisions.getAddedEntities()) {
                handleCollision(e);
            }

        }
    }

    private void handleCollision(Entity collisionEntity) {
        CollisionEventComponent eventComponent = collisionEntity.get(CollisionEventComponent.class);
        EntityId entityIdA = eventComponent.getEntityIdA();
        EntityId entityIdB = eventComponent.getEntityIdB();

        // check if collector collected an item
        if (collectors.containsId(entityIdA) && items.containsId(entityIdB)) {
            collectItem(entityIdA, entityIdB);
        } else if (collectors.containsId(entityIdB) && items.containsId(entityIdA)) {
            collectItem(entityIdB, entityIdA);
        }

    }

    private void collectItem(EntityId collectorId, EntityId itemId) {
        ItemComponent itemComponent = items.getEntity(itemId).get(ItemComponent.class);
        int points = itemComponent.getPoints();

        // add to score
        getState(MainGameAppState.class).addToScore(points);

        entityData.setComponent(itemId, new DecayComponent());
    }

    @Override
    protected void cleanup(Application app) {
        this.items.release();
        this.items.clear();
        this.collectors.release();
        this.collectors.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
