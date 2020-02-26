package com.napier.mad.components;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class PhysicsCharacterComponent implements EntityComponent {

    private float radius;
    private float height;
    private float mass;

    private Vector3f walkDirection;
    private Vector3f viewDirection;

    public PhysicsCharacterComponent(float radius, float height, float mass, Vector3f walkDirection, Vector3f viewDirection) {
        this.radius = radius;
        this.height = height;
        this.mass = mass;
        this.walkDirection = walkDirection;
        this.viewDirection = viewDirection;
    }

    public float getRadius() {
        return radius;
    }

    public float getHeight() {
        return height;
    }

    public float getMass() {
        return mass;
    }

    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public Vector3f getViewDirection() {
        return viewDirection;
    }
}
