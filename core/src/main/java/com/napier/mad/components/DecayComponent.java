package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class DecayComponent implements EntityComponent {

    private float timeToLive;

    public DecayComponent(float timeToLive) {
        this.timeToLive = timeToLive;
    }

    public float getTimeToLive() {
        return timeToLive;
    }

}
