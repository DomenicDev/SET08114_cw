package com.napier.mad.factory;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.CollisionShapeComponent;
import com.napier.mad.components.DeleteAttachedEntitiesOnRemoveComponent;
import com.napier.mad.components.DirectionComponent;
import com.napier.mad.components.ItemComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.types.AnchorMovementType;
import com.napier.mad.types.Direction;
import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class EntityFactory {


    public static EntityId createStraightRoad(EntityData entityData, Vector3f location, Quaternion rotation, Direction direction) {
        EntityId road = entityData.createEntity();
        entityData.setComponents(road,
                new ModelComponent(ModelType.Road_Straight),
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


}
