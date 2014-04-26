package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class EntityJet extends EntityEnemy {
	
	public EntityJet(float x, float y) {
		
		super(x, y, 48f, 22f, 10f);
		damage = 5f;
		xMotion -= 500f;
	}

	@Override
	public void die() {
		
		super.die();
		Game.instance().score += 25f;
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
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 61, 93, 24f, 11f, true, "set0");
	}
}
