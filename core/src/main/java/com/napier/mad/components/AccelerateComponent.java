package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class AccelerateComponent implements EntityComponent {

    private float acceleration;

    public AccelerateComponent(float acceleration) {
        this.acceleration = acceleration;
    }

    public float getAcceleration() {
        return acceleration;
    }
}
