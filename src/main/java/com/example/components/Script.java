package com.example.components;

import com.example.GameObject;
import com.example.Scene;
import com.example.Vector2;
import com.example.ecs.ECS;

public class Script extends Component{

    protected GameObject gameObject;

    public Script(GameObject ob){
        gameObject = ob;
    }

    public Script(){}

    public void initScript(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public void update(double deltaTime){
        Transform t = gameObject.getComponent(Transform.class);
        t.position = t.position.add(new Vector2(10*deltaTime,0));

    }
}
