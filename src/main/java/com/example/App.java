package com.example;

import com.example.components.*;

public class App extends GameApplication {

	public static void main(String args[]){
		App app = new App();
		app.setTitle("Mitt Spel");
		Scene scene = new Scene(app);
		int size = 100;
		int gridSize = (int) Math.ceil(Math.sqrt(size));
		float gap = 2f;
		float cellSize = 10f + gap;

		for (int i = 0; i < size; i++) {
			GameObject ob = scene.createGameObject();
			ob.addComponent(new Renderer());
			Transform t = ob.getComponent(Transform.class);
			int row = i / gridSize;
			int col = i % gridSize;
			t.position = new Vector2(col * cellSize, row * cellSize);
			t.scale = new Vector2(10, 10);
			ob.addComponent(new Movement());
		}

		app.setSelectedScene(scene);



	}

}
