package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

/**
 * Indicates that an entity follows a path
 */
public class FollowPathComponent implements EntityComponent {

    private EntityId path;

    public FollowPathComponent(EntityId path) {
        this.path = path;
    }

    public EntityId getPath() {
        return path;
    }
}
