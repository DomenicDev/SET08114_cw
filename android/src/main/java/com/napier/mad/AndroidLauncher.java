package com.napier.mad;

import android.os.Bundle;
import com.jme3.app.AndroidHarness;
import com.napier.mad.Main;

public class AndroidLauncher extends AndroidHarness {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        app=new Main();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
