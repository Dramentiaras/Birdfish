package dramen.ld29.entity;

import org.lwjgl.opengl.Display;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class EntityBubble extends Entity {

	public float damage;
	
	public EntityBubble(float x, float y, float damage) {
		
		super(x, y, 10f, 10f);
		this.damage = damage;
	}

	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 66f, 36f, 5f, 5f, true, "set0");
	}

	@Override
	public void onCollisionWithEntity(Entity e) {
		
		if (e instanceof EntityEnemy) {
			
			((EntityEnemy) e).dealDamage(damage);
			Game.instance().removeEntity(this);
		}
	}
	
	public void update() {
		
		if (x < 0 - width / 2f || x > Display.getWidth() + width / 2f
				|| y > Display.getHeight() / 2f - 5f || y < 5f) {
			
			Game.instance().removeEntity(this);
		}
		
		super.update();
	}
}
