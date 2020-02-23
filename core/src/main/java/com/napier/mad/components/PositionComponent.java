package com.napier.mad.components;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;

public class PositionComponent implements EntityComponent {

    private Vector3f location;

    public PositionComponent(Vector3f location) {
        this.location = location;
    }

    public Vector3f getLocation() {
        return location;
    }
}
