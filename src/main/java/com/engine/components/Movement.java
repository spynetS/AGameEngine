package com.engine.components;

import java.awt.Rectangle;

import com.engine.Debug;
import com.engine.GameObject;
import com.engine.Scene;
import com.engine.components.*;
import com.engine.Vector2;
import com.engine.ecs.ECS;
import com.engine.input.Input;
import com.engine.input.Keys;

public class Movement extends Script implements AnimationListener{

    boolean mouse = false;
    public Movement(boolean mouse){
        this.mouse = mouse;
    }

    @Override
    public void onAnimationDone() {
        SpriteRenderer r = gameObject.getComponent(SpriteRenderer.class);
        if(r.getAnimationIndex() == 4){
            // add the dead one
            GameObject dead = gameObject.scene.createGameObject();
            SpriteRenderer newR = new SpriteRenderer();
            int w = 384/6;
            int h = 48;
            newR.addSprite(new Sprite("src/main/resources/death.png", new Rectangle(5*w,0,w,h)));
            dead.addComponent(newR);
            dead.getComponent(Transform.class).position = gameObject.getComponent(Transform.class).position;
            dead.getComponent(Transform.class).scale = gameObject.getComponent(Transform.class).scale;
            gameObject.destroy();
        }
    }
    public void update(double deltaTime){

        Transform t = gameObject.getComponent(Transform.class);
        Vector2 dir = Vector2.zero;
        Rigidbody rb = gameObject.getComponent(Rigidbody.class);
        SpriteRenderer r = gameObject.getComponent(SpriteRenderer.class);
            if(Input.isKeyPressed(Keys.SPACE)){
                t.scale = t.scale.multiply(1.2);
                r.setAnimationIndex(4);
            }

        if(!mouse){
            if(r.getAnimationIndex() != 4){
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

                if(dir.equals(Vector2.zero)){
                    r.setAnimationIndex(3);
                }
            }

        }
        else{
            dir = t.position.lookAt(Input.getMousePosition());
        }
        dir = dir.getNormalized();
        rb.addForce(dir.multiply(50));
    }

}
