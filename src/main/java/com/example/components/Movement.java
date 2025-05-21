package com.example.components;

import com.example.GameObject;
import com.example.Scene;
import com.example.Vector2;
import com.example.ecs.ECS;
import com.example.input.Input;
import com.example.input.Keys;

public class Movement extends Script{

    public void update(double deltaTime){
//        Transform t = gameObject.getComponent(Transform.class);
        Vector2 dir = Vector2.zero;
        RigidBody rb = gameObject.getComponent(RigidBody.class);

        if(Input.isKeyDown(Keys.W))
            dir = dir.add(Vector2.up);

        if(Input.isKeyDown(Keys.S))
            dir = dir.add(Vector2.down);

        if(Input.isKeyDown(Keys.A))
            dir = dir.add(Vector2.left);
        if(Input.isKeyDown(Keys.D))
            dir = dir.add(Vector2.right);

        if(Input.isKeyDown(Keys.SPACE))
            gameObject.getComponent(Collider.class).w++;

        rb.addForce(dir.multiply(30));



    }
}
