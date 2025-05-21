package com.example.components;

import com.example.GameObject;
import com.example.Scene;
import com.example.Vector2;
import com.example.ecs.ECS;

public class Movement extends Script{

    public void update(double deltaTime){
        Transform t = gameObject.getComponent(Transform.class);
        t.position = t.position.add(new Vector2(10*deltaTime,0));
    }
}
