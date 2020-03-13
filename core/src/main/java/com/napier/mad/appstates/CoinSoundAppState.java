package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.napier.mad.components.ItemCollectedEventComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.types.ModelType;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;
import com.simsilica.es.filter.FieldFilter;

public class CoinSoundAppState extends BaseAppState {

    private EntitySet collectionEvents;
    private EntitySet coins;

    private AudioNode coinSound;

    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        this.collectionEvents = entityData.getEntities(ItemCollectedEventComponent.class);
        this.coins = entityData.getEntities(new FieldFilter<>(ModelComponent.class, "type", ModelType.Coin), ModelComponent.class);

        this.coinSound = new AudioNode(app.getAssetManager(), "Sounds/coin.wav", AudioData.DataType.Buffer);
        this.coinSound.setVolume(0.4f);
        this.coinSound.setPositional(false);
        this.coinSound.setLooping(false);
    }

    @Override
    public void update(float tpf) {
        this.coins.applyChanges();

        if (this.collectionEvents.applyChanges()) {

            for (Entity e : collectionEvents.getAddedEntities()) {
                addSound(e);
            }

        }
    }

    private void addSound(Entity e) {
        ItemCollectedEventComponent event = e.get(ItemCollectedEventComponent.class);
        if (coins.containsId(event.getItemId())) {
            // play sound
            coinSound.playInstance();
        }
    }

    @Override
    protected void cleanup(Application app) {
        this.collectionEvents.release();
        this.coins.release();
        this.collectionEvents.clear();
        this.coins.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
