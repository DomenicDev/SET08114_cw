package com.napier.mad.components.shapes;

public class SphereCollisionShapeData extends CollisionShapeData {

    private float radius;

    public SphereCollisionShapeData(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
}
