package com.napier.mad.anchors;

import com.napier.mad.components.LocalTransformComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import java.util.ArrayList;

public abstract class AnchorLogic {

    private AnchorListener listener;

    // set automatically
    protected EntityData entityData;
    protected EntityId anchorEntityId;
    protected float speed;

    private boolean finished = false;

    abstract void onEntityEnter();

    public void update(float tpf) {
        if (finished) {
            return;
        }
        updateMovement(tpf);
    }

    protected abstract void updateMovement(float tpf);

    public void onMovementFinished() {
        this.finished = true;
        listener.onFinish(anchorEntityId);
    }

    public void setListener(AnchorListener listener) {
        this.listener = listener;
    }

    public void setEntityData(EntityData entityData) {
        this.entityData = entityData;
    }

    public void setAnchorEntityId(EntityId anchorEntityId) {
        this.anchorEntityId = anchorEntityId;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    void onUpdateTransform(LocalTransformComponent newTransform) {
        entityData.setComponent(anchorEntityId, newTransform);
    }



}
