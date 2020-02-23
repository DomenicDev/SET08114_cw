package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.napier.mad.components.ModelComponent;
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
                new PositionComponent(new Vector3f(0, 2, 0)),
                new ModelComponent(ModelComponent.ModelType.Ball),
                new RigidBodyComponent(40, new SphereCollisionShapeData(0.5f)));

        EntityId platform = entityData.createEntity();
        entityData.setComponents(platform,
                new PositionComponent(new Vector3f(0, 0.3f, 0)),
                new ModelComponent(ModelComponent.ModelType.Platform),
                new RigidBodyComponent(0, new BoxCollisionShapeData(new Vector3f(1f, 0.2f, 1f))));


        System.out.println(ball + " " + platform);

        this.entityToTransform = ball;
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
