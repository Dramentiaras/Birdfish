package dramen.ld29.entity;

import dramen.ld29.render.RenderEngine;

public class EntityBeam extends EntityEnemy {

	public EntityBeam(float x, float y, float height) {
		super(x, y, 14f, height, 1f);
		invincible = true;
		damage = 20f;
	}

	@Override
	public void render() {
		
		for (int i = 0; i < height % 32f; i++) {
			
			RenderEngine.instance().renderTexturedQuad(x, y - height / 2f + 10f + 32f * i, width, 32f, 0f, 61, 43, 7, 16, true, "set0");
		}
	}
}
