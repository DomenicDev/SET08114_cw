package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class LocalTransformAppState extends BaseAppState {

    private Map<EntityId, Node> entityIdNodeMap = new HashMap<>();
    private EntitySet entities;
    private ModelLoaderAppState modelLoaderAppState;
    private SceneAppState sceneAppState;

    @Override
    protected void initialize(Application app) {
        this.sceneAppState = getState(SceneAppState.class);
        this.modelLoaderAppState = getState(ModelLoaderAppState.class);
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.entities = entityData.getEntities(LocalTransformComponent.class, ModelComponent.class);
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
        updateModel(e);
    }

    private void updateModel(Entity e) {
        // get data from component
        LocalTransformComponent transformComponent = e.get(LocalTransformComponent.class);
        Vector3f location = transformComponent.getLocation();
        Quaternion rotation = transformComponent.getRotation();
        Vector3f scale = transformComponent.getScale();

        // apply to model
        Node modelNode = entityIdNodeMap.get(e.getId());
        modelNode.setLocalTranslation(location);
        modelNode.setLocalRotation(rotation);
        modelNode.setLocalScale(scale);
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
