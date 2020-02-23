package com.napier.mad.components;

import com.napier.mad.components.shapes.CollisionShapeData;
import com.simsilica.es.EntityComponent;

public class RigidBodyComponent implements EntityComponent {

    private float mass;
    private CollisionShapeData collisionShapeData;

    public RigidBodyComponent(float mass, CollisionShapeData collisionShapeData) {
        this.mass = mass;
        this.collisionShapeData = collisionShapeData;
    }

    public float getMass() {
        return mass;
    }

    public CollisionShapeData getCollisionShapeData() {
        return collisionShapeData;
    }
}
