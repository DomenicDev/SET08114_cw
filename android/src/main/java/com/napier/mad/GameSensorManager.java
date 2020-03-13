package com.napier.mad;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;

public class GameSensorManager implements SensorEventListener {

    private float lastAngleY = 0;
    private GameSensorListener listener;

    private SensorManager sensorManager;

    public enum Direction {
        Left,
        Right
    }

    public GameSensorManager(Activity activity, GameSensorListener listener) {
        this.listener = listener;
        setupSensor(activity);
    }

    private void setupSensor(Activity activity) {
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            throw new IllegalStateException("Sensor Manager not available.");
        }
        Sensor gameSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.sensorManager.registerListener(this, gameSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float rotX = event.values[1];
        float rotZ = -event.values[2];

        float result = -1 * (rotX + rotZ);

        listener.onSensorChange(result);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // should never be called!
    }

    public interface GameSensorListener {

        void onSensorChange(float value);

    }

    public void cleanup() {
        this.sensorManager.unregisterListener(this);
    }
}
