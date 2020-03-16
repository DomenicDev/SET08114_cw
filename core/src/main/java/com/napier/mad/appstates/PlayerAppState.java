package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.ScoreComponent;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;
import com.simsilica.es.WatchedEntity;

public class PlayerAppState extends BaseAppState {

    private EntityData entityData;
    private EntityId playerId;
    private WatchedEntity playerEntity;
    private MainGameAppState mainGameAppState;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.mainGameAppState = getState(MainGameAppState.class);
    }

    void setPlayerId(EntityId playerId) {
        this.playerId = playerId;
        if (playerId != null) {
            this.playerEntity = entityData.watchEntity(playerId, ScoreComponent.class);
        }
    }

    @Override
    public void update(float tpf) {
        if (playerId == null) {
            return;
        }

        if (playerEntity.applyChanges()) {

            ScoreComponent scoreComponent = playerEntity.get(ScoreComponent.class);
            int newScore = scoreComponent.getScore();
            this.mainGameAppState.setNewScore(newScore);

        }
    }

    @Override
    protected void cleanup(Application app) {
        if (playerEntity != null) {
            playerEntity.release();
        }
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
