package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class EntityShot extends Entity {

	float damage;
	
	public EntityShot(float x, float y, float damage) {
		super(x, y, 8f, 2f);
		this.damage = damage;
	}

	@Override
	public void onCollisionWithEntity(Entity e) {
		
		if (e instanceof EntityPlayer) {
			
			((EntityPlayer) e).player.dealDamage(damage);
			Game.instance().removeEntity(this);
		}
	}
	
	@Override
	public void update() {
		
		super.update();
	}
	
	@Override
	public void render() {
		
		RenderEngine.instance().renderQuad(x, y, width, height, 1f, 1f, 0f, 1f);
	}
}
