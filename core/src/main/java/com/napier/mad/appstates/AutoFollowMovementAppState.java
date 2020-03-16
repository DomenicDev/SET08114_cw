package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.FollowPathComponent;
import com.napier.mad.components.MoveOnComponent;
import com.napier.mad.components.MovementSpeedComponent;
import com.napier.mad.components.OnMovementFinishedComponent;
import com.napier.mad.components.PathComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.List;
import java.util.logging.Logger;

public class AutoFollowMovementAppState extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(AutoFollowMovementAppState.class.getName());

    private EntityData entityData;
    private EntitySet finishedMovements;
    private EntitySet paths;
    private EntitySet follows;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.finishedMovements = entityData.getEntities(OnMovementFinishedComponent.class);
        this.paths = entityData.getEntities(PathComponent.class);
        this.follows = entityData.getEntities(FollowPathComponent.class, MovementSpeedComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.paths.applyChanges();
        this.follows.applyChanges();

        if (finishedMovements.applyChanges()) {

            for (Entity e : finishedMovements.getAddedEntities()) {
                moveOnNext(e);
            }

            for (Entity e : finishedMovements.getChangedEntities()) {
                moveOnNext(e);
            }

        }
    }

    private void moveOnNext(Entity e) {
        OnMovementFinishedComponent finishedComponent = e.get(OnMovementFinishedComponent.class);
        EntityId movingEntityId = finishedComponent.getMovingEntityId(); // the player
        EntityId movableEntityId = finishedComponent.getMovableEntityId(); // the road

        // check if given entity follows a path
        if (!follows.containsId(movingEntityId)) {
            return;
        }

        // get the path the entity is following
        FollowPathComponent followPathComponent = follows.getEntity(movingEntityId).get(FollowPathComponent.class);
        EntityId pathId = followPathComponent.getPath();

        // check if that path does really exist
        if (!paths.containsId(pathId)) {
            LOGGER.warning("the entity " + movingEntityId + " follows a path that does not exist");
            return;
        }

        // get path and last movable entity
        List<EntityId> path = paths.getEntity(pathId).get(PathComponent.class).getPath();
        int indexLastMovable = path.indexOf(movableEntityId);
        if (indexLastMovable == -1) {
            LOGGER.warning("movable does not exist on this path");
            return;
        }

        // check if there is a next movable entity
        int indexNextMovable = indexLastMovable + 1;
        if (indexNextMovable >= path.size()) {
            LOGGER.warning("no next movable entity on this path");
            return;
        }

        // get speed
        float speed = follows.getEntity(movingEntityId).get(MovementSpeedComponent.class).getSpeed();

        // go on next movable object
        EntityId nextMovable = path.get(indexNextMovable);
        this.entityData.setComponent(movingEntityId, new MoveOnComponent(nextMovable, speed));
    }

    @Override
    protected void cleanup(Application app) {
        this.follows.release();
        this.follows.clear();
        this.paths.release();
        this.paths.clear();
        this.finishedMovements.release();
        this.finishedMovements.clear();
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
