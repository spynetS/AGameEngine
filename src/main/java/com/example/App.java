package com.example;

import java.awt.Color;
import java.util.Random;

import com.example.components.*;

public class App extends GameApplication {

	public static void main(String args[]){
		App app = new App();
		//app.setTitle("Mitt Spel");
		Scene scene = new Scene(app);

		TestObject t = new TestObject(scene);

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
			r.friction = 0.5;

		}
	}

}
