package com.napier.mad.components;

public class AccelerationComponent {

    private final float xAcc;
    private final float yAcc;

    public AccelerationComponent(float xAcc, float yAcc) {
        this.xAcc = xAcc;
        this.yAcc = yAcc;
    }

    public float getxAcc() {
        return xAcc;
    }

    public float getyAcc() {
        return yAcc;
    }
}
