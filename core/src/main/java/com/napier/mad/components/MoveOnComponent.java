package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

public class MoveOnComponent implements EntityComponent {

    private EntityId entityToMoveOn;
    private float speed;

    public MoveOnComponent(EntityId entityToMoveOn, float speed) {
        this.entityToMoveOn = entityToMoveOn;
        this.speed = speed;
    }

    public EntityId getEntityToMoveOn() {
        return entityToMoveOn;
    }

    public float getSpeed() {
        return speed;
    }
}
