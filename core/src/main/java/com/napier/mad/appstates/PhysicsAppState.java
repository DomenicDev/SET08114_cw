package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.napier.mad.components.PositionComponent;
import com.napier.mad.components.RigidBodyComponent;
import com.napier.mad.components.shapes.BoxCollisionShapeData;
import com.napier.mad.components.shapes.CollisionShapeData;
import com.napier.mad.components.shapes.SphereCollisionShapeData;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PhysicsAppState extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(PhysicsAppState.class.getName());

    private EntitySet physicsEntities;
    private EntityData entityData;
    private Map<EntityId, RigidBodyControl> rigidBodies = new HashMap<>();

    private BulletAppState bulletAppState;

    @Override
    protected void initialize(Application app) {
        AppStateManager stateManager = app.getStateManager();
        this.entityData = stateManager.getState(EntityDataAppState.class).getEntityData();

        this.bulletAppState = new BulletAppState();
        this.bulletAppState.setDebugEnabled(false);
        stateManager.attach(bulletAppState);

        this.physicsEntities = entityData.getEntities(RigidBodyComponent.class, PositionComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (physicsEntities.applyChanges()) {

            for (Entity e : physicsEntities.getAddedEntities()) {
                createRigidBodyControl(e);
            }

            for (Entity e : physicsEntities.getChangedEntities()) {

            }

            for (Entity e : physicsEntities.getRemovedEntities()) {

            }

        }

        // apply new locations to all moving physical entities

        for (Entity e : physicsEntities) {
            EntityId entityId = e.getId();
            RigidBodyControl physicsControl = rigidBodies.get(entityId);

            PositionComponent positionComponent = e.get(PositionComponent.class);

            Vector3f componentLocation = positionComponent.getLocation();
            Vector3f physicsLocation = physicsControl.getPhysicsLocation();

            if (componentLocation.equals(physicsLocation)) {
                // location did not change, we can continue
                continue;
            }

            // location did change, we apply new position to component
            entityData.setComponent(entityId, new PositionComponent(physicsLocation));
        }

    }

    private void createRigidBodyControl(Entity e) {
        EntityId entityId = e.getId();
        RigidBodyComponent rigidBodyComponent = e.get(RigidBodyComponent.class);
        PositionComponent positionComponent = e.get(PositionComponent.class);

        // create control
        RigidBodyControl rigidBodyControl = createRigidBodyControl(rigidBodyComponent);
        applyLocation(positionComponent, rigidBodyControl);

        // add control to physics space and global map
        this.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        this.rigidBodies.put(entityId, rigidBodyControl);
    }

    private RigidBodyControl createRigidBodyControl(RigidBodyComponent component) {
        CollisionShapeData collisionShapeData = component.getCollisionShapeData();

        // create proper collision shape
        CollisionShape collisionShape = getCollisionShape(collisionShapeData);
        if (collisionShape == null) {
            LOGGER.warning("no collision shape for shape data created. Data: " + collisionShapeData);
        }

        // create rigid body control and apply values
        RigidBodyControl control = new RigidBodyControl();
        control.setMass(component.getMass());
        control.setCollisionShape(collisionShape);
        return control;
    }

    private CollisionShape getCollisionShape(CollisionShapeData collisionShapeData) {
        CollisionShape collisionShape = null;
        if (collisionShapeData instanceof SphereCollisionShapeData) {
            SphereCollisionShapeData sphereData = (SphereCollisionShapeData) collisionShapeData;
            collisionShape = new SphereCollisionShape(sphereData.getRadius());
        } else if (collisionShapeData instanceof BoxCollisionShapeData) {
            BoxCollisionShapeData boxData = (BoxCollisionShapeData) collisionShapeData;
            collisionShape = new BoxCollisionShape(boxData.getHalfExtends());
        }
        return collisionShape;
    }

    private void applyLocation(PositionComponent position, RigidBodyControl control) {
        control.setPhysicsLocation(position.getLocation());
    }

    public RigidBodyControl getRigidBodyControl(EntityId entityId) {
        return this.rigidBodies.get(entityId);
    }

    @Override
    protected void cleanup(Application app) {
        getStateManager().detach(this.bulletAppState);

        this.physicsEntities.release();
        this.physicsEntities.clear();
        this.physicsEntities = null;
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
