package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class CollisionEventComponent implements EntityComponent {

    private EntityId entityIdA;
    private EntityId entityIdB;

    public CollisionEventComponent(EntityId entityIdA, EntityId entityIdB) {
        this.entityIdA = entityIdA;
        this.entityIdB = entityIdB;
    }

    public EntityId getEntityIdA() {
        return entityIdA;
    }

    public EntityId getEntityIdB() {
        return entityIdB;
    }
}
