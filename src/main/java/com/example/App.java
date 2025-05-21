package com.example;

import com.example.components.*;

public class App extends GameApplication {

	public static void main(String args[]){
		App app = new App();
		app.setTitle("Mitt Spel");
		Scene scene = new Scene(app);

		GameObject ob = scene.createGameObject();
		ob.addComponent(new Renderer());
		ob.addComponent(new Movement());
		ob.addComponent(new Collider());
		ob.addComponent(new RigidBody(true));

		GameObject zombie = scene.createGameObject();
		zombie.addComponent(new Renderer());
		zombie.getComponent(Transform.class).position = new Vector2(0,200);
		zombie.addComponent(new Collider());

		app.setSelectedScene(scene);
	}

}
