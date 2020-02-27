package com.napier.mad.components;

import com.napier.mad.types.AnchorMovementType;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class AnchorComponent implements EntityComponent {

    private AnchorMovementType movementType;
    private float speed;
    private EntityId movingEntity;
    private boolean start = false;

    public AnchorComponent(AnchorMovementType movementType, float speed, EntityId movingEntity) {
        this(movementType, speed, movingEntity, false);
    }

    public AnchorComponent(AnchorMovementType movementType, float speed, EntityId nextAnchor, boolean start) {
        this.movementType = movementType;
        this.speed = speed;
        this.movingEntity = nextAnchor;
        this.start = start;
    }

    public EntityId getMovingEntity() {
        return movingEntity;
    }

    public AnchorMovementType getMovementType() {
        return movementType;
    }

    public float getSpeed() {
        return speed;
    }

}
