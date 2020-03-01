package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.napier.mad.components.CollisionEventComponent;
import com.napier.mad.components.CollisionShapeComponent;
import com.napier.mad.components.DecayComponent;
import com.napier.mad.components.WorldTransformComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class CollisionAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet collidables;
    private Map<EntityId, BoundingBox> boundingBoxMap = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.collidables = entityData.getEntities(CollisionShapeComponent.class, WorldTransformComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (collidables.applyChanges()) {

            for (Entity e : collidables.getAddedEntities()) {
                addBoundingBox(e);
            }

            for (Entity e : collidables.getChangedEntities()) {
                updateBoundingBox(e);
            }

            for (Entity e : collidables.getRemovedEntities()) {
                removeBoundingBox(e);
            }
        }

        checkForCollisions();
    }

    private void addBoundingBox(Entity e) {
        CollisionShapeComponent shapeComponent = e.get(CollisionShapeComponent.class);
        WorldTransformComponent worldTransformComponent = e.get(WorldTransformComponent.class);
        Vector3f worldTranslation = worldTransformComponent.getWorldTransform().getTranslation();
        Vector3f halfExtends = shapeComponent.getBoundingBoxHalfExtends();
        BoundingBox boundingBox = new BoundingBox(worldTranslation, halfExtends.x, halfExtends.y, halfExtends.z);
        this.boundingBoxMap.put(e.getId(), boundingBox);
    }

    private void updateBoundingBox(Entity e) {
        WorldTransformComponent worldTransformComponent = e.get(WorldTransformComponent.class);
        Vector3f worldTranslation = worldTransformComponent.getWorldTransform().getTranslation();
        BoundingBox boundingBox = this.boundingBoxMap.get(e.getId());
        boundingBox.setCenter(worldTranslation);
    }

    private void removeBoundingBox(Entity e) {
        this.boundingBoxMap.remove(e.getId());
    }

    private void checkForCollisions() {
        for (Map.Entry<EntityId, BoundingBox> e : boundingBoxMap.entrySet()) {
            EntityId entityId = e.getKey();
            BoundingBox boundingBox = e.getValue();

            for (Map.Entry<EntityId, BoundingBox> other : boundingBoxMap.entrySet()) {
                EntityId otherEntityId = other.getKey();
                if (entityId.equals(otherEntityId)) {
                    continue;
                }
                BoundingBox otherBoundingBox = other.getValue();
                if (boundingBox.intersects(otherBoundingBox)) {
                    createCollisionEntity(entityId, otherEntityId);
                }
            }

        }
    }

    private void createCollisionEntity(EntityId entityIdA, EntityId entityIdB) {
        EntityId collision = entityData.createEntity();
        entityData.setComponents(collision, new CollisionEventComponent(entityIdA, entityIdB),new DecayComponent());
    }

    @Override
    protected void cleanup(Application app) {
        this.collidables.release();
        this.collidables.clear();
        this.collidables = null;
        this.boundingBoxMap.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }


}
