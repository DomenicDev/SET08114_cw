package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.DecayComponent;
import com.napier.mad.components.DeleteAttachedEntitiesOnRemoveComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.EntitySet;

public class DeleteAttachedEntitiesAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet attachedEntities;
    private EntitySet parents;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.attachedEntities = entityData.getEntities(AttachedToComponent.class);
        this.parents = entityData.getEntities(DeleteAttachedEntitiesOnRemoveComponent.class);
    }

    @Override
    public void update(float tpf) {
        this.attachedEntities.applyChanges();

        if (parents.applyChanges()) {

            for (Entity e : parents.getRemovedEntities()) {
                deleteAttached(e);
            }

        }
    }

    private void deleteAttached(Entity e) {
        EntityId parentId = e.getId();
        for (Entity childEntity : attachedEntities) {
            AttachedToComponent attachedToComponent = childEntity.get(AttachedToComponent.class);
            if (parentId.equals(attachedToComponent.getParentId())) {
                entityData.setComponent(childEntity.getId(), new DecayComponent());
            }
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
}
