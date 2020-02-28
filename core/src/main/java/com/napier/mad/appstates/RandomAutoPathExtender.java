package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.components.FollowPathComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.MoveOnComponent;
import com.napier.mad.components.PathAutoGeneratedComponent;
import com.napier.mad.components.PathComponent;
import com.napier.mad.constants.Constants;
import com.napier.mad.factory.EntityFactory;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.List;
import java.util.logging.Logger;

public class RandomAutoPathExtender extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(RandomAutoPathExtender.class.getName());

    private static final int GENERATION_TRIGGER_REMAINING_ENTITIES = 1;

    private EntityData entityData;
    private EntitySet paths;
    private EntitySet followers;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.paths = entityData.getEntities(PathComponent.class, PathAutoGeneratedComponent.class);
        this.followers = entityData.getEntities(FollowPathComponent.class, MoveOnComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.paths.applyChanges();

        if (this.followers.applyChanges()) {

            for (Entity follower : this.followers.getAddedEntities()) {
                extendPathIfNecessary(follower);
            }

            for (Entity follower : this.followers.getChangedEntities()) {
                extendPathIfNecessary(follower);
            }

        }
    }

    private void extendPathIfNecessary(Entity follower) {
        FollowPathComponent followPathComponent = follower.get(FollowPathComponent.class);
        MoveOnComponent moveOnComponent = follower.get(MoveOnComponent.class);

        EntityId pathId = followPathComponent.getPath();

        // check if path (still) exists
        if (!paths.containsId(pathId)) {
            LOGGER.warning("path does not exist");
            return;
        }

        // get path data
        PathComponent pathComponent = paths.getEntity(pathId).get(PathComponent.class);
        List<EntityId> path = pathComponent.getPath();

        // get current entity on path
        EntityId currentEntity = moveOnComponent.getEntityToMoveOn();

        // look up position in path
        int currentIndex = path.indexOf(currentEntity);
        int remainingTiles = path.size() - currentIndex;

        if (remainingTiles <= GENERATION_TRIGGER_REMAINING_ENTITIES) {
            // extend path
            // TODO ...
            EntityId lastPathEntity = path.get(path.size()-1);
            LocalTransformComponent transformLast = entityData.getComponent(lastPathEntity, LocalTransformComponent.class);

            Vector3f newLocation = transformLast.getLocation().add(0, 0, Constants.TILE_LENGTH);
            Quaternion rotation = transformLast.getRotation();

            double random = Math.random();
            EntityId next;

            if (random <= 0.4f) {
                next = EntityFactory.createCornerToLeft(entityData, newLocation, rotation);
            } else {
                next = EntityFactory.createStraightRoad(entityData, newLocation, rotation);
            }

            // add created entities to path
            path.add(next);
            entityData.setComponent(pathId, new PathComponent(path));
        }

    }

    @Override
    protected void cleanup(Application app) {
        this.followers.release();
        this.followers.clear();
        this.followers = null;

        this.paths.release();
        this.paths.clear();
        this.paths = null;
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
