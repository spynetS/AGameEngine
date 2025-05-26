package com.example;

import java.awt.Rectangle;

import com.engine.GameObject;
import com.engine.components.Script;
import com.engine.components.Sprite;
import com.engine.components.SpriteRenderer;

public class TowerSpawner extends Script {


	GameObject tower;

	public void spawnTower(){
		if(tower != null) return;

		SpriteRenderer r2 = new SpriteRenderer();
		int w = 534/3;
		int h = 661/3;
		r2.addSprite(new Sprite("src/main/resources/towers.png",new Rectangle(50,0,200,300)));
		r2.setKeepAspect(true);
		GameObject tower = new GameObject(gameObject.scene);
		tower.addComponent(r2);
		tower.addComponent(new Tower());
		tower.transform.position = gameObject.transform.position;
		this.tower = tower;
	}

	@Override
	public void update(double deltaTime) {

	}
}
