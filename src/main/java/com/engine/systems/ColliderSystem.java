package com.engine.systems;

import com.engine.ecs.ECS;

import com.engine.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import com.engine.components.*;

public class ColliderSystem implements ISystem {

    public void onCollision(ECS ecs, int entityA, int entityB) {

        Script scriptA = ecs.getComponent(entityA, Script.class);
        if (scriptA != null) scriptA.onCollision(entityB);

        Script scriptB = ecs.getComponent(entityB, Script.class);
        if (scriptB != null) scriptB.onCollision(entityA);
    }

    public void onTrigger(ECS ecs, int entityA, int entityB) {

        Script scriptA = ecs.getComponent(entityA, Script.class);
        if (scriptA != null) scriptA.onTrigger(entityB);

        Script scriptB = ecs.getComponent(entityB, Script.class);
        if (scriptB != null) scriptB.onTrigger(entityA);
    }

    @Override
    public void update(Scene scene) {
        ECS ecs = scene.getEcs();

        Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, Collider.class);
        for (int entityId : entities) {
            Transform t1 = ecs.getComponent(entityId, Transform.class);
            Collider r1 = ecs.getComponent(entityId, Collider.class);
            for (int entityId2 : entities) {
                if (entityId == entityId2) continue;
                Transform t2 = ecs.getComponent(entityId2, Transform.class);
                Collider r2 = ecs.getComponent(entityId2, Collider.class);

                if(r2==null || r1 ==null) continue;

                Rectangle2D shape1 = r1.getShape(t1);
                Rectangle2D shape2 = r2.getShape(t2);

                r1.isColliding = shape1.intersects(shape2);
                r2.isColliding = shape2.intersects(shape1);

                if(r1.isColliding){
                    if(r1.isTrigger)
                        onTrigger(ecs, entityId, entityId2);
                    else
                        onCollision(ecs, entityId, entityId2);
                    r1.collisionEntity = entityId2;
                    r2.collisionEntity = entityId;
                }
            }
        }
    }

    @Override
    public void render(Scene scene, Graphics2D g2) {
        // ECS ecs = scene.getEcs();

        // Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, Collider.class);
        // for (int entityId : entities) {
        //     Transform t = ecs.getComponent(entityId, Transform.class);
        //     Collider r = ecs.getComponent(entityId, Collider.class);
        //     if(r==null) continue;

        //     Color c = g2.getColor();
        //     if(!r.isColliding)
        //         g2.setColor(Color.green);
        //     else
        //         g2.setColor(Color.red);
        //     g2.draw(r.getShape(t));
        //     g2.setColor(c);

        // }

    }
}
