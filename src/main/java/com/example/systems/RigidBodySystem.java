package com.example.systems;

import java.awt.Graphics2D;
import java.util.Set;

import com.example.Scene;
import com.example.Time;
import com.example.Vector2;
import com.example.components.*;
import com.example.ecs.ECS;

/**
 * RigidBodySystem
 */
public class RigidBodySystem implements ISystem{

    @Override
    public void update(Scene scene) {
        ECS ecs = scene.getEcs();

        Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, Collider.class, RigidBody.class);
        for (int entityId : entities) {
            RigidBody rb = ecs.getComponent(entityId, RigidBody.class);
            Collider c = ecs.getComponent(entityId, Collider.class);

            if(rb.useGravity){
                rb.addForce(Vector2.down.multiply(9.82));
            }

            if(!c.isColliding){

                Transform t = ecs.getComponent(entityId, Transform.class);
                t.position = t.position.add(rb.velocity.multiply(Time.deltaTime));
            }
            else
                rb.velocity = Vector2.zero;
        }

    }

    @Override
    public void render(Scene scene, Graphics2D g2) {
        // TODO Auto-generated method stub

    }
}
