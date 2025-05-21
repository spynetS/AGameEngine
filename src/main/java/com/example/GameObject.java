package com.example;

import com.example.components.*;

public class GameObject {
    private int id = 0;
    private Scene owner;

    public GameObject(Scene owner, int id){
        this.owner = owner;
        this.id = id;
    }

    public void addComponent(Component component){
        if (component instanceof Script) {
            ((Script) component).initScript(this);
        }
        this.owner.getEcs().addComponent(id, component);
    }
    public <T extends Component> T getComponent(Class<T> comp){
        return this.owner.getEcs().getComponent(id, comp);
    }


}
