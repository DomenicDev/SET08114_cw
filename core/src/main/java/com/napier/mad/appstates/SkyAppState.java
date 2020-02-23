package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;


public class SkyAppState extends BaseAppState {

    private Node skyNode;

    @Override
    protected void initialize(Application app) {
        AssetManager assetManager = app.getAssetManager();
        Texture skyTexture = assetManager.loadTexture("Interface/cat.jpg");

        this.skyNode = new Node("SkyNode");

        Spatial sky = SkyFactory.createSky(assetManager,  skyTexture, Vector3f.UNIT_XYZ, SkyFactory.EnvMapType.SphereMap);
        skyNode.attachChild(sky);

        Node sceneNode = getStateManager().getState(SceneAppState.class).getSceneNode();
        sceneNode.attachChild(skyNode);
    }

    @Override
    protected void cleanup(Application app) {
        this.skyNode.removeFromParent();
        this.skyNode = null;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
