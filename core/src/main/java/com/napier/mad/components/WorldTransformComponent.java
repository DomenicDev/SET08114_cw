package com.napier.mad.components;

import com.jme3.math.Transform;
import com.simsilica.es.EntityComponent;

public class WorldTransformComponent implements EntityComponent {

    private Transform worldTransform;

    public WorldTransformComponent(Transform worldTransform) {
        this.worldTransform = worldTransform;
    }

    public Transform getWorldTransform() {
        return worldTransform;
    }
}
