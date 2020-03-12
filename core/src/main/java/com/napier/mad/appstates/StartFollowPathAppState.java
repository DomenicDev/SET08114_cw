package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.FollowPathComponent;
import com.napier.mad.components.MoveOnComponent;
import com.napier.mad.components.MovementSpeedComponent;
import com.napier.mad.components.PathComponent;
import com.napier.mad.constants.Constants;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class StartFollowPathAppState extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(StartFollowPathAppState.class.getName());

    private EntityData entityData;
    private EntitySet followers;
    private EntitySet paths;

    private Map<EntityId, EntityId> lastMovedOnMap = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.followers = entityData.getEntities(FollowPathComponent.class, MovementSpeedComponent.class);
        this.paths = entityData.getEntities(PathComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.paths.applyChanges();

        if (this.followers.applyChanges()) {

            for (Entity e : this.followers.getAddedEntities()) {
                startFollowingPath(e);
            }

            for (Entity e : this.followers.getChangedEntities()) {
           //     startFollowingPath(e);
            }

        }
    }

    private void startFollowingPath(Entity follower) {
        EntityId followerId = follower.getId();
        EntityId pathId = follower.get(FollowPathComponent.class).getPath();

        // check if path really exist
        if (!paths.containsId(pathId)) {
            LOGGER.warning("specified path does not exist, so no following possible");
            return;
        }

        // let follower start following that path by setting the on move component on the first item
        PathComponent pathComponent = paths.getEntity(pathId).get(PathComponent.class);
        List<EntityId> path = pathComponent.getPath();

        // check that that path is not empty
        if (path.isEmpty()) {
            LOGGER.warning("no entities to follow within this path");
            return;
        }


   //     EntityId lastMovedOn = lastMovedOnMap.get(en);

        MovementSpeedComponent movementSpeedComponent = follower.get(MovementSpeedComponent.class);
        float speed = movementSpeedComponent.getSpeed();

        // we start with the first entity
        EntityId entityToMoveOn = path.get(0);
        entityData.setComponent(followerId, new MoveOnComponent(entityToMoveOn, speed));
        LOGGER.info("entity " + followerId + " is now following path " + pathId);
    }

    @Override
    protected void cleanup(Application app) {
        this.followers.release();
        this.followers.clear();
        this.paths.release();
        this.paths.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
