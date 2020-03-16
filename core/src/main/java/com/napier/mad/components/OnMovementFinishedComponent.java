package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class OnMovementFinishedComponent implements EntityComponent {

    private EntityId movableEntityId;
    private EntityId movingEntityId;


    public OnMovementFinishedComponent(EntityId movableEntityId, EntityId movingEntityId) {
        this.movableEntityId = movableEntityId;
        this.movingEntityId = movingEntityId;
    }

    public EntityId getMovableEntityId() {
        return movableEntityId;
    }

    public EntityId getMovingEntityId() {
        return movingEntityId;
    }
}
