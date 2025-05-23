package com.example;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.example.components.*;

public class App extends GameApplication {

	public static void main(String args[]){
		App app = new App();
		//app.setTitle("Mitt Spel");
		Scene scene = new Scene(app);

		GameObject ob = scene.createGameObject();
		SpriteRenderer spriteRenderer = new SpriteRenderer();
		String path = "src/main/resources/Walk.png";


		spriteRenderer.addAnimation(Sprite.getSprites(path,
													  new Vector2(192,96),
													  new Vector2(6,3),
													  new Vector2(0,0)));

		spriteRenderer.addAnimation(Sprite.getSprites(path,
													  new Vector2(192,96),
													  new Vector2(6,3),
													  new Vector2(0,1)));

		spriteRenderer.addAnimation(Sprite.getSprites(path,
													  new Vector2(192,96),
													  new Vector2(6,3),
													  new Vector2(0,2)));
		spriteRenderer.addAnimation(Sprite.getSprites("src/main/resources/Idle.png",
													  new Vector2(128,96),
													  new Vector2(4,3),
													  new Vector2(0,0)));
		spriteRenderer.addAnimation(Sprite.getSprites("src/main/resources/death.png",
													  new Vector2(384,48),
													  new Vector2(6,1),
													  new Vector2(0,0)));
		ob.addComponent(spriteRenderer);
		ob.getComponent(Transform.class).scale = new Vector2(400,400);
		ob.addComponent(new Rigidbody());
		Movement m = new Movement(false);
		ob.addComponent(m);
		spriteRenderer.animationListeners.add(m);

		Debug.showWhere = true;

		app.setSelectedScene(scene);
	}

	public static class TestObject extends GameObject{
		public TestObject(Scene scene){
			super(scene);
			addComponent(new ShapeRenderer());
			addComponent(new Movement(true));
			Rigidbody r = new Rigidbody();
			addComponent(r);
			r.mass = 1;
			r.friction = 0.005;

		}
	}

}
