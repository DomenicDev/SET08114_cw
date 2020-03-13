package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class SideMovementComponent implements EntityComponent {

    public static final int LEFT = -1;
    public static final int STAND = 0;
    public static final int RIGHT = 1;

    private float target;

    public SideMovementComponent(float target) {
        this.target = target;
    }

    public float getTarget() {
        return target;
    }
}
