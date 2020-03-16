package com.napier.mad.android.input;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GameSensorManager implements SensorEventListener {

    private GameSensorListener listener;
    private SensorManager sensorManager;

    public GameSensorManager(Activity activity, GameSensorListener listener) {
        this.listener = listener;
        setupSensor(activity);
    }

    private void setupSensor(Activity activity) {
        this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            throw new IllegalStateException("Sensor Manager not available.");
        }
        Sensor gameSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (gameSensor == null) {
            throw new IllegalStateException("This device does not have a accelerometer!");
        }
        this.sensorManager.registerListener(this, gameSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float acceleration = event.values[0];

        // we want to ignore very small movements
        if (Math.abs(acceleration) <= 0.8) {
            acceleration = 0;
        }

        listener.onAccelerationChanged(acceleration);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // should never be called!
    }

    public interface GameSensorListener {

        /**
         * Called every time the acceleration has changed.
         * @param acceleration the current acceleration
         */
        void onAccelerationChanged(float acceleration);

    }

    public void cleanup() {
        this.sensorManager.unregisterListener(this);
    }
}
