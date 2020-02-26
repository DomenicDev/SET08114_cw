package com.napier.mad;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.napier.mad.appstates.GameAppStateInitializer;

public class Main extends SimpleApplication {


    private Quaternion rotation = new Quaternion();

    @Override
    public void simpleInitApp() {

        getFlyByCamera().setEnabled(true);

        cam.setLocation(new Vector3f(0, 4, -8));

        stateManager.attach(new GameAppStateInitializer());

    //    stateManager.attach(new GameAppStateInitializer());


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
