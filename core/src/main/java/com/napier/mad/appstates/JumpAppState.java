package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.components.JumpComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JumpAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet jumpEntities;

    private Map<EntityId, JumpControl> jumpControlMap = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.jumpEntities = entityData.getEntities(JumpComponent.class, LocalTransformComponent.class);
    }

    @Override
    public void update(float tpf) {

        if (jumpEntities.applyChanges()) {

            for (Entity e : jumpEntities.getAddedEntities()) {
                handleJump(e);
            }

            for (Entity e : jumpEntities.getChangedEntities()) {
                handleJump(e);
            }

        }

        updateJumpingEntities(tpf);
    }

    private void handleJump(Entity e) {
        LocalTransformComponent localTransformComponent = e.get(LocalTransformComponent.class);
        float startHeight = localTransformComponent.getLocation().getY();
        JumpComponent jumpComponent = e.get(JumpComponent.class);
        boolean isJumping = jumpComponent.isJumping();

        if (isJumping) {
            if (!jumpControlMap.containsKey(e.getId())) {
                jumpControlMap.put(e.getId(), new JumpControl(startHeight));
            }
        }
    }

    private void updateJumpingEntities(float tpf) {
        if (jumpControlMap.isEmpty()) {
            return;
        }
        List<EntityId> finishedEntities = new LinkedList<>();
        for (Map.Entry<EntityId, JumpControl> entry : jumpControlMap.entrySet()) {
            EntityId entityId = entry.getKey();
            JumpControl jumpControl = entry.getValue();
            float newHeight = jumpControl.update(tpf);
            applyNewHeight(entityId, newHeight);
            if (jumpControl.jumpFinished) {
                finishedEntities.add(entityId);
            }
        }

        // remove finished jumping controls
        for (EntityId jumpEntity : finishedEntities) {
            jumpControlMap.remove(jumpEntity);
            entityData.setComponent(jumpEntity, new JumpComponent(false));
        }
    }

    private void applyNewHeight(EntityId entityId, float newHeight) {
        // we only want to change the y value of the location, everything else is not touched
        LocalTransformComponent localTransformComponent = jumpEntities.getEntity(entityId).get(LocalTransformComponent.class);
        Vector3f location = localTransformComponent.getLocation();
        Quaternion rotation = localTransformComponent.getRotation();
        Vector3f scale = localTransformComponent.getScale();

        // update the players height, but leave everything else unchanged
        Vector3f newLocation = new Vector3f(location.x, newHeight, location.z);
        entityData.setComponent(entityId, new LocalTransformComponent(newLocation, rotation, scale));
    }

    private static class JumpControl {

        private float startHeight;
        private float timer = 0;
        private boolean jumpFinished = false;

        private JumpControl(float startHeight) {
            this.startHeight = startHeight;
        }

        private float update(float tpf) {
            timer += tpf * 2;
            float maxHeight = 2.5f;
            float height =  -maxHeight * timer * (timer - 2);//f(x) = -2x (x - 2)
            if (height <= 0) {
                jumpFinished = true;
                 return startHeight;
            } else {
                return startHeight + height;
            }
        }

    }

    @Override
    protected void cleanup(Application app) {
        this.jumpEntities.release();
        this.jumpEntities.clear();
        this.jumpControlMap.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
