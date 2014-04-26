package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;
import dramen.ld29.util.Time;

public class EntityHelicopter extends EntityEnemy {

	float animTime;
	float shootTime;
	int frame;
	
	public EntityHelicopter(float x, float y) {
		super(x, y, 56f, 30f, 30f);
		xMotion -= 240f;
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
			
			EntityShot shot = new EntityShot(x - 28f, y - 15f, 5f);
			shot.xMotion -= 520f;
			Game.instance().addEntity(shot);
		}
		
		if (x + width / 2f < 0f) {
			
			Game.instance().removeEntity(this);
		}
		
		super.update();
	}
	
	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 61f + frame * 28f, 78f, 28f, 15f, true, "set0");
	}
}
