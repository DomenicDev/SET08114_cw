package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class AttachedToComponent implements EntityComponent {

    private EntityId parentId;

    public AttachedToComponent(EntityId parentId) {
        this.parentId = parentId;
    }

    public EntityId getParentId() {
        return parentId;
    }
}
