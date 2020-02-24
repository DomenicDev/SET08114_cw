package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.ChaseCamera;
import com.jme3.math.Vector3f;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.PlayerMovementComponent;
import com.napier.mad.components.PositionComponent;
import com.napier.mad.components.RigidBodyComponent;
import com.napier.mad.components.shapes.BoxCollisionShapeData;
import com.napier.mad.components.shapes.SphereCollisionShapeData;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class GameStarterAppState extends BaseAppState {

    private float timer = 0;

    private EntityData entityData;
    private EntityId entityToTransform;

    @Override
    protected void initialize(Application app) {
        AppStateManager stateManager = app.getStateManager();
        this.entityData = stateManager.getState(EntityDataAppState.class).getEntityData();

        // Create some simple objects for now...

        EntityId ball = entityData.createEntity();
        entityData.setComponents(ball,
                new PositionComponent(new Vector3f(0, 1f, 0)),
                new ModelComponent(ModelComponent.ModelType.Ball),
                new RigidBodyComponent(40, new SphereCollisionShapeData(0.5f)),
                new PlayerMovementComponent(new Vector3f(Vector3f.UNIT_X).multLocal(-1), 2));

        for (int i = 0; i < 5; i++) {
            createSimplePlatform(new Vector3f(i*-4, 0, 0));
        }

        for (int i = 1; i < 5; i++) {
            createSimplePlatform(new Vector3f(-16, 0, i*4));
        }

        stateManager.getState(CameraAppState.class).chase(ball);

        this.entityToTransform = ball;
    }

    private void createSimplePlatform(Vector3f position) {
        EntityId platform = entityData.createEntity();
        entityData.setComponents(platform,
                new PositionComponent(position),
                new ModelComponent(ModelComponent.ModelType.Platform),
                new RigidBodyComponent(0, new BoxCollisionShapeData(new Vector3f(2f, 0.2f, 2f))));
    }

    @Override
    public void update(float tpf) {
        timer += tpf;
      //  this.entityData.setComponent(entityToTransform, new PositionComponent((float) Math.sin(timer), (float) Math.cos(timer) * 0.7f));
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
