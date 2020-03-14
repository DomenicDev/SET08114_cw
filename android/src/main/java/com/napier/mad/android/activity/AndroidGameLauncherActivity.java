package com.napier.mad.android.activity;

import android.os.Bundle;

import com.jme3.app.AndroidHarness;
import com.napier.mad.android.input.AndroidGameEventHandler;
import com.napier.mad.Main;
import com.napier.mad.appstates.MainGameAppState;

public class AndroidGameLauncherActivity extends AndroidHarness {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // init game
        Main mainApp = new Main();

        // we set our own listener to "adopt" android as input source
        mainApp.setGameEventListener(new AndroidGameEventHandler(mainApp, this));

        // now let the AndroidHarness set up the OpenGL context for us
        app = mainApp;
        super.onCreate(savedInstanceState);
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
        app.stop(true);
        super.onStop();
    }

}
