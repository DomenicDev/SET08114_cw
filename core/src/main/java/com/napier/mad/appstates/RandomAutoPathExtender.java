package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.components.DirectionComponent;
import com.napier.mad.components.FollowPathComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.MoveOnComponent;
import com.napier.mad.components.PathAutoGeneratedComponent;
import com.napier.mad.components.PathComponent;
import com.napier.mad.constants.Constants;
import com.napier.mad.factory.EntityFactory;
import com.napier.mad.types.Direction;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RandomAutoPathExtender extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(RandomAutoPathExtender.class.getName());

    private static final int GENERATION_TRIGGER_REMAINING_ENTITIES = 15;

    private EntityData entityData;
    private EntitySet paths;
    private EntitySet followers;
    private EntitySet directionEntities;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.paths = entityData.getEntities(PathComponent.class, PathAutoGeneratedComponent.class);
        this.followers = entityData.getEntities(FollowPathComponent.class, MoveOnComponent.class);
        this.directionEntities = entityData.getEntities(LocalTransformComponent.class, DirectionComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.paths.applyChanges();
        this.directionEntities.applyChanges();

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

            /*
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
             */

            generateAndAttachNewTiles(pathId);

        }

    }

    private void generateAndAttachNewTiles(EntityId pathId) {
        // get path data
        PathComponent pathComponent = paths.getEntity(pathId).get(PathComponent.class);
        List<EntityId> path = pathComponent.getPath();

        // check if path is empty
        if (path.isEmpty()) {
            // we could add tiles in future here
            LOGGER.warning("path is empty, no tiles are being added");
            return;
        }

        // get last entity
        EntityId lastEntityId = path.get(path.size()-1);
   //     Direction lastDirection = getDirection(lastEntityId);
   //     Vector3f lastLocation = getLocation(lastEntityId);
   //     Quaternion lastRotation = getRotation(lastEntityId);

        List<EntityId> toBeAdded = new ArrayList<>();

        double random = random();
        if (random < 0.3 && random >= 0) {
            toBeAdded.addAll(generateStraightLineAfterEntity(lastEntityId, 5));
          //  toBeAdded = generateStraightLine(getNextLocation(lastLocation, lastDirection), lastRotation, lastDirection, 5);
        } else if (random < 1 && random >= 0.3) {
            toBeAdded.addAll(generateStraightCornerStraight(lastEntityId));
        }

        // add generated entities to path
        path.addAll(toBeAdded);

        // update path entity
        entityData.setComponent(pathId, new PathComponent(path));
    }

    private List<EntityId> generateStraightCornerStraight(EntityId lastEntityId) {
        List<EntityId> result = new ArrayList<>();
        // add straight road first
        List<EntityId> startStraight = generateStraightLineAfterEntity(lastEntityId, 3);
        this.directionEntities.applyChanges();
        EntityId corner = generateCornerToLeft(getLast(startStraight));
        this.directionEntities.applyChanges();
        Direction newDirection = getDirectionAfterTurnLeft(getDirection(corner));
        Quaternion newRotation = getQuaternionFromDirection(newDirection);
        List<EntityId> endStraight = generateStraightLineAfterEntity(getLocation(corner), newRotation, newDirection, 3);

        // add to results
        result.addAll(startStraight);
        result.add(corner);
        result.addAll(endStraight);
        return result;
    }

    private EntityId generateCornerToLeft(EntityId lastEntityId) {
        Direction lastDirection = getDirection(lastEntityId);
        Vector3f lastLocation = getLocation(lastEntityId);
        Quaternion lastRotation = getRotation(lastEntityId);
        return EntityFactory.createCornerToLeft(entityData, getNextLocation(lastLocation, lastDirection), lastRotation, lastDirection);
    }

    private List<EntityId> generateStraightLineAfterEntity(Vector3f location, Quaternion rotation, Direction direction, int count) {
        return generateStraightLine(getNextLocation(location, direction), rotation, direction, count);
    }

    private List<EntityId> generateStraightLineAfterEntity(EntityId lastEntityId, int count) {
        // get last entity's transform
        Direction lastDirection = getDirection(lastEntityId);
        Vector3f lastLocation = getLocation(lastEntityId);
        Quaternion lastRotation = getRotation(lastEntityId);
        return generateStraightLineAfterEntity(lastLocation, lastRotation, lastDirection, count);
    //    return generateStraightLine(getNextLocation(lastLocation, lastDirection), lastRotation, lastDirection, count);
    }

    private List<EntityId> generateStraightLine(Vector3f firstEntityLocation, Quaternion rotation, Direction direction, int count) {
        List<EntityId> results = new ArrayList<>(count);
        Vector3f nextLocation = new Vector3f(firstEntityLocation);
        for (int i = 0; i < count; i++) {
            // create entity and add to result list
            EntityId straight = EntityFactory.createStraightRoad(entityData, nextLocation, rotation, direction);
            results.add(straight);

            // compute next location
            nextLocation = (getNextLocation(nextLocation, direction));


        }
        return results;
    }

    private EntityId getLast(List<EntityId> list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(list.size()-1);
    }


    private Direction getDirectionAfterTurnLeft(Direction lastDirection) {
        if (lastDirection == Direction.NORTH) {
            return Direction.WEST;
        }
        if (lastDirection == Direction.WEST) {
            return Direction.SOUTH;
        }
        if (lastDirection == Direction.SOUTH) {
            return Direction.EAST;
        }
        if (lastDirection == Direction.EAST) {
            return Direction.NORTH;
        }
        return Direction.NORTH;
    }

    private Vector3f getNextLocation(Vector3f lastLocation, Direction direction) {
        Vector3f factor = getFactorVectorForDirection(direction);
        Vector3f nextLoc = new Vector3f();
        nextLoc.setX(Constants.TILE_LENGTH * factor.x + lastLocation.x);
        nextLoc.setY(Constants.TILE_LENGTH * factor.y + lastLocation.y);
        nextLoc.setZ(Constants.TILE_LENGTH * factor.z + lastLocation.z);
        return nextLoc;
    }

    private Direction getDirection(EntityId entityId) {
        Entity directionEntity = directionEntities.getEntity(entityId);
        return directionEntity.get(DirectionComponent.class).getDirection();
    }

    private Vector3f getLocation(EntityId entityId) {
        Entity e = directionEntities.getEntity(entityId);
        return e.get(LocalTransformComponent.class).getLocation();
    }

    private Quaternion getRotation(EntityId entityId) {
        return this.directionEntities.getEntity(entityId).get(LocalTransformComponent.class).getRotation();
    }

    private Vector3f getFactorVectorForDirection(Direction direction) {
        if (direction == Direction.NORTH) {
            return new Vector3f(0, 0, 1);
        }
        if (direction == Direction.EAST) {
            return new Vector3f(-1, 0, 0);
        }
        if (direction == Direction.WEST) {
            return new Vector3f(1, 0, 0);
        }
        if (direction == Direction.SOUTH) {
            return new Vector3f(0, 0, -1);
        }
        return new Vector3f(0, 0, 1);
    }

    private Quaternion getQuaternionFromDirection(Direction direction) {
        if (direction == Direction.NORTH) {
            return new Quaternion(new float[]{0,0,0});
        }
        if (direction == Direction.EAST) {
            return new Quaternion(new float[] {0, -90 * FastMath.DEG_TO_RAD, 0});
        }
        if (direction == Direction.WEST) {
            return new Quaternion(new float[] {0, 90 * FastMath.DEG_TO_RAD, 0});
        }
        if (direction == Direction.SOUTH) {
            return new Quaternion(new float[] {0, -180 * FastMath.DEG_TO_RAD, 0});
        }
        return null;
    }

    private double random() {
        return Math.random();
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