package com.napier.mad.factory;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.components.AccelerateComponent;
import com.napier.mad.components.AliveStateComponent;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.CollectorComponent;
import com.napier.mad.components.CollisionShapeComponent;
import com.napier.mad.components.DeleteAttachedEntitiesOnRemoveComponent;
import com.napier.mad.components.DestroyPassedEntityOnFinishedMovementComponent;
import com.napier.mad.components.FollowPathComponent;
import com.napier.mad.components.JumpComponent;
import com.napier.mad.components.MovementSpeedComponent;
import com.napier.mad.components.ObstacleComponent;
import com.napier.mad.components.DirectionComponent;
import com.napier.mad.components.ItemComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.components.PlayerControlled;
import com.napier.mad.components.ScoreComponent;
import com.napier.mad.constants.GameConstants;
import com.napier.mad.types.AnchorMovementType;
import com.napier.mad.types.Direction;
import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class EntityFactory {

    public static EntityId createPlayer(EntityData entityData, EntityId pathId) {
        EntityId player = entityData.createEntity();
        entityData.setComponents(player,
                new ModelComponent(ModelType.Human),
                new LocalTransformComponent(new Vector3f(0f, 0, 0)),
                new FollowPathComponent(pathId),
                new AttachedToComponent(),
                new PlayerControlled(),
                new AliveStateComponent(true),
                new CollectorComponent(),
                new JumpComponent(false),
                new ScoreComponent(),
                new MovementSpeedComponent(GameConstants.DEFAULT_SPEED),
                new AccelerateComponent(GameConstants.DEFAULT_PLAYER_ACCELERATION),
                new DestroyPassedEntityOnFinishedMovementComponent(2f),
                new CollisionShapeComponent(new Vector3f(0.35f, 0.8f, 0.35f), 0.8f)
        );
        return player;
    }

    public static EntityId createStaticCar(EntityData entityData, EntityId parentId, Vector3f localTranslation) {
        EntityId movingCar = entityData.createEntity();
        entityData.setComponents(movingCar,
                new ModelComponent(ModelType.Car),
                new LocalTransformComponent(localTranslation),
                new AttachedToComponent(parentId),
                new ObstacleComponent(),
                new CollisionShapeComponent(new Vector3f(0.6f, 0.3f, 0.6f), 0.5f)
        );
        return movingCar;
    }

    public static EntityId createStraightRoad(EntityData entityData, Vector3f location, Quaternion rotation, Direction direction) {
        EntityId road = entityData.createEntity();
        entityData.setComponents(road,
                new ModelComponent(ModelType.Road_Straight_House_2),
                new LocalTransformComponent(location, rotation),
                new AttachedToComponent(),
                new MovableComponent(AnchorMovementType.Linear),
                new DirectionComponent(direction),
                new DeleteAttachedEntitiesOnRemoveComponent());
        return road;
    }

    public static EntityId createCornerToLeft(EntityData entityData, Vector3f location, Quaternion quaternion, Direction direction) {
        EntityId cornerLeft = entityData.createEntity();
        entityData.setComponents(cornerLeft,
                new ModelComponent(ModelType.Road_Corner_Left),
                new LocalTransformComponent(location, quaternion),
                new AttachedToComponent(),
                new MovableComponent(AnchorMovementType.CornerToLeft),
                new DirectionComponent(direction),
                new DeleteAttachedEntitiesOnRemoveComponent());
        return cornerLeft;
    }

    public static EntityId createCornerToRight(EntityData entityData, Vector3f location, Quaternion rotation, Direction direction) {
        EntityId cornerLeft = entityData.createEntity();
        entityData.setComponents(cornerLeft,
                new ModelComponent(ModelType.Road_Corner_Right),
                new LocalTransformComponent(location, rotation),
                new AttachedToComponent(),
                new MovableComponent(AnchorMovementType.CornerToRight),
                new DirectionComponent(direction),
                new DeleteAttachedEntitiesOnRemoveComponent());
        return cornerLeft;
    }

    public static EntityId createCoin(EntityData entityData, EntityId parentEntityId, Vector3f localTranslation) {
        EntityId coin = entityData.createEntity();
        entityData.setComponents(coin,
                new ModelComponent(ModelType.Coin),
                new LocalTransformComponent(localTranslation),
                new AttachedToComponent(parentEntityId),
                new ItemComponent(5),
                new CollisionShapeComponent(new Vector3f(0.5f, 0.5f, 0.5f)));
        return coin;
    }

    public static EntityId createObstacle(EntityData entityData, EntityId parentEntityId, Vector3f localTranslation) {
        EntityId obstacle = entityData.createEntity();
        entityData.setComponents(obstacle,
                new ModelComponent(ModelType.Empty),
                new LocalTransformComponent(localTranslation),
                new AttachedToComponent(parentEntityId),
                new CollisionShapeComponent(new Vector3f(0.8f, 0.3f, 0.8f)),
                new ObstacleComponent());
        return obstacle;
    }

}
