package com.example;

import java.awt.Color;

import com.example.components.*;

public class App extends GameApplication {

	public static void main(String args[]){
		App app = new App();
		//app.setTitle("Mitt Spel");
		Scene scene = new Scene(app);

		GameObject ob = scene.createGameObject();
		SpriteRenderer r = new SpriteRenderer();
		ob.addComponent(r);
		r.addSprite(new Sprite("src/main/resources/eric.png"), 0);
		r.addSprite(new Sprite("src/main/resources/hansuck.jpg"), 0);


		ob.getComponent(Transform.class).scale = new Vector2(500,500);
		//ob.addComponent(new ShapeRenderer());


		ob.addComponent(new Movement(true));
		ob.addComponent(new Rigidbody(false));

		app.setSelectedScene(scene);
	}

}
