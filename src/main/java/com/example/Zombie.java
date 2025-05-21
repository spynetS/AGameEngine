package com.example;

import java.awt.Transparency;

import com.example.components.Script;
import com.example.components.Transform;

public class Zombie extends Script {

    GameObject target;

    public Zombie(GameObject target){
        this.target = target;
    }

    @Override
    public void update(double deltaTime) {
        Transform t1 = target.getComponent(Transform.class);
        Transform t2 = gameObject.getComponent(Transform.class);

        Vector2 dir = t2.position.lookAt(t1.position);
        t2.position = t2.position.add(dir.multiply(deltaTime).multiply(50));
    }

}
