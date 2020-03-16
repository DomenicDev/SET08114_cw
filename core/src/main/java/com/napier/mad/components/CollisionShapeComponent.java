package com.napier.mad.components;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class CollisionShapeComponent implements EntityComponent {

    private Vector3f boundingBoxHalfExtends;
    private float yOffset;

    public CollisionShapeComponent(Vector3f boundingBoxHalfExtends, float yOffset) {
        this.boundingBoxHalfExtends = boundingBoxHalfExtends;
        this.yOffset = yOffset;
    }

    public CollisionShapeComponent(Vector3f boundingBoxHalfExtends) {
        this.boundingBoxHalfExtends = boundingBoxHalfExtends;
        this.yOffset = 0;
    }

    public float getyOffset() {
        return yOffset;
    }

    public Vector3f getBoundingBoxHalfExtends() {
        return boundingBoxHalfExtends;
    }
}
