package com.napier.mad.components;

import com.napier.mad.types.Direction;
import com.simsilica.es.EntityComponent;

/**
 * The direction component contains logical information about the entities orientation in the game world.
 */
public class DirectionComponent implements EntityComponent {

    private Direction direction;

    public DirectionComponent(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
