package com.example;

import java.awt.Rectangle;
import java.awt.Shape;

import com.engine.Debug;
import com.engine.GameObject;
import com.engine.Vector2;
import com.engine.components.*;
import com.engine.input.Input;
import com.engine.input.Keys;

public class MouseScript extends Script {


	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		super.update(deltaTime);
		Transform t = gameObject.getComponent(Transform.class);
		t.position = Input.getMousePosition();


		for(int entiy : gameObject.scene.getEcs().getEntitiesWithComponents(SpriteRenderer.class)){

			SpriteRenderer r = gameObject.scene.getEcs().getComponent(entiy,SpriteRenderer.class);
			Transform t2 = gameObject.scene.getEcs().getComponent(entiy,Transform.class);
			Shape shape = r.getShape(t2);


			if(shape.contains(Input.getMousePosition().x,Input.getMousePosition().y)){
				if(Input.isKeyPressed(Keys.SPACE)){
					try{
						TowerSpawner ts = gameObject.scene.getEcs().getComponent(entiy, TowerSpawner.class);
					ts.spawnTower();
					}
					catch(Exception e){}

				}
				t2.scale = new Vector2(110,110);
			}
			else{
				t2.scale = new Vector2(100,100);
			}
	 	}

	}

}
