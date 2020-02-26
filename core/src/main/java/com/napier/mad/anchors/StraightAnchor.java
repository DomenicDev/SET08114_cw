package com.napier.mad.anchors;

import com.jme3.math.Vector3f;
import com.napier.mad.components.LocalTransformComponent;

public class StraightAnchor extends AnchorLogic {

    private float distance;
    private float time;


    public StraightAnchor(float distance) {
        this.distance = distance;
    }

    @Override
    void onEntityEnter() {

    }

    @Override
    public void update(float tpf) {
        time += tpf;

        float movedDistance = time* speed;
        if (movedDistance >= distance) {
            movedDistance = distance;
            onMovementFinished();
        }
        onUpdateTransform(new LocalTransformComponent(new Vector3f(0, 0, movedDistance)));

    }

}
