package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class AliveStateComponent implements EntityComponent {

    private boolean alive;

    public AliveStateComponent(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
