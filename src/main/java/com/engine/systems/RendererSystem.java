package com.engine.systems;

import com.engine.ecs.ECS;

import com.engine.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import com.engine.components.*;

public class RendererSystem implements ISystem {
    @Override
    public void update(Scene scene) {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(Scene scene, Graphics2D g2) {
        ECS ecs = scene.getEcs();

        Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, ShapeRenderer.class);


        for (int entityId : entities) {
           Transform t = ecs.getComponent(entityId, Transform.class);
           ShapeRenderer r = ecs.getComponent(entityId, ShapeRenderer.class);

           double w = t.scale.getX();
           double h = t.scale.getY();

            g2.fill(new Rectangle2D.Double(
                                           t.position.getX() - (w / 2),
                                           t.position.getY() - (h / 2),
                                           w+1, h+1
                                           ));

        }

    }
}
