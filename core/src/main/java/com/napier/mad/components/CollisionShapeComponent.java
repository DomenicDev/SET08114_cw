package com.napier.mad.components;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class CollisionShapeComponent implements EntityComponent {

    private Vector3f boundingBoxHalfExtends;

    public CollisionShapeComponent(Vector3f boundingBoxHalfExtends) {
        this.boundingBoxHalfExtends = boundingBoxHalfExtends;
    }

    public Vector3f getBoundingBoxHalfExtends() {
        return boundingBoxHalfExtends;
    }
}
