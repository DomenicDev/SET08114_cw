package com.napier.mad.components;

import com.jme3.math.Vector3f;
import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class PhysicsPushComponent implements EntityComponent {

    private EntityId targetEntity;

    private Vector3f direction;
    private float strength;

    public PhysicsPushComponent(EntityId targetEntity, Vector3f direction, float strength) {
        this.targetEntity = targetEntity;
        this.direction = direction;
        this.strength = strength;
    }

    public EntityId getTargetEntity() {
        return targetEntity;
    }

    public Vector3f getDirection() {
        return direction;
    }


    public float getStrength() {
        return strength;
    }

}
