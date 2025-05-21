package com.example.components;

import com.example.Vector2;

/**
 * Rigidbody
 */
public class Rigidbody extends Component{

    public boolean useGravity = false;
    public double mass = 1;
    public double friction = 0.01;

    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();

    public Rigidbody(){

    }
    public Rigidbody(boolean useGravity){
        this.useGravity = useGravity;
    }

    public void addForce(Vector2 force){
        acceleration = acceleration.add(force.divide(mass));

    }


}
