package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class SideMovementComponent implements EntityComponent {

    public static final int LEFT = -1;
    public static final int STAND = 0;
    public static final int RIGHT = 1;

    private int movement;

    public SideMovementComponent(int movement) {
        this.movement = movement;
    }

    public int getMovement() {
        return movement;
    }
}
