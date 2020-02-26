package com.napier.mad.components;

import com.napier.mad.types.TileOrientation;
import com.napier.mad.types.TileType;
import com.simsilica.es.EntityComponent;

public class TileTypeComponent implements EntityComponent {


    private TileType type;
    private TileOrientation orientation;

    public TileTypeComponent(TileType type, TileOrientation orientation) {
        this.type = type;
        this.orientation = orientation;
    }

    public TileType getType() {
        return type;
    }

    public TileOrientation getOrientation() {
        return orientation;
    }
}
