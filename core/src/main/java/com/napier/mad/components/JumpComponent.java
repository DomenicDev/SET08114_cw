package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class JumpComponent implements EntityComponent {

    private boolean jumping;

    public JumpComponent(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isJumping() {
        return jumping;
    }
}
