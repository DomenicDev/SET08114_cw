package com.napier.mad.anchors;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.napier.mad.components.LocalTransformComponent;

public class RotationAnchor extends AnchorLogic {

    private float timer;

    private float distanceX;
    private float distanceY;

    private float MAX_ANGLE = 90 * FastMath.DEG_TO_RAD;

    private Vector2f targetPosition = new Vector2f();
    private float startX;
    private float startY;
    private float startZ;

    private Vector3f centerCircle = new Vector3f();

    private Vector3f newTransform = new Vector3f();
    private Quaternion newRotation = new Quaternion();

    public RotationAnchor(LocalTransformComponent anchorTransform, float centerOffsetX, float centerOffsetZ, float distanceX, float distanceZ) {
        this.distanceX = distanceX;
        this.distanceY = distanceZ;

        Vector3f anchorLocation = anchorTransform.getLocation();
        this.centerCircle.setX(anchorLocation.getX() + centerOffsetX);
        this.centerCircle.setY(anchorLocation.getY());
        this.centerCircle.setZ(anchorLocation.getZ() + centerOffsetZ);

        // store init values
        this.startX = anchorLocation.x;
        this.startY = anchorLocation.y;
        this.startZ = anchorLocation.z;

        // compute target position
        this.targetPosition.x = anchorLocation.x + distanceX;
        this.targetPosition.y = anchorLocation.z + distanceZ;

        // setup
        this.timer = 0; // fresh start
    }

    @Override
    void onEntityEnter() {

    }

    @Override
    protected void updateMovement(float tpf) {
        // refresh timer
        this.timer += tpf;

        boolean finished = false;

        float v = (speed * timer) / FastMath.PI;

        // limit to 90 degrees (half phi)
        if (v >= MAX_ANGLE) {
            v = MAX_ANGLE;
            finished = true;
        }


        float newXOffset = FastMath.cos(v) * -distanceX;
        float newZOffset = FastMath.sin(v) * distanceY;

        System.out.println();

        // compute new location
        float newX = centerCircle.x + newXOffset;
        float newZ = centerCircle.z + newZOffset;
        newTransform.set(newX, startY, newZ);

        // rotate entity in this direction its moving
        newRotation.fromAngles(0, v, 0);


        onUpdateTransform(new LocalTransformComponent(newTransform, newRotation));

        if (finished) {
            onMovementFinished();
        }
    }
}
