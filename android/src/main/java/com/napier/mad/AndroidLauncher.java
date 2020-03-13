package com.napier.mad;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;

import com.jme3.app.AndroidHarness;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.napier.mad.appstates.GameInputAppState;
import com.napier.mad.appstates.MainGameAppState;

public class AndroidLauncher extends AndroidHarness implements SensorEventListener, GameSensorManager.GameSensorListener {

    private GameSensorManager gameSensorManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // init game
        app=new Main(new AndroidGameEventHandler(this));
        super.onCreate(savedInstanceState);

        /*
        // setup sensor listener
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        sensorManager.registerListener(this, sensor, 10000);

         */

        // setup game sensor
        this.gameSensorManager = new GameSensorManager(this, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_MOVE) {
            float movedY = event.getY();
            if (movedY >= 30) {
                GameInputAppState inputAppState = getGameInputAppState();
                if (inputAppState != null) {
                    inputAppState.jump();
                }
                return true;
            }

        }
        return super.onTouchEvent(event);
    }

    private GameInputAppState getGameInputAppState() {
        return app.getStateManager().getState(GameInputAppState.class);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] q = new float[4];
        SensorManager.getQuaternionFromVector(q, sensorEvent.values);
        Quaternion quat = new Quaternion();
        quat.set(q[1], q[2], q[3], q[0]);
        float[] angles = new float[3];
        quat.toAngles(angles);


        float angle = angles[1] * FastMath.RAD_TO_DEG;

        if (app == null) {
            return;
        }
        GameInputAppState inputAppState = getGameInputAppState();
        if (inputAppState == null) {
            // game is not ready yet for taking input
            return;
        }


        /*

        float toleranceDegree = 7.0f;
        System.out.println(angle);

        if (angle > toleranceDegree) {
            // turn left
            inputAppState.moveRight();
        } else if (angle < -toleranceDegree) {
            // turn right
            inputAppState.moveLeft();
        } else {
            // stand still
            inputAppState.standStill();
        }

         */

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        MainGameAppState mainGameAppState = this.app.getStateManager().getState(MainGameAppState.class);
        if (mainGameAppState != null) {
            mainGameAppState.togglePauseGame();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        MainGameAppState mainGameAppState = this.app.getStateManager().getState(MainGameAppState.class);
        if (mainGameAppState != null) {
            mainGameAppState.togglePauseGame();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // we want to close the app properly when exiting the application
        app.stop(true);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        this.gameSensorManager.cleanup();
        super.onStop();
    }

    @Override
    public void onSensorChange(float value) {
        GameInputAppState gameInputAppState = this.app.getStateManager().getState(GameInputAppState.class);
        if (gameInputAppState == null) {
            return;
        }

      //  value = (direction == GameSensorManager.Direction.Left) ? value : -value;
        gameInputAppState.moveSidewards(value);
    }
}
