package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;
import dramen.ld29.util.Time;

public class EntitySubmarine extends EntityEnemy {

	float animTime;
	float shootTime;
	int frame = 0;
	
	public EntitySubmarine(float x, float y) {
		super(x, y, 76f, 40f, 30f);
		xMotion = -240f;
		damage = 20f;
	}

	@Override
	public void die() {
		
		super.die();
		Game.instance().score += 50f;
	}
	
	public void update() {
		
		animTime += Time.delta();
		shootTime += Time.delta();
		
		if (animTime > 0.05f) {
			
			animTime -= 0.05f;
			
			if (frame == 0) frame = 1;
			else if (frame == 1) frame = 0;
		}
		
		if (shootTime > 2f) {
			
			shootTime -= 2f;
			
			EntityTorpedo shot = new EntityTorpedo(x, y - 14f, 5f);
			shot.xMotion -= 520f;
			Game.instance().addEntity(0, shot);
		}
		
		if (x + width / 2f < 0f) {
			
			Game.instance().removeEntity(this);
		}
		
		super.update();
	}
	
	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 89f, 93f + frame * 20f, 38f, 20f, true, "set0");
	}
}
