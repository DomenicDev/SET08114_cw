package com.napier.mad.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.napier.mad.anchors.AnchorListener;
import com.napier.mad.components.AnchorComponent;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.FollowPathComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.components.NewMovementComponent;
import com.napier.mad.components.PathAutoGeneratedComponent;
import com.napier.mad.components.PathComponent;
import com.napier.mad.constants.Constants;
import com.napier.mad.types.AnchorMovementType;
import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

import java.util.ArrayList;
import java.util.List;

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

        /*
        // Test game world
        EntityId straight = entityData.createEntity();
        entityData.setComponents(straight,
                new LocalTransformComponent(new Vector3f(0, 0, 0), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent(),
                new NewMovementComponent(AnchorMovementType.Linear));

        EntityId straight2 = entityData.createEntity();
        entityData.setComponents(straight2,
                new LocalTransformComponent(new Vector3f(0, 0, 1 * TILE_LENGTH), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent(),
                new NewMovementComponent(AnchorMovementType.Linear));

         */
        /*
        EntityId straight3 = entityData.createEntity();
        entityData.setComponents(straight3,
                new LocalTransformComponent(new Vector3f(0, 0, 2* TILE_LENGTH), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent(),
                new NewMovementComponent(AnchorMovementType.Linear));


        EntityId straight4 = entityData.createEntity();
        entityData.setComponents(straight4,
                new LocalTransformComponent(new Vector3f(0, 0, 3 * TILE_LENGTH), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent(),
                new NewMovementComponent(AnchorMovementType.Linear));
*/
        /*
        EntityId curve = entityData.createEntity();
        entityData.setComponents(curve,
                new LocalTransformComponent(new Vector3f(0, 0, 2*TILE_LENGTH), Constants.UP),
                new ModelComponent(ModelType.Road_Corner),
                new AttachedToComponent());

        EntityId straight3 = entityData.createEntity();
        entityData.setComponents(straight3,
                new LocalTransformComponent(new Vector3f(8, 0, 2*TILE_LENGTH), Constants.LEFT),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent());


        float movementSpeed =2f;

        EntityId anchor = entityData.createEntity();
        entityData.setComponents(anchor,
                new AttachedToComponent(straight),
                new ModelComponent(ModelType.Empty),
                new LocalTransformComponent(new Vector3f(0, 0, -4))
                );

        EntityId anchor2 = entityData.createEntity();
        entityData.setComponents(anchor2,
                new AttachedToComponent(straight2),
                new ModelComponent(ModelType.Empty),
                new LocalTransformComponent(new Vector3f(0, 0, -4))
        );

        EntityId cornerAnchor = entityData.createEntity();
        entityData.setComponents(cornerAnchor,
                new ModelComponent(ModelType.Empty),
                new LocalTransformComponent(new Vector3f(-4, 0, 4)),
                new AttachedToComponent(curve)
        );





        // player
        EntityId player = entityData.createEntity();
        entityData.setComponents(player,
                new ModelComponent(ModelType.Player),
                new LocalTransformComponent(new Vector3f(0f, 2, 0)),
                new AttachedToComponent(anchor)
        );

        entityData.setComponent(anchor, new AnchorComponent(AnchorMovementType.Linear, movementSpeed, anchor2, true));

        getState(AnchorMovementAppState.class).addAnchorListener(new AnchorListener() {

            boolean done = false;
            @Override
            public void onFinish(EntityId anchorId) {

                AnchorComponent comp = entityData.getComponent(anchorId, AnchorComponent.class);
                EntityId next = comp.getMovingEntity();
                entityData.setComponent(next, new AnchorComponent(AnchorMovementType.Linear, movementSpeed, cornerAnchor, true));
                entityData.setComponent(player, new AttachedToComponent(next));

                /*
                if (anchorId.equals(anchor)) {
                    System.out.println("hier");
              //      entityData.setComponent(anchor, new AnchorComponent(AnchorMovementType.Linear, movementSpeed, anchor2));
                    entityData.setComponent(anchor2, new AnchorComponent(AnchorMovementType.Linear, movementSpeed, cornerAnchor, true));
                }
                else if (anchorId.equals(anchor2)) {

                }



                if (done) return;
                done = true;
                        entityData.setComponent(anchor2,
                        new AnchorComponent(AnchorMovementType.Linear, movementSpeed));

                entityData.setComponent(player, new AttachedToComponent(anchor2));


            }

        });
        */

   //
        //     this.player = player;

        EntityId straight = entityData.createEntity();
        entityData.setComponents(straight,
                new LocalTransformComponent(new Vector3f(0, 0, 0), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent(),
                new MovableComponent(AnchorMovementType.Linear));

        EntityId straight2 = entityData.createEntity();
        entityData.setComponents(straight2,
                new LocalTransformComponent(new Vector3f(0, 0, 1 * TILE_LENGTH), Constants.UP),
                new ModelComponent(ModelType.Road_Straight),
                new AttachedToComponent(),
                new MovableComponent(AnchorMovementType.Linear));

        // create path
        List<EntityId> path = new ArrayList<>();
        path.add(straight);
        path.add(straight2);

        // create path entity
        EntityId pathId = entityData.createEntity();
        entityData.setComponents(pathId,
                new PathComponent(path),
                new PathAutoGeneratedComponent());
        System.out.println("path " + pathId);

        // create player and let player follow that path
        EntityId player = entityData.createEntity();
        System.out.println("player " + player);
        entityData.setComponents(player,
                new ModelComponent(ModelType.Player),
                new LocalTransformComponent(new Vector3f(0f, 2, 0)),
                new FollowPathComponent(pathId),
                new AttachedToComponent()
        );

        this.player = player;

    }

    private EntityId player;
    private boolean cameraSetup;

    @Override
    public void update(float tpf) {
        if (!cameraSetup) {
            CameraAppState cameraAppState = getState(CameraAppState.class);
            ModelLoaderAppState modelLoaderAppState = getState(ModelLoaderAppState.class);
            Node modelNode = modelLoaderAppState.getModelNode(player);
            if (modelNode != null) {
                cameraAppState.chase(modelNode);
                cameraSetup = true;
            }
        }
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
