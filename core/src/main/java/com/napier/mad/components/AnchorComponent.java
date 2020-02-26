package com.napier.mad.components;

import com.napier.mad.types.AnchorMovementType;
import com.simsilica.es.EntityComponent;

public class AnchorComponent implements EntityComponent {

    private AnchorMovementType movementType;
    private float speed;

    public AnchorComponent(AnchorMovementType movementType, float speed) {
        this.movementType = movementType;
        this.speed = speed;
    }

    public AnchorMovementType getMovementType() {
        return movementType;
    }

    public float getSpeed() {
        return speed;
    }
}
