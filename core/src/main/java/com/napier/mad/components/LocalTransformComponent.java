package com.napier.mad.components;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class LocalTransformComponent implements EntityComponent {

    private Vector3f location;
    private Quaternion rotation;
    private Vector3f scale;

    public LocalTransformComponent() {
        this(new Vector3f(0,0,0));
    }

    public LocalTransformComponent(Vector3f location) {
        this(location, new Quaternion(new float[] {0,0,0}));
    }

    public LocalTransformComponent(Vector3f location, Quaternion rotation) {
        this(location, rotation, Vector3f.UNIT_XYZ.clone());
    }

    public LocalTransformComponent(Vector3f location, Quaternion rotation, Vector3f scale) {
        this.location = location;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Vector3f getLocation() {
        return location;
    }

    public Vector3f getScale() {
        return scale;
    }
}
