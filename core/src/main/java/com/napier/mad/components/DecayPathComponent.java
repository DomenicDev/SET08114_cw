package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

/**
 * Indicates for this path, that entities which will not be moved on anymore
 * are getting removed after some delay.
 */
public class DecayPathComponent implements EntityComponent {

    private float timeToLive;

    public DecayPathComponent(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    public float getTimeToLive() {
        return timeToLive;
    }
}
