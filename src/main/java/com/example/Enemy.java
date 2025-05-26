package com.example;

import java.util.Random;

import com.engine.components.Script;
import com.engine.components.Transform;
import com.engine.*;

public class Enemy extends Script{

	Vector2 dir = Vector2.zero;
	double timer = 0;
	Random rand = new Random();

	@Override
	public void update(double deltaTime) {
		timer+= deltaTime;
		Transform t = gameObject.transform;

		if(timer > 1){
			timer = 0;
			// Random angle in radians
			double angle = rand.nextDouble() * 2 * Math.PI;

			// Unit direction vector
			dir = new Vector2(Math.cos(angle), Math.sin(angle)); //random Vector2
		}

		gameObject.transform.position = gameObject.transform.position.add(dir.multiply(0.5));
	}

}
