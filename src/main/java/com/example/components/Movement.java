package com.example.components;

import com.example.Debug;
import com.example.GameObject;
import com.example.Scene;
import com.example.Vector2;
import com.example.ecs.ECS;
import com.example.input.Input;
import com.example.input.Keys;

public class Movement extends Script implements AnimationListener{

    boolean mouse = false;
    public Movement(boolean mouse){
        this.mouse = mouse;
    }

    @Override
    public void onAnimationDone() {
        SpriteRenderer r = gameObject.getComponent(SpriteRenderer.class);
        if(r.getAnimationIndex() == 4){
            gameObject.destroy();
        }
    }
    public void update(double deltaTime){

        Transform t = gameObject.getComponent(Transform.class);
        Vector2 dir = Vector2.zero;
        Rigidbody rb = gameObject.getComponent(Rigidbody.class);
        SpriteRenderer r = gameObject.getComponent(SpriteRenderer.class);


        if(!mouse){
            if(Input.isKeyDown(Keys.W)){
                dir = dir.add(Vector2.up.multiply(1));
                r.setAnimationIndex(1);
            }
            if(Input.isKeyDown(Keys.S)){
                dir = dir.add(Vector2.down);
                r.setAnimationIndex(0);
            }

            if(Input.isKeyDown(Keys.A)){
                dir = dir.add(Vector2.left);
                r.setInverted(true);
                r.setAnimationIndex(2);

            }

            if(Input.isKeyDown(Keys.D)){
                dir = dir.add(Vector2.right);
                r.setInverted(false);
                r.setAnimationIndex(2);
            }


            if(Input.isKeyPressed(Keys.SPACE)){
                t.scale = t.scale.multiply(1.2);
                r.setAnimationIndex(4);
            }

            if(r.getAnimationIndex() != 4 &&  dir.equals(Vector2.zero)){
                r.setAnimationIndex(3);
            }

        }
        else{
            dir = t.position.lookAt(Input.getMousePosition());
        }
        dir = dir.getNormalized();
        rb.addForce(dir.multiply(50));
    }

}
