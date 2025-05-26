package com.engine.systems;

import java.awt.Graphics2D;

import com.engine.Scene;

public interface ISystem {

    public void update(Scene scene);
    public void render(Scene scene, Graphics2D g2);


}
