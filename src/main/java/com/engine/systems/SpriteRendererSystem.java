package com.engine.systems;

import com.engine.ecs.ECS;

import com.engine.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
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
            BufferedImage image = r.getCurrentSprite().getImage();

            double imageW = image.getWidth();
            double imageH = image.getHeight();

            int inverted = r.isInverted() ? -1 : 1;
            boolean keepAspect = r.isKeepAspect(); // Or wherever this is defined

            // Position to draw the center of the sprite
            double centerX = t.position.getX();
            double centerY = t.position.getY();

            // Compute scale factors
            double scaleX = (t.scale.x+1) / imageW;
            double scaleY = (t.scale.y+1) / imageH;

            if (keepAspect) {
                // Use the smaller scale to preserve image proportions
                double uniformScale = Math.min(scaleX, scaleY);
                scaleX = uniformScale * inverted;
                scaleY = uniformScale;
            } else {
                scaleX *= inverted;
            }

            AffineTransform transform = new AffineTransform();

            // 1. Translate to position
            transform.translate(centerX, centerY);

            // 2. Rotate around center
            transform.rotate(t.rotation); // radians

            // 3. Scale (including flip)
            transform.scale(scaleX, scaleY);

            // 4. Translate to center image
            transform.translate(-imageW / 2.0, -imageH / 2.0);

            g2.drawImage(image, transform, null);
        }

    }
}
