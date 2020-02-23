package com.napier.mad.components.shapes;

import com.jme3.math.Vector3f;

public class BoxCollisionShapeData extends CollisionShapeData {

    private final Vector3f halfExtends;

    public BoxCollisionShapeData(Vector3f halfExtends) {
        this.halfExtends = halfExtends.clone();
    }

    public Vector3f getHalfExtends() {
        return halfExtends;
    }
}
