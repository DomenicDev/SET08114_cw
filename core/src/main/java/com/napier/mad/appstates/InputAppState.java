package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.TouchInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.TouchListener;
import com.jme3.input.controls.TouchTrigger;
import com.jme3.input.event.TouchEvent;
import com.jme3.math.Vector3f;
import com.napier.mad.components.PhysicsCharacterComponent;
import com.napier.mad.components.PhysicsPushComponent;
import com.napier.mad.components.PlayerMovementComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class InputAppState extends BaseAppState implements TouchListener {

    private InputManager inputManager;

    private EntityData entityData;

    public static final String PUSH = "PUSH";

    private EntityId playerEntityId = new EntityId(0); // for now, replace later

    @Override
    protected void initialize(Application app) {
        this.entityData = getStateManager().getState(EntityDataAppState.class).getEntityData();

        this.inputManager = app.getInputManager();
        this.inputManager.addMapping(PUSH, new TouchTrigger(TouchInput.ALL));
        this.inputManager.addListener(this, PUSH);
    }

    @Override
    protected void cleanup(Application app) {
        this.inputManager.removeListener(this);
        this.inputManager.deleteMapping(PUSH);
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}


    @Override
    public void onTouch(String name, TouchEvent event, float tpf) {
        if (PUSH.equals(name)) {
            if (event.getType().equals(TouchEvent.Type.TAP)) {
                EntityId pushEntity = entityData.createEntity();
            //    entityData.setComponent(pushEntity, new PhysicsPushComponent(new EntityId(0), new Vector3f(0, 1, 0).normalizeLocal(), 10));

            }
        }
    }

    public void onSwipeLeft() {
        //PlayerMovementComponent movementComponent = entityData.getComponent(playerEntityId, PlayerMovementComponent.class);
        //Vector3f newDir = movementComponent.getDirection().normalize().crossLocal(Vector3f.UNIT_Y).negateLocal();
        //entityData.setComponent(playerEntityId, new PlayerMovementComponent(newDir, movementComponent.getSpeed()));


        PhysicsCharacterComponent charComp = entityData.getComponent(playerEntityId, PhysicsCharacterComponent.class);
        Vector3f newWalkDir = charComp.getWalkDirection().cross(Vector3f.UNIT_Y).negateLocal();
        entityData.setComponent(playerEntityId, new PhysicsCharacterComponent(charComp.getRadius(), charComp.getHeight(), charComp.getMass(), newWalkDir, newWalkDir.normalize()));


    }

    public void onSwipeRight() {

    }
}
