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
		ob.addComponent(new Rigidbody(false));
		ob.getComponent(Transform.class).scale = new Vector2(50,100);

		GameObject zombie = scene.createGameObject();
		zombie.addComponent(new Renderer());
		zombie.getComponent(Transform.class).position = new Vector2(0,200);
		zombie.getComponent(Transform.class).scale = new Vector2(200,50);
		zombie.addComponent(new Collider());
		zombie.addComponent(new Rigidbody(false));
		zombie.getComponent(Rigidbody.class).mass = 1;


		app.setSelectedScene(scene);
	}

}
