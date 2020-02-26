package com.napier.mad.components;

import com.napier.mad.types.AnchorMovementType;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class AnchorComponent implements EntityComponent {

    private AnchorMovementType movementType;
    private float speed;
    private EntityId nextAnchor;
    private boolean start = false;

    public AnchorComponent(AnchorMovementType movementType, float speed, EntityId nextAnchor) {
        this(movementType, speed, nextAnchor, false);
    }

    public AnchorComponent(AnchorMovementType movementType, float speed, EntityId nextAnchor, boolean start) {
        this.movementType = movementType;
        this.speed = speed;
        this.nextAnchor = nextAnchor;
        this.start = start;
    }

    public EntityId getNextAnchor() {
        return nextAnchor;
    }

    public AnchorMovementType getMovementType() {
        return movementType;
    }

    public float getSpeed() {
        return speed;
    }

}
