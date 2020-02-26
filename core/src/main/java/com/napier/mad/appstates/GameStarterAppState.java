package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;
import com.napier.mad.anchors.AnchorListener;
import com.napier.mad.components.AnchorComponent;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.constants.Constants;
import com.napier.mad.types.AnchorMovementType;
import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class GameStarterAppState extends BaseAppState {



    @Override
    protected void initialize(Application app) {
        EntityData entityData = getState(EntityDataAppState.class).getEntityData();
        // init some entities
        float TILE_LENGTH = Constants.TILE_LENGTH;
        /*
        for (int i = 0; i < 10; i++) {
            EntityId straight = entityData.createEntity();
            entityData.setComponents(straight,
                    new LocalTransformComponent(new Vector3f(0, 0, i*TILE_LENGTH), Constants.UP),
                    new ModelComponent(ModelType.Road_Straight),
                    new AttachedToComponent());
        }

         */

        // Test game world
        EntityId straight = entityData.createEntity();
        entityData.setComponents(straight,
                new LocalTransformComponent(new Vector3f(0, 0, 0), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent());

        EntityId straight2 = entityData.createEntity();
        entityData.setComponents(straight2,
                new LocalTransformComponent(new Vector3f(0, 0, 1 * TILE_LENGTH), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent());



        float movementSpeed = 2f;

        EntityId anchor = entityData.createEntity();
        entityData.setComponents(anchor,
                new AttachedToComponent(straight),
                new ModelComponent(ModelType.Player),
                new LocalTransformComponent(),
                new AnchorComponent(AnchorMovementType.Linear, movementSpeed));

        // player
        EntityId player = entityData.createEntity();
        entityData.setComponents(player,
                new ModelComponent(ModelType.Player),
                new LocalTransformComponent(new Vector3f(0.3f, 2, 0)),
                new AttachedToComponent(anchor)
        );

        getState(AnchorMovementAppState.class).addAnchorListener(new AnchorListener() {
            @Override
            public void onFinish() {
            }
        });

    }


    @Override
    public void update(float tpf) {

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
