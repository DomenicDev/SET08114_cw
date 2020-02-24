package com.napier.mad.components;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class PlayerMovementComponent implements EntityComponent {

    private Vector3f direction;
    private float speed;

    public PlayerMovementComponent(Vector3f direction, float speed) {
        this.direction = direction;
        this.speed = speed;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public float getSpeed() {
        return speed;
    }
}
