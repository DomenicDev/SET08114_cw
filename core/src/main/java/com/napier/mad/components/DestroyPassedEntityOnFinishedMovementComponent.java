package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class DestroyPassedEntityOnFinishedMovementComponent implements EntityComponent {

    private float timeToLive;

    public DestroyPassedEntityOnFinishedMovementComponent() {
        this(0);
    }

    public DestroyPassedEntityOnFinishedMovementComponent(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * This is the time to live after a passed entity (e.g. road) has been passed by this entity.
     * @return the time to live
     */
    public float getTimeToLive() {
        return timeToLive;
    }
}
