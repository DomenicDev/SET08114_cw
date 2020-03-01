package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.anchors.AnchorListener;
import com.napier.mad.components.AnchorComponent;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.MoveOnComponent;
import com.napier.mad.components.OnMovementFinishedComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.types.AnchorMovementType;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class NewMovementAppState extends BaseAppState implements AnchorListener {

    private static final Logger LOGGER = Logger.getLogger(NewMovementAppState.class.getName());

    private EntityData entityData;
    private EntitySet movables;
    private EntitySet movingEntities;

    private Map<EntityId, EntityId> anchorToMovingEntity = new HashMap<>();
    private Map<EntityId, EntityId> anchorToMovable = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.movables = entityData.getEntities(MovableComponent.class);
        this.movingEntities = entityData.getEntities(MoveOnComponent.class);

        getState(AnchorMovementAppState.class).addAnchorListener(this);
    }

    @Override
    public void update(float tpf) {
        this.movables.applyChanges();

        if (movingEntities.applyChanges()) {

            for (Entity e : movingEntities.getAddedEntities()) {
                setOnMove(e);
            }

            for (Entity e: movingEntities.getChangedEntities()) {
                setOnMove(e);
            }

            for (Entity e : movingEntities.getRemovedEntities()) {

            }

        }
    }

    private void setOnMove(Entity e) {
        EntityId movingEntityId = e.getId();
        EntityId movableId = e.get(MoveOnComponent.class).getEntityToMoveOn();
        Entity movableEntity = movables.getEntity(movableId);

        // get data
        MovableComponent movableComponent = movableEntity.get(MovableComponent.class);
        AnchorMovementType movementType = movableComponent.getMovementType();
        float speed =  e.get(MoveOnComponent.class).getSpeed();

        // create anchor, attach anchor to movable entity
        EntityId anchor = createAnchor(movementType, speed, movableId, movingEntityId);

        // attach player to anchor
        entityData.setComponent(movingEntityId, new AttachedToComponent(anchor));
        anchorToMovingEntity.put(anchor, movingEntityId);
        anchorToMovable.put(anchor, movableId);
    }

    private EntityId createAnchor(AnchorMovementType movementType, float speed, EntityId parent, EntityId movingEntity) {
        EntityId anchor = entityData.createEntity();
        entityData.setComponents(anchor,
                new AnchorComponent(movementType, speed, movingEntity),
                new AttachedToComponent(parent),
                new LocalTransformComponent(movementType.location, movementType.rotation),
                new ModelComponent(ModelType.Empty));
        return anchor;
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
        // get data of moving and movable entity
        EntityId movable = this.anchorToMovable.get(anchorId);
        EntityId movedEntity = this.anchorToMovingEntity.get(anchorId);

        // we create a new entity to inform about the movement-finished event
        EntityId finishedEntity = entityData.createEntity();
        entityData.setComponent(finishedEntity, new OnMovementFinishedComponent(movable, movedEntity));
    }
}
