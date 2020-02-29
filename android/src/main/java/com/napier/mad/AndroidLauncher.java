package com.napier.mad;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import com.jme3.app.AndroidHarness;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.napier.mad.Main;
import com.napier.mad.appstates.GameInputAppState;

public class AndroidLauncher extends AndroidHarness implements SensorEventListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // init game
        app=new Main();
        super.onCreate(savedInstanceState);

        // setup sensor listener
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, 10000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] q = new float[4];
        SensorManager.getQuaternionFromVector(q, sensorEvent.values);
        Quaternion quat = new Quaternion();
        quat.set(q[0], q[1], q[2], q[3]);
        float[] angles = new float[3];
        quat.toAngles(angles);


        float angle = angles[1] * FastMath.RAD_TO_DEG;

        if (app == null) {
            return;
        }
        GameInputAppState inputAppState = app.getStateManager().getState(GameInputAppState.class);
        if (inputAppState == null) {
            // game is not ready yet for taking input
            return;
        }

        float toleranceDegree = 7.0f;

        if (angle > toleranceDegree) {
            // turn left
            inputAppState.moveLeft();
        } else if (angle < -toleranceDegree) {
            // turn right
            inputAppState.moveRight();
        } else {
            // stand still
            inputAppState.standStill();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
