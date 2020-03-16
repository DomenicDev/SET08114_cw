package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;

/**
 * This app state adds a simple sky box to the scene.
 */
public class SkyAppState extends BaseAppState {

    private Node skyNode;

    @Override
    protected void initialize(Application app) {
        AssetManager assetManager = app.getAssetManager();

        this.skyNode = new Node("SkyNode");

        // load textures
        Texture top = assetManager.loadTexture("Textures/sky/Daylight Box_Top.bmp");
        Texture bottom = assetManager.loadTexture("Textures/sky/Daylight Box_Bottom.bmp");
        Texture back = assetManager.loadTexture("Textures/sky/Daylight Box_Back.bmp");
        Texture front = assetManager.loadTexture("Textures/sky/Daylight Box_Front.bmp");
        Texture left = assetManager.loadTexture("Textures/sky/Daylight Box_Left.bmp");
        Texture right = assetManager.loadTexture("Textures/sky/Daylight Box_Right.bmp");

        // create sky box
        skyNode.attachChild(SkyFactory.createSky(assetManager, left, right, front, back, top, bottom));

        // attach sky to scene
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
