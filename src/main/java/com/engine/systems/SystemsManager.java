package com.engine.systems;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.engine.Scene;

public class SystemsManager {
    private ArrayList<ISystem> systems = new ArrayList<>();

    public SystemsManager(){
        // Register systems
        systems.add(new ColliderSystem());
        systems.add(new SpriteRendererSystem());
        systems.add(new RendererSystem());
        systems.add(new RigidbodySystem());
    }

    public ArrayList<ISystem> getSystems() {
        return systems;
    }

    public void updateSystems(Scene scene){
        for(ISystem system : systems){
            system.update(scene);
        }
    }
    public void renderSystems(Scene scene, Graphics2D g2){
        for(ISystem system : systems){
            system.render(scene, g2);
        }
    }

}
