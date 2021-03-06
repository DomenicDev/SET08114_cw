package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.anchors.AnchorListener;
import com.napier.mad.anchors.AnchorLogic;
import com.napier.mad.anchors.RotationAnchor;
import com.napier.mad.anchors.StraightAnchor;
import com.napier.mad.components.AnchorComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.constants.GameConstants;
import com.napier.mad.types.AnchorMovementType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AnchorMovementAppState extends BaseAppState implements AnchorListener {

    private static final Logger LOGGER = Logger.getLogger(AnchorMovementAppState.class.getName());

    private EntityData entityData;
    private EntitySet anchors;

    private Map<EntityId, AnchorLogic> anchorLogicMap = new HashMap<>();
    private List<AnchorListener> anchorListeners = new ArrayList<>();

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.anchors = entityData.getEntities(AnchorComponent.class, LocalTransformComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (anchors.applyChanges()) {

            for (Entity e : anchors.getAddedEntities()) {
                add(e);
            }

            for (Entity e : anchors.getRemovedEntities()) {
                remove(e);
            }

        }

        // update anchor
        for (Entity anchor : anchors) {
            anchorLogicMap.get(anchor.getId()).update(tpf);
        }
    }

    private void add(Entity e) {
        EntityId anchorId = e.getId();
        AnchorComponent anchorComponent = e.get(AnchorComponent.class);
        LocalTransformComponent localTransform = e.get(LocalTransformComponent.class);
        float speed = anchorComponent.getSpeed();
        AnchorMovementType movementType = anchorComponent.getMovementType();

        AnchorLogic anchorLogic = null;

        if (movementType == AnchorMovementType.Linear) {
            // create straight movement control
            anchorLogic = new StraightAnchor(GameConstants.TILE_LENGTH, localTransform.getLocation());
        } else if (movementType == AnchorMovementType.CornerToLeft) {
            anchorLogic = new RotationAnchor(localTransform, 4, 0, 4, 4);
        } else if (movementType == AnchorMovementType.CornerToRight) {
            anchorLogic = new RotationAnchor(localTransform, -4, 0, -4, 4);
        }

        if (anchorLogic == null) {
            LOGGER.warning("anchor logic is null, returning...");
            return;
        }
        // set essential parameters
        anchorLogic.setEntityData(this.entityData);
        anchorLogic.setAnchorEntityId(anchorId);
        anchorLogic.setSpeed(speed);
        anchorLogic.setListener(this);

        // put anchor into map
        this.anchorLogicMap.put(anchorId, anchorLogic);
    }

    private void remove(Entity e) {
        this.anchorLogicMap.remove(e.getId());
    }

    public void addAnchorListener(AnchorListener listener) {
        this.anchorListeners.add(listener);
    }

    public void removeAnchorListener(AnchorListener listener) {
        this.anchorListeners.remove(listener);
    }


    @Override
    protected void cleanup(Application app) {
        this.anchors.release();
        this.anchors.clear();
        this.anchorListeners.clear();
        this.anchorLogicMap.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    // ---------- LISTENER METHODS ---------------- //

    @Override
    public void onFinish(EntityId anchorId) {
        for (AnchorListener listener : anchorListeners) {
            listener.onFinish(anchorId);
        }
    }
}
