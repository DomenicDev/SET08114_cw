package com.napier.mad.components;

import com.simsilica.es.EntityComponent;

public class ModelComponent implements EntityComponent {

    public enum ModelType {
        Ball,
        Platform;
    }

    private ModelType modelType;

    public ModelComponent(ModelType modelType) {
        this.modelType = modelType;
    }

    public ModelType getModelType() {
        return modelType;
    }
}
