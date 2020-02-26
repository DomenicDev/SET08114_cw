package com.napier.mad.physics;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.objects.PhysicsRigidBody;

public class DodgeDemonCharacterControl extends BetterCharacterControl {

    public DodgeDemonCharacterControl(float radius, float height, float mass) {
        super(radius, height, mass);
    }

    public PhysicsRigidBody getPhysicsRigidBody() {
        return this.rigidBody;
    }

}
