package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class ItemComponent implements EntityComponent {

    private int points;

    public ItemComponent(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

}
