package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.napier.mad.components.PlayerControlled;
import com.napier.mad.components.SideMovementComponent;
import com.simsilica.es.Entity;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntitySet;

public class GameInputAppState extends BaseAppState {

    private EntityData entityData;
    private EntitySet playerControlledEntities;

    private enum MoveType {
        Left,
        Right,
        Still;
    }

    private MoveType lastMove = MoveType.Still;

    @Override
    protected void initialize(Application app) {
        this.entityData = getState(EntityDataAppState.class).getEntityData();
        this.playerControlledEntities = entityData.getEntities(PlayerControlled.class);
    }

    @Override
    public void update(float tpf) {
        this.playerControlledEntities.applyChanges();
    }

    public void moveLeft() {
        if (!isInitialized()) {
            return;
        }
        if (lastMove == MoveType.Left) {
            return;
        }
        // move
        this.lastMove = MoveType.Left;
        refreshSideMovementComponent(lastMove);

    }

    public void standStill() {
        if (!isInitialized()) {
            return;
        }
        if (lastMove == MoveType.Still) {
            return;
        }
        this.lastMove = MoveType.Still;
        refreshSideMovementComponent(lastMove);
    }

    public void moveRight() {
        if (!isInitialized()) {
            return;
        }
        if (lastMove == MoveType.Right) {
            return;
        }
        this.lastMove = MoveType.Right;
        refreshSideMovementComponent(lastMove);
    }

    public void jump() {
        System.out.println("jump");
    }

    private void refreshSideMovementComponent(MoveType type) {
        int movementType = getMovementTypeFromMoveType(type);
        for (Entity e : playerControlledEntities) {
            this.entityData.setComponent(e.getId(), new SideMovementComponent(movementType));
        }
    }

    private int getMovementTypeFromMoveType(MoveType type) {
        if (type == MoveType.Left) {
            return SideMovementComponent.LEFT;
        }
        if (type == MoveType.Right) {
            return SideMovementComponent.RIGHT;
        }
        return SideMovementComponent.STAND;
    }


    @Override
    protected void cleanup(Application app) {
        this.playerControlledEntities.release();
        this.playerControlledEntities.clear();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
