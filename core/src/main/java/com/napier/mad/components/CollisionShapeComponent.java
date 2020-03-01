package com.napier.mad.components;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.simsilica.es.EntityComponent;

public class CollisionShapeComponent implements EntityComponent {

    private BoxCollisionShape boxCollisionShape;

    public CollisionShapeComponent(BoxCollisionShape boxCollisionShape) {
        this.boxCollisionShape = boxCollisionShape;
    }

    public BoxCollisionShape getBoxCollisionShape() {
        return boxCollisionShape;
    }
}
