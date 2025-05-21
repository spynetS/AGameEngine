package com.example.components;

import com.example.GameObject;
import com.example.Scene;
import com.example.Vector2;
import com.example.ecs.ECS;
import com.example.input.Input;
import com.example.input.Keys;

public class Movement extends Script{

    public void update(double deltaTime){
        Transform t = gameObject.getComponent(Transform.class);
        Vector2 dir = Vector2.zero;

        if(Input.isKeyDown(Keys.W))
            dir = dir.add(Vector2.up);

        if(Input.isKeyDown(Keys.S))
            dir = dir.add(Vector2.down);

        if(Input.isKeyDown(Keys.A))
            dir = dir.add(Vector2.left);
        if(Input.isKeyDown(Keys.D))
            dir = dir.add(Vector2.right);

        if(Input.isKeyPressed(Keys.SPACE))
            t.scale = t.scale.multiply(2);


        t.position = t.position.add(new Vector2(dir.getNormalized().multiply(deltaTime).multiply(200)));

    }
}
