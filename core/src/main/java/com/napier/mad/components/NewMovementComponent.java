package com.napier.mad.components;

import com.napier.mad.types.AnchorMovementType;
import com.simsilica.es.EntityComponent;

public class NewMovementComponent implements EntityComponent {

    AnchorMovementType movementType;

    public NewMovementComponent(AnchorMovementType type) {
        this.movementType = type;
    }

    public AnchorMovementType getMovementType() {
        return movementType;
    }

}
