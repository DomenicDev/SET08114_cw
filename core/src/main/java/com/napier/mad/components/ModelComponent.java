package com.napier.mad.components;

import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityComponent;

public class ModelComponent implements EntityComponent {

    private ModelType type;

    public ModelComponent(ModelType type) {
        this.type = type;
    }

    public ModelType getType() {
        return type;
    }
}
