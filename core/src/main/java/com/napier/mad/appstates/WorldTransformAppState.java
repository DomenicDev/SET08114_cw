package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Transform;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.WorldTransformComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldTransformAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet entities;
    private EntitySet attached; // helper set to manage parent child relationships

    private Map<EntityId, List<EntityId>> parentToChildrenMap = new HashMap<>();
    private Map<EntityId, EntityId> lastParentMap = new HashMap<>(); // helper map for optimization (child -> parent)
    private Map<EntityId, Transform> worldTransformMap = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.attached = entityData.getEntities(AttachedToComponent.class);
        this.entities = entityData.getEntities(LocalTransformComponent.class, AttachedToComponent.class);
    }

    @Override
    public void update(float tpf) {

        if (attached.applyChanges()) {

            for (Entity e : attached.getAddedEntities()) {
                addToMap(e);
            }

            for (Entity e : attached.getChangedEntities()) {
                updateMap(e);
            }

            for (Entity e : attached.getRemovedEntities()) {
                removeFromMap(e);
            }

        }

        if (entities.applyChanges()) {

            for (Entity e : entities.getAddedEntities()) {
                addTransform(e);
            }

            for (Entity e : entities.getChangedEntities()) {
                updateTransform(e);
            }

            for (Entity e : entities.getRemovedEntities()) {
                removeTransform(e);
            }

        }

    }

    private void addToMap(Entity e) {
        EntityId childId = e.getId();
        AttachedToComponent attachedToComponent = e.get(AttachedToComponent.class);
        EntityId parentId = attachedToComponent.getParentId();
        if (parentId != null) {
            if (!parentToChildrenMap.containsKey(parentId)) {
                List<EntityId> children = new ArrayList<>();
                parentToChildrenMap.put(parentId, children);
            }
            parentToChildrenMap.get(parentId).add(childId);
            lastParentMap.put(childId, parentId);
        }
    }

    private void updateMap(Entity e) {
        EntityId childId = e.getId();
        EntityId lastParentId = lastParentMap.get(childId);

        AttachedToComponent attachedToComponent = e.get(AttachedToComponent.class);
        EntityId newParentId = attachedToComponent.getParentId();

        if (newParentId == null) {
            return;
        }

        if (newParentId.equals(lastParentId)) {
            // parent did not change, so there is nothing we need to do
            return;
        }

        // update maps
        if (lastParentId != null) {
            parentToChildrenMap.get(lastParentId).remove(childId);
        }
        addToMap(e);  // will add new parent if necessary
        lastParentMap.put(childId, newParentId);
    }

    private void removeFromMap(Entity e) {
        EntityId childId = e.getId();
        EntityId lastParentId = lastParentMap.remove(childId);
        if (lastParentId != null) {
            parentToChildrenMap.get(lastParentId).remove(lastParentId);
            // clean up if everything is empty
            if (parentToChildrenMap.get(lastParentId).isEmpty()) {
                parentToChildrenMap.remove(lastParentId);
            }
        }
    }

    private void addTransform(Entity e) {
        initTransform(e.getId());
        updateTransform(e);
    }

    private void initTransform(EntityId entityId) {
        worldTransformMap.put(entityId, new Transform());
    }

    private void updateTransform(Entity e) {
        EntityId entityId = e.getId();
        EntityId parentId = lastParentMap.get(entityId);

        LocalTransformComponent localTransform = e.get(LocalTransformComponent.class);

        if (parentId == null) {
            // local transform is world transform
            Transform worldTransform = worldTransformMap.get(entityId);
            applyLocalTransform(localTransform, worldTransform);
            setNewWorldTransform(entityId, worldTransform);
        } else {
            // we have a parent to combine the transform with
            // first check if we have calculated the world transform of the parent yet
            if (!worldTransformMap.containsKey(parentId)) {
                addTransform(entities.getEntity(parentId));
            }

            // get child current world transform and apply (changed) local transform
            Transform worldTransformChild = worldTransformMap.get(entityId);
            applyLocalTransform(localTransform, worldTransformChild);

            // combine parents world transform with child one and update entity
            Transform worldTransformParent = worldTransformMap.get(parentId);
            worldTransformChild.combineWithParent(worldTransformParent);
            setNewWorldTransform(entityId, worldTransformChild);

            // we also need to update the world transform for all children entities
            if (parentToChildrenMap.containsKey(entityId)) {
                for (EntityId childId : parentToChildrenMap.get(entityId)) {
                    Entity childEntity = entities.getEntity(childId);
                    if (childEntity != null)  {
                        updateTransform(entities.getEntity(childId));
                    }
                }
            }
        }
    }

    private void setNewWorldTransform(EntityId entityId, Transform worldTransform) {
        worldTransformMap.put(entityId, worldTransform);
        entityData.setComponent(entityId, new WorldTransformComponent(worldTransform));
    }

    private void applyLocalTransform(LocalTransformComponent localTransform, Transform worldTransform) {
        worldTransform.setTranslation(localTransform.getLocation());
        worldTransform.setRotation(localTransform.getRotation());
        worldTransform.setScale(localTransform.getScale());
    }

    private void removeTransform(Entity e) {
        worldTransformMap.remove(e.getId());
        entityData.removeComponent(e.getId(), WorldTransformComponent.class);
    }

    @Override
    protected void cleanup(Application app) {
        this.entities.release();
        this.entities.clear();
        this.attached.release();
        this.attached.clear();
        this.parentToChildrenMap.clear();
        this.lastParentMap.clear();
        this.worldTransformMap.clear();
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
