package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class EntityBoat extends EntityEnemy {

	private EntityBeam beam;
	
	public EntityBoat(float x, float y) {
		super(x, y, 104f, 82f, 50f);
		damage = 20f;
		xMotion = -120f;
		
		beam = new EntityBeam(x + 9f, y + 42f + 200f, 400f);
		Game.instance().addEntity(beam);
	}

	@Override
	public void die() {
		
		super.die();
		Game.instance().removeEntity(beam);
		Game.instance().score += 100f;
	}
	
	@Override
	public void update() {
		
		beam.x = x + 10f;
		
		if (x < -width / 2f) {
			
			Game.instance().removeEntity(beam);
			Game.instance().removeEntity(this);
		}
		
		super.update();
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 1f, 1f, 1f, 1f, 92f, 36f, 52f, 41f, true, "set0");
	}
}
