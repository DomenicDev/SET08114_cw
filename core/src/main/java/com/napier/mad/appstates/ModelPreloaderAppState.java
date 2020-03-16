package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;

public class ModelPreloaderAppState extends BaseAppState {

    @Override
    protected void initialize(Application app) {
        AssetManager assetManager = app.getAssetManager();
        assetManager.loadModel(ModelLoaderAppState.LANDSCAPE_TITLE_FILE);
        assetManager.loadModel(ModelLoaderAppState.ROAD_TILE_FILE);
        assetManager.loadModel(ModelLoaderAppState.SUBURB_TILE_FILE);
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
