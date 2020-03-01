package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionGroupListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.napier.mad.components.CollisionEventComponent;
import com.napier.mad.components.CollisionShapeComponent;
import com.napier.mad.components.ModelComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class CollisionAppState extends BaseAppState implements PhysicsCollisionGroupListener {

    private EntityData entityData;
    private EntitySet collidables;

    private BulletAppState bulletAppState;
    private Map<EntityId, RigidBodyControl> rigidBodyControlMap = new HashMap<>();

    private static final int COLLISION_GROUP = 10;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.collidables = entityData.getEntities(CollisionShapeComponent.class, ModelComponent.class);

        this.bulletAppState = new BulletAppState();
        getStateManager().attach(bulletAppState);
        this.bulletAppState.getPhysicsSpace().addCollisionGroupListener(this, COLLISION_GROUP);
        this.bulletAppState.setDebugEnabled(true);
    }

    @Override
    public void update(float tpf) {
        if (collidables.applyChanges()) {

            for (Entity e : collidables.getAddedEntities()) {
                addCollisionBox(e);
            }

            for (Entity e : collidables.getRemovedEntities()) {
                removeCollisionBox(e);
            }
        }

        for (Entity e : collidables) {
            updateCollisionShape(e.getId());
        }
    }

    private void addCollisionBox(Entity e) {
        EntityId entityId = e.getId();
        CollisionShapeComponent collisionShapeComponent = e.get(CollisionShapeComponent.class);
        BoxCollisionShape shape = collisionShapeComponent.getBoxCollisionShape();
        RigidBodyControl rbc = new RigidBodyControl(shape, 0);
        rbc.setUserObject(e.getId());
        rbc.setCollisionGroup(COLLISION_GROUP);
        updateCollisionShape(entityId);

        this.bulletAppState.getPhysicsSpace().add(rbc);
        this.rigidBodyControlMap.put(entityId, rbc);
    }

    private void updateCollisionShape(EntityId entityId) {
        RigidBodyControl rbc = rigidBodyControlMap.get(entityId);
        if (rbc == null) {
            return;
        }
        ModelLoaderAppState modelLoaderAppState = getState(ModelLoaderAppState.class);
        if (modelLoaderAppState == null) {
            return;
        }
        // get updated values
        Node modelNode = modelLoaderAppState.getModelNode(entityId);
        if (modelNode == null) {
            return;
        }
        Vector3f location = modelNode.getWorldTranslation();
        Quaternion rotation = modelNode.getWorldRotation();

        // apply transform
        rbc.setPhysicsLocation(location);
        rbc.setPhysicsRotation(rotation);
    }

    private void removeCollisionBox(Entity e) {
        EntityId entityId = e.getId();
        RigidBodyControl rbc = rigidBodyControlMap.remove(entityId);
        if (rbc != null) {
            this.bulletAppState.getPhysicsSpace().remove(rbc);
        }
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
    public boolean collide(PhysicsCollisionObject nodeA, PhysicsCollisionObject nodeB) {
        EntityId entityIdA = getEntityId(nodeA);
        EntityId entityIdB = getEntityId(nodeB);

        // create collision entity
        EntityId collision = entityData.createEntity();
        entityData.setComponents(collision, new CollisionEventComponent(entityIdA, entityIdB));
        return false;
    }

    private EntityId getEntityId(PhysicsCollisionObject object) {
        return (EntityId) object.getUserObject();
    }
}
