package com.napier.mad.factory;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.napier.mad.components.AttachedToComponent;
import com.napier.mad.components.LocalTransformComponent;
import com.napier.mad.components.ModelComponent;
import com.napier.mad.components.MovableComponent;
import com.napier.mad.types.AnchorMovementType;
import com.napier.mad.types.ModelType;
import com.simsilica.es.EntityData;
import com.simsilica.es.EntityId;

public class EntityFactory {


    public static EntityId createStraightRoad(EntityData entityData, Vector3f location, Quaternion rotation) {
        EntityId road = entityData.createEntity();
        entityData.setComponents(road,
                new ModelComponent(ModelType.Road_Straight),
                new LocalTransformComponent(location, rotation),
                new AttachedToComponent(),
                new MovableComponent(AnchorMovementType.Linear));
        return road;
    }


}
