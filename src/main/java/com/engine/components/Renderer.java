package com.engine.components;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;


public class Renderer extends Component {

    public Color color = Color.darkGray;
    public int renderOrder = 0;

    public Rectangle2D getShape(Transform t){

        double w = t.scale.getX();
        double h = t.scale.getY();
        double x = t.position.getX()-(w/2);
        double y = t.position.getY()-(h/2);

        return new Rectangle2D.Double(x,
                                      y,
                                      w,
                                      h);

    }
}
