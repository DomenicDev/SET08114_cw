package com.napier.mad.controls;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class ItemRotationControl extends AbstractControl {

    private static final float ROTATION_SPEED = 2f;
    private static final float WAVE_SPEED = 4f;

    private float timer = 0;

    private Vector3f startLocation = new Vector3f();

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (spatial != null) {
            this.startLocation.set(spatial.getLocalTranslation());
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.timer += tpf;
        this.spatial.rotate(0, tpf * ROTATION_SPEED, 0);
        this.spatial.setLocalTranslation(startLocation.x, startLocation.y + FastMath.sin(timer * WAVE_SPEED) * 0.2f, startLocation.z);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
