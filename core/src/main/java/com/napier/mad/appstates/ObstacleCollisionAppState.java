package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.AliveStateComponent;
import com.napier.mad.components.CollisionEventComponent;
import com.napier.mad.components.DecayComponent;
import com.napier.mad.components.ObstacleComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class ObstacleCollisionAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet obstacles;
    private EntitySet aliveEntities;

    private EntitySet collisions;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.obstacles = entityData.getEntities(ObstacleComponent.class);
        this.aliveEntities = entityData.getEntities(AliveStateComponent.class);
        this.collisions = entityData.getEntities(CollisionEventComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.obstacles.applyChanges();
        this.aliveEntities.applyChanges();

        if (collisions.applyChanges()) {
            for (Entity e : collisions.getAddedEntities()) {
                handleCollision(e);
            }
        }
    }

    private void handleCollision(Entity e) {
        CollisionEventComponent collisionEventComponent = e.get(CollisionEventComponent.class);
        EntityId entityIdA = collisionEventComponent.getEntityIdA();
        EntityId entityIdB = collisionEventComponent.getEntityIdB();
        if (obstacles.containsId(entityIdA) && aliveEntities.containsId(entityIdB)) {
            handleCollision(entityIdA, entityIdB);
        } else if (obstacles.containsId(entityIdB) && aliveEntities.containsId(entityIdA)) {
            handleCollision(entityIdB, entityIdA);
        }
    }

    private void handleCollision(EntityId obstacleId, EntityId hitEntity) {
        entityData.setComponents(hitEntity, new AliveStateComponent(false), new DecayComponent(0.02f));
    }

    @Override
    protected void cleanup(Application app) {
        this.obstacles.release();
        this.obstacles.clear();
        this.aliveEntities.release();
        this.aliveEntities.clear();
        this.collisions.release();
        this.collisions.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
