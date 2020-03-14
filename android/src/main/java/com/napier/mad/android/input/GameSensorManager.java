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
        Sensor gameSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.sensorManager.registerListener(this, gameSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float rotX = event.values[1];
        float rotZ = -event.values[2];

        float result = -1 * (rotX + rotZ);

        listener.onAccelerationChanged(result);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // should never be called!
    }

    public interface GameSensorListener {

        void onAccelerationChanged(float acceleration);

    }

    public void cleanup() {
        this.sensorManager.unregisterListener(this);
    }
}
