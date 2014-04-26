package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;
import dramen.ld29.util.Time;

public class EntityEnemyFish extends EntityEnemy {

	float animTime;
	int frame;
	boolean back = false;
	
	public EntityEnemyFish(float x, float y) {
		
		super(x, y, 48f, 22f, 10f);
		damage = 5f;
		xMotion = -500f;
	}

	@Override
	public void die() {
		
		super.die();
		Game.instance().score += 25f;
	}
	
	@Override
	public void update() {
		
		animTime += Time.delta();
		
		if (x + width / 2f < 0f) {
			
			Game.instance().removeEntity(this);
		}
		
		if (animTime > 0.05f) {
			
			animTime -= 0.05f;
			
			if (frame == 0) {
				if (back) {
					frame = 1;
				}
				else {
					frame = 2;
				}
			}
			else {
				frame = 0;
			}
		}
		
		super.update();
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 0f, 111f + frame * 11f, 24f, 11f, true, "set0");
	}
}
