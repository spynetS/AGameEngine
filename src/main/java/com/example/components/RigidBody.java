package com.example.components;

import com.example.Vector2;

/**
 * RigidBody
 */
public class RigidBody extends Component{

    public boolean useGravity = false;
    public double mass = 10;
    public double friction = 0.1;

    public Vector2 velocity = new Vector2();

    public RigidBody(){

    }
    public RigidBody(boolean useGravity){
        this.useGravity = useGravity;
    }

    public void addForce(Vector2 force){
        velocity = velocity.add(force);

    }

}
