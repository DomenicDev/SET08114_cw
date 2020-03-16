package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.napier.mad.components.ItemComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.controls.ItemRotationControl;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;

public class ItemRotationAppState extends BaseAppState {

    private EntitySet itemsToRotate;
    private Map<EntityId, Node> controlMap = new HashMap<>();
    private ModelLoaderAppState modelLoaderAppState;

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.itemsToRotate = entityData.getEntities(ItemComponent.class, ModelComponent.class);
        this.modelLoaderAppState = getState(ModelLoaderAppState.class);
    }

    @Override
    public void update(float tpf) {
        if (itemsToRotate.applyChanges()) {

            for (Entity e : itemsToRotate.getAddedEntities()) {
                addControl(e);
            }

            for (Entity e : itemsToRotate.getRemovedEntities()) {
                removeControl(e);
            }

        }
    }

    private void addControl(Entity e) {
        EntityId entityId = e.getId();
        Node modelNode = modelLoaderAppState.getModelNode(entityId);
        if (modelNode != null) {
            ItemRotationControl rotationControl = new ItemRotationControl();
            modelNode.addControl(rotationControl);
            controlMap.put(entityId, modelNode);
        }
    }

    private void removeControl(Entity e) {
        EntityId entityId = e.getId();
        Node modelNode = controlMap.remove(entityId);
        if (modelNode != null) {
            modelNode.removeControl(ItemRotationControl.class);
        }
    }

    @Override
    protected void cleanup(Application app) {
        this.itemsToRotate.release();
        this.itemsToRotate.clear();
        this.controlMap.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
