package com.napier.mad.components;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class WorldTransformComponent implements EntityComponent {

    private Vector3f location;
    private Quaternion rotation;
    private Vector3f scale;

    public WorldTransformComponent(Vector3f location, Quaternion rotation) {
        this(location, rotation, Vector3f.UNIT_XYZ.clone());
    }

    public WorldTransformComponent(Vector3f location, Quaternion rotation, Vector3f scale) {
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
