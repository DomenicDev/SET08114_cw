package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class DecayComponent implements EntityComponent {

    private float timeToLive;

    /**
     * Removes the entity on the next tick.
     */
    public DecayComponent() {
        this(0);
    }

    /**
     * Removes the entity after the specified amount of time (in seconds)
     * @param timeToLive the time to live for this entity (in seconds)
     */
    public DecayComponent(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    public float getTimeToLive() {
        return timeToLive;
    }

}
