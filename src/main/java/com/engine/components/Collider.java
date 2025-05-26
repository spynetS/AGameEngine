package com.engine.components;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import com.engine.systems.ColliderSystem;
import com.engine.*;

public class Collider extends Component{

    public Vector2 offset = new Vector2();
    public Vector2 size = new Vector2();
    public boolean isColliding = false;

    public boolean isTrigger = false;

    public int collisionEntity;

    public Collider(){}
    public Collider(boolean isTrigger){
        this.isTrigger = isTrigger;
    }

    public Rectangle2D getShape(Transform t){

        double w = t.scale.getX()+this.size.getX();
        double h = t.scale.getY()+this.size.getY();
        double x = t.position.getX()+this.offset.getX()-(w/2);
        double y = t.position.getY()+this.offset.getY()-(h/2);

        return new Rectangle2D.Double(x,
                                      y,
                                      w,
                                      h);

    }
    
}
