package com.example;


import java.awt.Rectangle;

import com.engine.*;
import com.engine.input.*;
import com.engine.components.Collider;
import com.engine.components.Rigidbody;
import com.engine.components.Script;
import com.engine.components.ShapeRenderer;
import com.engine.components.Sprite;
import com.engine.components.SpriteRenderer;

public class Tower extends Script{

	public double shootingSpeed = 2;
	public double timer = 0;
	public Sprite sprite = new Sprite("src/main/resources/towers.png",new Rectangle(200,40,50,100));

	@Override
	public void update(double deltaTime) {
		super.update(deltaTime);

		Vector2 dir = gameObject.transform.position.lookAt(Input.getMousePosition());
		float rotationRadians = (float) Math.atan2(dir.y, dir.x);
		gameObject.transform.rotation = rotationRadians+Math.PI/2;
		timer += deltaTime;

		if(timer > shootingSpeed){
			timer = 0;
			GameObject bullet = new GameObject(gameObject.scene);
			Rigidbody rb = new Rigidbody();
			bullet.addComponent(rb);

			rb.addForce(dir.multiply(10000));
			SpriteRenderer render = new SpriteRenderer();
			render.setKeepAspect(true);
			render.addSprite(sprite);
			render.renderOrder = 100;

			bullet.addComponent(new Bullet());
			bullet.addComponent(render);
			bullet.addComponent(new Collider(true));
			bullet.transform.position = gameObject.transform.position;
			bullet.transform.scale = new Vector2(100,50);
			bullet.transform.rotation = rotationRadians+Math.PI/2;

		}
	}

}
