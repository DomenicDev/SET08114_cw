package com.napier.mad.anchors;

import com.jme3.math.Vector3f;
import com.napier.mad.components.LocalTransformComponent;

public class StraightAnchor extends AnchorLogic {

    private float distance;
    private float time;
    private Vector3f startLocation;


    public StraightAnchor(float distance, Vector3f startLocation) {
        this.distance = distance;
        this.startLocation = startLocation;
    }

    @Override
    void onEntityEnter() {

    }

    @Override
    public void updateMovement(float tpf) {
        time += tpf;

        float movedDistance = time* speed;

        if (movedDistance >= distance) {
            //    movedDistance = distance;
            onMovementFinished();
        }

        onUpdateTransform(new LocalTransformComponent(startLocation.add(startLocation.getX(), startLocation.getY(), movedDistance)));

    }

}
