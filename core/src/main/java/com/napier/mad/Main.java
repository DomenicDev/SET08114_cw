package com.napier.mad;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.napier.mad.appstates.GameAppStateInitializer;

public class Main extends SimpleApplication {

    private Geometry geom;

    private Quaternion rotation = new Quaternion();

    @Override
    public void simpleInitApp() {
        /*
        Box b = new Box(1, 1, 1);
        this.geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);

        Texture cube1Tex = assetManager.loadTexture(
                "Interface/cat.jpg");
        mat.setTexture("ColorMap", cube1Tex);

        geom.setMaterial(mat);
        rootNode.attachChild(geom);


         */

        AmbientLight ambientLight = new AmbientLight(ColorRGBA.White.mult(1f));
        rootNode.addLight(ambientLight);

        getFlyByCamera().setEnabled(false);

        stateManager.attach(new GameAppStateInitializer());


    }

    public void setRotationOfCube(float x, float y, float z, float w) {
        rotation.set(x, y, z, w);
    }


    @Override
    public void simpleUpdate(float tpf) {
      //  this.geom.rotate(2 * tpf, 1 * tpf, 0);

//        this.geom.rotate(x * tpf, y * tpf, z * tpf);
   //     this.geom.setLocalRotation(new Quaternion().fromAngles(x, y, z));
   //     this.geom.setLocalRotation(rotation);
     //   setRotationOfCube(0, 0, 0);

    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
}
