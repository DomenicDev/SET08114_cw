package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.ModelComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ModelAttachAppState extends BaseAppState {

    private static final Logger LOGGER = Logger.getLogger(ModelAttachAppState.class.getName());

    private EntitySet attachedEntities;
    private ModelLoaderAppState modelLoaderAppState;
    private Map<EntityId, Node> attachedModels = new HashMap<>();

    @Override
    protected void initialize(Application app) {
        this.modelLoaderAppState = getState(ModelLoaderAppState.class);

        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.attachedEntities = entityData.getEntities(AttachedToComponent.class, ModelComponent.class);
    }

    @Override
    public void update(float tpf) {
        if (attachedEntities.applyChanges()) {

            for (Entity e : attachedEntities.getAddedEntities()) {
                attachEntity(e);
            }

            for (Entity e : attachedEntities.getChangedEntities()) {
                attachEntity(e);
            }

            for (Entity e : attachedEntities.getRemovedEntities()) {
                detachEntity(e);
            }

        }
    }

    private void attachEntity(Entity e) {
        EntityId childId = e.getId();
        AttachedToComponent attachComponent = e.get(AttachedToComponent.class);
        EntityId parentId = attachComponent.getParentId();
        Node parentNode = modelLoaderAppState.getModelNode(parentId);
        if (parentNode == null) {
            LOGGER.warning("node for parent entity does not exist.");
            return;
        }
        Node childNode = modelLoaderAppState.getModelNode(childId);
        if (childNode == null) {
            LOGGER.warning("node for child entity does not exist");
            return;
        }
        parentNode.attachChild(childNode);
        this.attachedModels.put(childId, childNode);
    }

    private void detachEntity(Entity e) {
        EntityId childId = e.getId();
        Node childNode = this.attachedModels.remove(childId);
        if (childNode != null) {
            childNode.removeFromParent();
        }
    }

    @Override
    protected void cleanup(Application app) {
        for (Entity e : attachedEntities) {
            detachEntity(e);
        }
        this.attachedEntities.release();
        this.attachedEntities.clear();
        this.attachedEntities = null;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
