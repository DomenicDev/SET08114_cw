package com.napier.mad.components;

import com.napier.mad.types.AnchorMovementType;
import com.simsilica.es.EntityComponent;

public class MovableComponent implements EntityComponent {

    private AnchorMovementType movementType;

    public MovableComponent(AnchorMovementType movementType) {
        this.movementType = movementType;
    }

    public AnchorMovementType getMovementType() {
        return movementType;
    }
}
