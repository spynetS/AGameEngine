package com.example.components;

import com.example.GameObject;
import com.example.Scene;
import com.example.Vector2;
import com.example.ecs.ECS;
import com.example.systems.ICollision;

public class Script extends Component implements ICollision {

    protected GameObject gameObject;

    public Script(GameObject ob){
        gameObject = ob;
    }

    public Script(){}

    public void initScript(GameObject gameObject){
        this.gameObject = gameObject;
    }

    public void start(){

    }

    public void update(double deltaTime){

    }

    @Override
    public void onCollision(int entity) {  }

}
