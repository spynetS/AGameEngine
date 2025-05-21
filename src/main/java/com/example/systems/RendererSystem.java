package com.example.systems;

import com.example.ecs.ECS;

import com.example.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Set;

import com.example.components.*;

public class RendererSystem implements ISystem {
    @Override
    public void update(Scene scene) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(Scene scene, Graphics2D g2) {
        ECS ecs = scene.getEcs();

        Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, Renderer.class);
        for (int entityId : entities) {
            Transform t = ecs.getComponent(entityId, Transform.class);
            Renderer r = ecs.getComponent(entityId, Renderer.class);

            double w = t.scale.getX();
            double h = t.scale.getY();

            Color c = g2.getColor();
            g2.setColor(r.color);
            g2.fill(new Rectangle2D.Double(t.position.getX()-(w/2),t.position.getY()-(h/2),w,h));
            g2.setColor(c);

        }

    }
}
