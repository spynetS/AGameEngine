package com.example;

import com.engine.*;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import com.engine.components.*;

public class App extends GameApplication {
	Sprite sprite = new Sprite("src/main/resources/sprites.png", new Rectangle(0,0,62,310/5));
	Sprite grass_sprite = new Sprite("src/main/resources/sprites.png", new Rectangle(0,310/5*2,62,310/5));
	Sprite obsticle_sprite = new Sprite("src/main/resources/sprites.png", new Rectangle(0,310/5*4,62,310/5));

int[][] map = {
  {0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,2,0},
  {0,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,2,0,0,2,0},
  {0,0,0,0,2,0,2,2,2,2,2,2,2,2,0,0,0,1,0,2,0,0,2,0},
  {0,2,2,0,2,0,0,0,0,0,0,0,0,0,0,2,2,1,0,2,2,2,2,0},
  {0,0,0,0,2,0,0,0,0,2,2,2,2,2,0,0,2,1,0,0,0,0,0,0},
  {2,2,2,0,2,2,2,0,0,0,0,0,0,2,0,0,2,1,1,1,1,1,1,1},
  {0,0,0,0,0,0,2,0,2,2,0,0,0,2,0,0,0,0,0,0,0,0,0,1},
  {0,2,2,2,2,0,2,0,2,0,0,0,0,2,2,2,2,2,2,2,0,2,0,1},
  {0,2,0,0,2,0,2,0,2,0,0,0,0,0,0,0,0,0,0,2,0,2,0,1},
  {0,2,0,2,2,0,2,0,0,0,2,2,2,2,2,0,0,0,0,2,0,0,0,1},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,2,2,2,2,2,2,2,1},
  {0,2,2,2,2,2,2,2,0,2,2,0,0,0,2,0,0,0,0,0,0,0,0,1},
  {0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0}
};

	public void loadMap(Scene scene){
		int w = 100;
		int h = 100;
		int x = 0;
		int y = 0;
		double gap = 1;
		for(int[] row : map){
			for(int tile : row){
				if(tile == 0){
					GameObject g = scene.createGameObject();
					SpriteRenderer renderer = new SpriteRenderer();
					renderer.addSprite(sprite);
					g.addComponent(renderer);
					g.addComponent(new TowerSpawner());
					g.transform.position = new Vector2(x*w*gap,y*h*gap);
			}
				else if (tile == 1){
					GameObject g = scene.createGameObject();
					SpriteRenderer renderer = new SpriteRenderer();
					renderer.addSprite(grass_sprite);
					g.addComponent(renderer);
					g.transform.position = new Vector2(x*w*gap,y*h*gap);
				}
				else{
					GameObject g = scene.createGameObject();
					SpriteRenderer renderer = new SpriteRenderer();
					renderer.addSprite(obsticle_sprite);
					g.addComponent(renderer);
					g.addComponent(new Collider(true));
					g.addComponent(new Rigidbody());
					g.transform.position = new Vector2(x*w*gap,y*h*gap);
					g.transform.tag = "enemy";
				}
				x++;
			}
			y++;
			x=0;
		}
	}

	public static void main(String args[]){
		App game = new App();
		game.setTitle("Tower defence game");
		Scene gameScene = new Scene(game);
		gameScene.setCameraPosition(new Vector2(14,14));


		GameObject player = gameScene.createGameObject();
		player.addComponent(new MouseScript());

		GameObject enemy = gameScene.createGameObject();
		enemy.addComponent(new Enemy());
		enemy.addComponent(new ShapeRenderer());
		enemy.transform.position = new Vector2(100,100);

		game.loadMap(gameScene);


		game.setSelectedScene(gameScene);
	}

}
