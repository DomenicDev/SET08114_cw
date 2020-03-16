package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;

public class BackgroundMusicAppState extends BaseAppState {

    private AudioNode music;

    @Override
    protected void initialize(Application app) {
        this.music = new AudioNode(app.getAssetManager(), "Sounds/background_music.wav", AudioData.DataType.Stream);
        this.music.setLooping(true);
        this.music.setPositional(false);
        this.music.setVolume(0.4f);
        this.music.play();
    }

    @Override
    protected void cleanup(Application app) {
        this.music.stop();
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }
}
