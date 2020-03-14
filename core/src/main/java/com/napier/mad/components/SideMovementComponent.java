package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class SideMovementComponent implements EntityComponent {

    private float acceleration;

    public SideMovementComponent(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAcceleration() {
        return acceleration;
    }
}
