package com.napier.mad.types;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public enum AnchorMovementType {

    Linear(new Vector3f(0, 0, -4), new Quaternion(new float[]{0,0,0}));

    public Vector3f location;
    public Quaternion rotation;

    AnchorMovementType(Vector3f location, Quaternion rotation) {
        this.location = location;
        this.rotation = rotation;
    }

}
