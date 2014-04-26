package dramen.ld29.entity;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class EntityTorpedo extends Entity {

	float damage;
	
	public EntityTorpedo(float x, float y, float damage) {
		
		super(x, y, 38f, 6f);
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
	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 58f, 104f, 19f, 3f, true, "set0");
	}
}
