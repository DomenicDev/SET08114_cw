package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class MovementSpeedComponent implements EntityComponent {

    private float speed;

    public MovementSpeedComponent(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }
}
