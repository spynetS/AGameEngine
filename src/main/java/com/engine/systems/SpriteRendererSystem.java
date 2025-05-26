package com.engine.systems;

import com.engine.ecs.ECS;

import com.engine.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import com.engine.components.*;

public class SpriteRendererSystem implements ISystem {
    @Override
    public void update(Scene scene) {
        ECS ecs = scene.getEcs();

        Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, SpriteRenderer.class);

        // Turn into a list to sort
        ArrayList<Integer> sortedEntities = new ArrayList<>(entities);

        // Sort by renderOrder (lower first)
        sortedEntities.sort(Comparator.comparingInt(eid -> ecs.getComponent(eid, SpriteRenderer.class).renderOrder));

        for (int entityId : sortedEntities) {
            Transform t = ecs.getComponent(entityId, Transform.class);
            SpriteRenderer r = ecs.getComponent(entityId, SpriteRenderer.class);
            r.update(scene);
        }


    }

    @Override
    public void render(Scene scene, Graphics2D g2) {
        ECS ecs = scene.getEcs();

        Set<Integer> entities = ecs.getEntitiesWithComponents(Transform.class, SpriteRenderer.class);

        // Turn into a list to sort
        ArrayList<Integer> sortedEntities = new ArrayList<>(entities);

        // Sort by renderOrder (lower first)
        sortedEntities.sort(Comparator.comparingInt(eid -> ecs.getComponent(eid, SpriteRenderer.class).renderOrder));

        for (int entityId : sortedEntities) {
            Transform t = ecs.getComponent(entityId, Transform.class);
            SpriteRenderer r = ecs.getComponent(entityId, SpriteRenderer.class);


           double w = t.scale.getX();
           double h = t.scale.getY();

           int inverted = (r.isInverted()) ? -1 : 1;

           g2.drawImage(r.getCurrentSprite().getImage(),
                        (int)(t.position.getX() - (w * inverted / 2)),
                        (int)(t.position.getY() - (h / 2)),
                        (int)t.scale.x * inverted,
                        (int)t.scale.y,
                        null);
        }

    }
}
