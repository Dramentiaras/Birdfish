package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class EntitySeaweed extends EntityEnemy {

	public EntitySeaweed(float x, float y) {
		super(x, y, 45f, 294f, 40f);
		damage = 20f;
		xMotion = -360f;
	}

	@Override
	public void die() {
		
		super.die();
		Game.instance().score += 75f;
	}
	
	@Override
	public void update() {
		
		if (x + width / 2f < 0f) {
			
			Game.instance().removeEntity(this);
		}
		
		super.update();
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 154f, 0f, 15f, 98f, true, "set0");
	}
}
