package dramen.ld29.entity;

import org.lwjgl.opengl.Display;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class EntityDrop extends Entity {

	float damage;
	
	public EntityDrop(float x, float y, float damage) {
		
		super(x, y, 24f, 10f);
		this.damage = damage;
	}

	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 54, 36f, 12f, 5f, true, "set0");
	}
	
	@Override
	public void onCollisionWithEntity(Entity e) {
		
		if (e instanceof EntityEnemy) {
			
			((EntityEnemy) e).dealDamage(damage);
			Game.instance().removeEntity(this);
		}
	}
	
	public void update() {
		
		if (x < 0 - width / 2f || x > Display.getWidth() + width / 2f) {

			Game.instance().removeEntity(this);
		}
		
		super.update();
	}
}
