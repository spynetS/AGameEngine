package com.example.systems;

import java.awt.Graphics2D;
import java.util.Set;

import com.example.Scene;
import com.example.Time;
import com.example.Vector2;
import com.example.components.*;
import com.example.ecs.ECS;

/**
 * RigidbodySystem
 */
public class RigidbodySystem implements ISystem{

    public void resolveCollision(Transform tA, Rigidbody rbA, Collider cA,
                                 Transform tB, Rigidbody rbB, Collider cB) {
        // Calculate overlap on X and Y axes (for AABBs)
        double overlapX = Math.min(tA.position.x + cA.offset.x + cA.size.x, tB.position.x + cB.offset.x + cB.size.x) -
            Math.max(tA.position.x + cA.offset.x, tB.position.x + cB.offset.x);
        double overlapY = Math.min(tA.position.y + cA.offset.y + cA.size.y, tB.position.y + cB.offset.y + cB.size.y) -
            Math.max(tA.position.y + cA.offset.y, tB.position.y + cB.offset.y);

        // Resolve the smallest overlap axis to separate the objects
        if (overlapX < overlapY) {
            // Push entities apart along X axis
            double correction = overlapX
                ;
            tA.position = tA.position.add(new Vector2(-correction, 0));
            tB.position = tB.position.add(new Vector2(correction, 0));

            // Reverse X velocity for a simple bounce effect
            rbA.velocity.x = -rbA.velocity.x;
            rbB.velocity.x = -rbB.velocity.x;
        } else {
            // Push entities apart along Y axis
            double correction = overlapY;
            tA.position = tA.position.add(new Vector2(0, -correction));
            tB.position = tB.position.add(new Vector2(0, correction));

            // Reverse Y velocity for bounce
            rbA.velocity.y = rbA.velocity.y;
            rbB.velocity.y = -rbB.velocity.y;
        }
    }

      @Override
    public void update(Scene scene) {
        ECS ecs = scene.getEcs();

        Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, Rigidbody.class);
        for (int entityId : entities) {
            Rigidbody rb = ecs.getComponent(entityId, Rigidbody.class);
            Transform t = ecs.getComponent(entityId, Transform.class);

            if(rb.useGravity){
                rb.acceleration = rb.acceleration.add(new Vector2(0,9.82));
            }

            Collider c = ecs.getComponent(entityId, Collider.class);
            if(c != null && c.isColliding){
                Transform t2 = ecs.getComponent(c.collisionEntity, Transform.class);
                Rigidbody rb2 = ecs.getComponent(c.collisionEntity, Rigidbody.class);
                this.resolveCollision(t,
                                      rb,
                                      c,
                                      t2,
                                      rb2,
                                      ecs.getComponent(c.collisionEntity, Collider.class));
            }

            //rb.velocity = rb.velocity.multiply(1-rb.friction);
            rb.velocity = rb.velocity.add(rb.acceleration.multiply(Time.deltaTime));


            t.position = t.position.add(rb.velocity.multiply(Time.deltaTime).multiply(100));

            rb.acceleration = Vector2.zero;
        }

    }

    @Override
    public void render(Scene scene, Graphics2D g2) {


    }
}
