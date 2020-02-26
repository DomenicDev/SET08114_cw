package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;


public class SceneAppState extends BaseAppState {

    private Node sceneNode;
    private AmbientLight ambientLight;

    @Override
    protected void initialize(Application app) {
        this.sceneNode = new Node("Main Game Scene Node");
        Node rootNode = ((SimpleApplication) app).getRootNode();
        rootNode.attachChild(sceneNode);

        // add some light
        this.ambientLight = new AmbientLight(ColorRGBA.White.mult(1f));
        sceneNode.addLight(ambientLight);
    }

    public Node getSceneNode() {
        return this.sceneNode;
    }

    @Override
    protected void cleanup(Application app) {
        this.sceneNode.removeLight(ambientLight);
        this.sceneNode.removeFromParent();
        this.sceneNode = null;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
