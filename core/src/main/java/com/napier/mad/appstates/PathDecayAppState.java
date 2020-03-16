package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.DecayComponent;
import com.napier.mad.components.DecayPathComponent;
import com.napier.mad.components.DestroyPassedEntityOnFinishedMovementComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.components.OnMovementFinishedComponent;
import com.napier.mad.components.PathComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.List;

/**
 * This app state removes the entities of a path if they have been passed
 * by all entities that follow this path.
 *
 * This app state does also for paths remove non-existing entities from the path list.
 */
public class PathDecayAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet finishedMovements;
    private EntitySet decayingPaths;
    private EntitySet movables;
    private EntitySet pathDestroyingMovingEntities; // when these entities pass a road, the tile gets destroyed

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.finishedMovements = entityData.getEntities(OnMovementFinishedComponent.class);
        this.decayingPaths = entityData.getEntities(PathComponent.class, DecayPathComponent.class);
        this.movables = entityData.getEntities(MovableComponent.class);
        this.pathDestroyingMovingEntities = entityData.getEntities(DestroyPassedEntityOnFinishedMovementComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.decayingPaths.applyChanges();
        this.pathDestroyingMovingEntities.applyChanges();

        if (this.finishedMovements.applyChanges()) {
            for (Entity e : finishedMovements.getAddedEntities()) {
                handleFinishedMovement(e);
            }
        }

        if (this.movables.applyChanges()) {
            for (Entity e : movables.getRemovedEntities()) {
                removeFromPath(e);
            }
        }
    }

    private void handleFinishedMovement(Entity e) {
        OnMovementFinishedComponent finishedComponent = e.get(OnMovementFinishedComponent.class);
        EntityId usedEntity = finishedComponent.getMovableEntityId(); // the road entity
        EntityId movingEntityId = finishedComponent.getMovingEntityId();

        if (!pathDestroyingMovingEntities.containsId(movingEntityId)) {
            // this entity does not destroy the tile after passing
            return;
        }

        Entity movingEntity = pathDestroyingMovingEntities.getEntity(movingEntityId);
        float timeToLive = movingEntity.get(DestroyPassedEntityOnFinishedMovementComponent.class).getTimeToLive();

        // look if that entity is part of a path
        for (Entity pathEntity : decayingPaths) {
            PathComponent pathComponent = pathEntity.get(PathComponent.class);
            List<EntityId> path = pathComponent.getPath();
            if (path.contains(usedEntity)) {
                entityData.setComponent(usedEntity, new DecayComponent(timeToLive));
            }
        }
    }

    private void removeFromPath(Entity e) {
        EntityId movable = e.getId();
        for (Entity pathEntity : decayingPaths) {
            EntityId pathId = pathEntity.getId();
            PathComponent pathComponent = pathEntity.get(PathComponent.class);
            List<EntityId> path = pathComponent.getPath();
            // when this entity was part of that path, we update the component
            if (path.remove(movable)) {
                entityData.setComponent(pathId, new PathComponent(path));
            }
        }
    }

    @Override
    protected void cleanup(Application app) {
        this.decayingPaths.release();
        this.decayingPaths.clear();
        this.movables.release();
        this.movables.clear();
        this.finishedMovements.release();
        this.finishedMovements.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
