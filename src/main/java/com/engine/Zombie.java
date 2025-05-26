package com.engine;

import java.awt.Transparency;

import com.engine.components.Script;
import com.engine.components.Transform;

public class Zombie extends Script {

    GameObject target;

    public Zombie(GameObject target){
        this.target = target;
    }

    public void start(){
        Transform t2 = gameObject.getComponent(Transform.class);
        t2.scale = new Vector2(20,20);
    }


    @Override
    public void update(double deltaTime) {
        Transform t1 = target.getComponent(Transform.class);
        Transform t2 = gameObject.getComponent(Transform.class);

        Vector2 dir = t2.position.lookAt(t1.position);
        t2.position = t2.position.add(dir.multiply(deltaTime).multiply(50));
    }

}
