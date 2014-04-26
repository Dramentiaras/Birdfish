package dramen.ld29.entity;

import org.lwjgl.opengl.Display;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;
import dramen.ld29.util.Time;

public class EntityBigBubble extends Entity {

	public float damage;
	float time = 0f;
	
	public EntityBigBubble(float x, float y, float damage) {
		super(x, y, 40f, 40f);
		this.damage = damage;
	}

	public void render() {
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 71f, 36f, 20f, 20f, true, "set0");
	}
	
	@Override
	public void onCollisionWithEntity(Entity e) {
		
		if (e instanceof EntityEnemy) {
			
			((EntityEnemy) e).dealDamage(damage);
			explode();
		}
	}
	
	public void explode() {
		
		for (int i = 0; i < 10; i++) {
			
			EntityBubble bubble = new EntityBubble(x, y, 10f);
			bubble.xMotion = 480f;
			bubble.yMotion = (i - 5) * 20f;
			
			Game.instance().addEntity(bubble);
		}
		
		Game.instance().removeEntity(this);
	}

	public void update() {
		
		time += Time.delta();
		
		if (x < 0 - width / 2f || x > Display.getWidth() + width / 2f) {
			
			Game.instance().removeEntity(this);
		}
		
		if (time > 1f) {
			
			explode();
		}
		
		xMotion += 300f * Time.delta();
		
		super.update();
	}
}
