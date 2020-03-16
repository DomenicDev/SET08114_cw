package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.DepthOfFieldFilter;
import com.jme3.scene.Node;


public class SceneAppState extends BaseAppState {

    private Node sceneNode;
    private AmbientLight ambientLight;
    private DirectionalLight directionalLight;

    @Override
    protected void initialize(Application app) {
        // setup main scene node
        this.sceneNode = new Node("Main Game Scene Node");
        Node rootNode = ((SimpleApplication) app).getRootNode();
        rootNode.attachChild(sceneNode);

        // add some ambient light
        this.ambientLight = new AmbientLight(ColorRGBA.White.mult(0.6f));
        sceneNode.addLight(ambientLight);

        // add a direction light with some warm color
        this.directionalLight = new DirectionalLight(new Vector3f(-1, -1, 0), ColorRGBA.Orange);
        this.sceneNode.addLight(directionalLight);
    }

    public Node getSceneNode() {
        return this.sceneNode;
    }

    @Override
    protected void cleanup(Application app) {
        this.sceneNode.removeLight(ambientLight);
        this.sceneNode.removeLight(directionalLight);
        this.sceneNode.removeFromParent();
        this.sceneNode = null;
    }

    @Override
    protected void onEnable() {}

    @Override
    protected void onDisable() {}
}
