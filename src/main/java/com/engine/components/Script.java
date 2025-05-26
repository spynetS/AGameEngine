package com.engine.components;

import com.engine.GameObject;
import com.engine.Scene;
import com.engine.Vector2;
import com.engine.ecs.ECS;
import com.engine.systems.ICollision;

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
