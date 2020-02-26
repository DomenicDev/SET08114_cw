package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.PhysicsCharacterComponent;
import com.napier.mad.components.PositionComponent;
import com.napier.mad.physics.DodgeDemonCharacterControl;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class PhysicsCharacterAppState extends BaseAppState {

    private EntitySet entities;
    private Map<EntityId, DodgeDemonCharacterControl> characterControlMap = new HashMap<>();

    private BulletAppState bulletAppState;

    private VisualisationAppState visualisationAppState;

    @Override
    protected void initialize(Application app) {
        this.visualisationAppState = getState(VisualisationAppState.class);
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.entities = entityData.getEntities(PhysicsCharacterComponent.class, PositionComponent.class, ModelComponent.class);

        this.bulletAppState = getState(PhysicsAppState.class).getBulletAppState();
    }

    @Override
    public void update(float tpf) {
        if (entities.applyChanges()) {

            for (Entity e : entities.getAddedEntities()) {
                createCharacterControl(e);
            }

            for (Entity e : entities.getChangedEntities()) {
                updateCharacterControl(e);

            }

            for (Entity e: entities.getRemovedEntities()) {
                // TODO... remove entity
            }

        }


        for (Entity e : entities) {
            // update position component based on physics location
            PositionComponent positionComponent = e.get(PositionComponent.class);
            PhysicsRigidBody rigidBody = characterControlMap.get(e.getId()).getPhysicsRigidBody();
            Vector3f location = positionComponent.getLocation();
            Vector3f physicsLocation = rigidBody.getPhysicsLocation();
            if (location.equals(physicsLocation)) {
                continue;
            }
            e.set(new PositionComponent(physicsLocation));

        }
    }

    private void createCharacterControl(Entity e) {
        PositionComponent positionComponent = e.get(PositionComponent.class);
        PhysicsCharacterComponent characterComponent = e.get(PhysicsCharacterComponent.class);
        float radius = characterComponent.getRadius();
        float height = characterComponent.getHeight();
        float mass = characterComponent.getMass();

        Vector3f walkDirection = characterComponent.getWalkDirection();
        Vector3f viewDirection = characterComponent.getViewDirection();

        DodgeDemonCharacterControl control = new DodgeDemonCharacterControl(radius, height, mass);
        control.setWalkDirection(walkDirection);
        control.setViewDirection(viewDirection);
        control.getPhysicsRigidBody().setPhysicsLocation(positionComponent.getLocation());

        // add spatial
        Node model = this.visualisationAppState.getModel(e.getId());
        model.addControl(control);
        control.setViewDirection(characterComponent.getViewDirection());
        control.setWalkDirection(characterComponent.getWalkDirection());

        bulletAppState.getPhysicsSpace().add(control);
        characterControlMap.put(e.getId(), control);
    }

    private void updateCharacterControl(Entity e) {
        PhysicsCharacterComponent characterComponent = e.get(PhysicsCharacterComponent.class);
        DodgeDemonCharacterControl control = characterControlMap.get(e.getId());

        if (!control.getViewDirection().equals(characterComponent.getViewDirection())) {
            System.out.println("VIEW DIRECTION CHANGED !!!!" + characterComponent.getViewDirection() + " | " + control.getViewDirection());
        }

        control.setViewDirection(characterComponent.getViewDirection());
        control.setWalkDirection(characterComponent.getWalkDirection());

    }

    @Override
    protected void cleanup(Application app) {
        this.entities.release();
        this.entities = null;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
