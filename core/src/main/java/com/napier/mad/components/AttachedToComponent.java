package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class AttachedToComponent implements EntityComponent {

    private EntityId parentId;

    /**
     * Use this if the object shall be added to the scene but has no direct parent entity.
     */
    public AttachedToComponent() {
        this.parentId = null;
    }

    /**
     * The entity with this component will be treated as a child object
     * of the specified entity.
     * @param parentId the parent id to attach that child entity to
     */
    public AttachedToComponent(EntityId parentId) {
        this.parentId = parentId;
    }

    public EntityId getParentId() {
        return parentId;
    }
}
