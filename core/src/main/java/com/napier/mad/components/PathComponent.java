package com.napier.mad.components;

import com.simsilica.es.EntityComponent;
import com.simsilica.es.EntityId;

import java.util.List;

/**
 * Used by "map" (path) entities
 */
public class PathComponent implements EntityComponent {

    private List<EntityId> path;

    public PathComponent(List<EntityId> path) {
        this.path = path;
    }

    public List<EntityId> getPath() {
        return path;
    }
}
