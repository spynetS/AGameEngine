package com.engine;

import com.engine.components.*;

public class GameObject {
    private int id = 0;
    public Scene scene;
    public Transform transform;

    public int getId() {
        return id;
    }

    public GameObject(Scene scene){
        this.scene = scene;
        this.id = scene.getEntityId();
        transform = new Transform();
        addComponent(transform);
    }

    public Component addComponent(Component component){
        if (component instanceof Script) {
            ((Script) component).initScript(this);
        }
        this.scene.getEcs().addComponent(id, component);
        return component;
    }
    public <T extends Component> T getComponent(Class<T> comp){
        return this.scene.getEcs().getComponent(id, comp);
    }

    public void destroy(){
        this.scene.getEcs().removeAllComponents(this.id);
        this.scene.getEcs().removeAllScripts(this.id);
    }

}
