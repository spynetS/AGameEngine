package com.example;

import com.engine.Debug;
import com.engine.components.*;

public class Bullet extends Script{

	double timer = 0;

	@Override
	public void update(double deltaTime) {
		// TODO Auto-generated method stub
		super.update(deltaTime);
		timer += deltaTime;
		if(timer > 2)
			gameObject.destroy();
	}
	@Override
	public void onTrigger(int entity) {
		Debug.log(entity+" - "+gameObject.getId());

		Transform t = gameObject.scene.getEcs().getComponent(entity,Transform.class);
		if(t!=null && t.tag == "enemy"){
						gameObject.scene.getEcs().removeAllComponents(entity);
						gameObject.destroy();
		}

	}
}
