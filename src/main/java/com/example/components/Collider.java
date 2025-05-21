package com.example.components;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import com.example.systems.ColliderSystem;

public class Collider extends Component{

    public int x = 0;
    public int y = 0;
    public int w = 50;
    public int h = 10;

    public boolean isColliding = false;

    public Rectangle2D getShape(Transform t){

        double w = t.scale.getX()+this.w;
        double h = t.scale.getY()+this.h;
        double x = t.position.getX()+this.y-(w/2);
        double y = t.position.getY()+this.y-(h/2);

        return new Rectangle2D.Double(x,
                                      y,
                                      w,
                                      h);

    }
    
}
