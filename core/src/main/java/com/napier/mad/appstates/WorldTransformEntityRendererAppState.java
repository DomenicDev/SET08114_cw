package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.WorldTransformComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class WorldTransformEntityRendererAppState extends BaseAppState {

    private Map<EntityId, Node> entityIdNodeMap = new HashMap<>();
    private EntitySet entities;
    private ModelLoaderAppState modelLoaderAppState;
    private SceneAppState sceneAppState;

    @Override
    protected void initialize(Application app) {
        this.sceneAppState = getState(SceneAppState.class);
        this.modelLoaderAppState = getState(ModelLoaderAppState.class);
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.entities = entityData.getEntities(WorldTransformComponent.class, ModelComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (entities.applyChanges()) {

            for (Entity e : entities.getAddedEntities()) {
                addModel(e);
            }

            for (Entity e : entities.getChangedEntities()) {
                updateModel(e);
            }

            for (Entity e : entities.getRemovedEntities()) {
                removeModel(e);
            }
        }
    }

    private void addModel(Entity e) {
        Node modelNode = modelLoaderAppState.getModelNode(e.getId());
        this.entityIdNodeMap.put(e.getId(), modelNode);
        getSceneNode().attachChild(modelNode);
        updateModel(e);
    }

    private void updateModel(Entity e) {
        // get data from component
        WorldTransformComponent transformComponent = e.get(WorldTransformComponent.class);
        Vector3f worldLocation = transformComponent.getLocation();
        Quaternion worldRotation = transformComponent.getRotation();
        Vector3f worldScale = transformComponent.getScale();

        // apply to model
        Node modelNode = entityIdNodeMap.get(e.getId());
        modelNode.setLocalTranslation(worldLocation);
        modelNode.setLocalRotation(worldRotation);
        modelNode.setLocalScale(worldScale);
    }

    private void removeModel(Entity e) {
        EntityId entityId = e.getId();
        Node modelNode = this.entityIdNodeMap.remove(entityId);
        if (modelNode != null) {
            modelNode.removeFromParent();
        }
    }

    private Node getSceneNode() {
        return this.sceneAppState.getSceneNode();
    }

    @Override
    protected void cleanup(Application app) {
        for (Entity e : entities) {
            removeModel(e);
        }
        this.entityIdNodeMap.clear();
        this.entityIdNodeMap = null;
        this.entities.release();
        this.entities = null;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
