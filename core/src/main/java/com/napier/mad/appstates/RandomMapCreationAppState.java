package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.anchors.AnchorListener;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.components.OnMovementFinishedComponent;
import com.napier.mad.constants.Constants;
import com.napier.mad.types.AnchorMovementType;
import com.napier.mad.types.Direction;
import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class RandomMapCreationAppState extends BaseAppState implements AnchorListener {

    private List<EntityId> anchors = new LinkedList<>();
    private List<EntityId> map = new LinkedList<>();
    private EntityData entityData;

    private EntitySet finishedMovement;

    private float TILE_LENGTH = Constants.TILE_LENGTH;

    @Override
    protected void initialize(Application app) {
        getState(AnchorMovementAppState.class).addAnchorListener(this);
        this.entityData = getState(EntityDataAppState.class).getEntityData();

        this.finishedMovement = entityData.getEntities(OnMovementFinishedComponent.class);
        // create some tiles

        // for now...
        createRoad(5, new Vector3f(), Direction.NORTH);
    }

    private void createRoad(int number, Vector3f startLocation, Direction direction) {
        Quaternion rotation = getQuaternionFromDirection(direction);
        Vector3f factor = getFactorVectorForDirection(direction);
        for (int i = 0; i < number; i++) {
            createStraightTile(startLocation.add(TILE_LENGTH * i * factor.x, TILE_LENGTH * i * factor.y, TILE_LENGTH * i * factor.z), rotation);
        }
    }

    private Vector3f getFactorVectorForDirection(Direction direction) {
        if (direction == Direction.NORTH) {
            return new Vector3f(0, 0, 1);
        }
        if (direction == Direction.EAST) {
            return new Vector3f(1, 0, 0);
        }
        if (direction == Direction.WEST) {
            return new Vector3f(-1, 0, 0);
        }
        if (direction == Direction.SOUTH) {
            return new Vector3f(0, 0, -1);
        }
        return null;
    }

    private Quaternion getQuaternionFromDirection(Direction direction) {
        if (direction == Direction.NORTH) {
            return new Quaternion(new float[]{0,0,0});
        }
        if (direction == Direction.EAST) {
            return new Quaternion(new float[] {0, 90 * FastMath.DEG_TO_RAD, 0});
        }
        if (direction == Direction.WEST) {
            return new Quaternion(new float[] {0, -90 * FastMath.DEG_TO_RAD, 0});
        }
        if (direction == Direction.SOUTH) {
            return new Quaternion(new float[] {0, -180 * FastMath.DEG_TO_RAD, 0});
        }
        return null;
    }

    private EntityId createStraightTile(Vector3f location, Quaternion rotation) {
        EntityId road = entityData.createEntity();
        entityData.setComponents(road,
                new ModelComponent(ModelType.Road_Straight),
                new LocalTransformComponent(location, rotation),
                new AttachedToComponent(),
                new MovableComponent(AnchorMovementType.Linear));
        this.map.add(road);
        return road;
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void onFinish(EntityId anchorId) {

    }

    private class MapPosition {
        public float x, y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MapPosition that = (MapPosition) o;
            return Float.compare(that.x, x) == 0 &&
                    Float.compare(that.y, y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
