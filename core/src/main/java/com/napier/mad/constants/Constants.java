package com.napier.mad.constants;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;

public class Constants {

    public static final Quaternion UP = new Quaternion(new float[] {0, FastMath.DEG_TO_RAD * 0, 0});
    public static final Quaternion DOWN = new Quaternion(new float[] {0, FastMath.DEG_TO_RAD * 180, 0});
    public static final Quaternion LEFT = new Quaternion(new float[] {0, FastMath.DEG_TO_RAD * 90, 0});
    public static final Quaternion RIGHT = new Quaternion(new float[] {0, FastMath.DEG_TO_RAD * -90, 0});

    public static final float TILE_LENGTH = 8f;

}
