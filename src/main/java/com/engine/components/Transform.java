package com.engine.components;

import com.engine.Vector2;

public class Transform extends Component {

    public Vector2 position = new Vector2();
    public Vector2 scale = new Vector2(100,100);
    public double rotation = 0;
    public String tag = "";
}
