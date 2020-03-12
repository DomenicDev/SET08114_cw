package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bounding.BoundingBox;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.WireBox;
import com.napier.mad.components.CollisionShapeComponent;
import com.napier.mad.components.WorldTransformComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class DebugCollisionBoxAppState extends BaseAppState {

    private EntitySet collisionBoxEntities;
    private Map<EntityId, Geometry> visibleBoxes = new HashMap<>();
    private Node debugCollisionBoxNode = new Node("Debug_BoundingBox_Node");

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.collisionBoxEntities = entityData.getEntities(CollisionShapeComponent.class, WorldTransformComponent.class);

        getState(SceneAppState.class).getSceneNode().attachChild(debugCollisionBoxNode);
    }

    @Override
    public void update(float tpf) {
        if (collisionBoxEntities.applyChanges()) {

            for (Entity e : collisionBoxEntities.getAddedEntities()) {
                addBox(e);
            }

            for (Entity e: collisionBoxEntities.getChangedEntities()) {
                updateBox(e);
            }

            for (Entity e : collisionBoxEntities.getRemovedEntities()) {
                removeBox(e);
            }

        }
    }

    private void addBox(Entity e) {
        WorldTransformComponent worldTransformComponent = e.get(WorldTransformComponent.class);
        Vector3f center = worldTransformComponent.getWorldTransform().getTranslation();
        CollisionShapeComponent collisionShapeComponent = e.get(CollisionShapeComponent.class);
        Vector3f halfExtends = collisionShapeComponent.getBoundingBoxHalfExtends();
        BoundingBox boundingBox = new BoundingBox(center, halfExtends.x, halfExtends.y, halfExtends.z);
        Geometry geom = WireBox.makeGeometry(boundingBox);
        Material mat = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        geom.setLocalTranslation(center);
        debugCollisionBoxNode.attachChild(geom);
        this.visibleBoxes.put(e.getId(), geom);
    }

    private void updateBox(Entity e) {
        CollisionShapeComponent shapeComponent = e.get(CollisionShapeComponent.class);
        Vector3f updatedTranslation = e.get(WorldTransformComponent.class).getWorldTransform().getTranslation().add(0, shapeComponent.getyOffset(), 0);
        this.visibleBoxes.get(e.getId()).setLocalTranslation(updatedTranslation);
    }

    private void removeBox(Entity e) {
        Geometry geom = this.visibleBoxes.remove(e.getId());
        geom.removeFromParent();
    }

    @Override
    protected void cleanup(Application app) {
        this.collisionBoxEntities.release();
        this.collisionBoxEntities.clear();
        this.collisionBoxEntities = null;
        this.debugCollisionBoxNode.removeFromParent();
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
