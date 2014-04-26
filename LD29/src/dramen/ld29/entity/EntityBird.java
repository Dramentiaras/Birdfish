package dramen.ld29.entity;

import org.lwjgl.input.Keyboard;

import dramen.ld29.game.Game;
import dramen.ld29.game.Player;
import dramen.ld29.input.Input;
import dramen.ld29.render.RenderEngine;
import dramen.ld29.util.Time;

public class EntityBird extends EntityPlayer {

	int body = 1, head;
	
	float time = 0f;
	float animTime = 0f;
	float fireTime = 0f;
	float burst = 0f;
	boolean animUp = true;
	boolean basic = false;
	
	int shots = 0;
	
	float rotation = 0f;
	
	public EntityBird(float x, float y, Player player) {
		
		super(x, y, 50f, 36f, player);
	}

	public void update() {
		
		float delta = Time.delta();
		
		time += delta;

		player.air -= 10f * delta;
		player.water += 20f * delta;
		
		if (Input.keyDown(Keyboard.KEY_UP)) {
			
			animTime += delta;
			
			if (animTime > 0.1f) {
				
				animTime -= 0.1f;
				
				if (animUp) {
					
					body += 1;
					
					if (body == 3) {
						
						animUp = false;
					}
				}
				else {
					
					body -= 1;
					
					if (body == 0) {
						
						animUp = true;
					}
				}
			}
		}
		else {
			
			body = 2;
			animTime = 0f;
			animUp = false;
		}
		
		rotation = (float)Math.atan(yMotion / MAX_SPEED);
		
		if (firing) {
			
			fireTime += delta;
			rotation = 0f;
			
			if (basic) {
				
				burst += delta;
				
				head = 2;
				if (fireTime > 0.5f) {
					
					firing = false;
					fireTime = 0f;
					burst = 0f;
					shots = 0;
				}
				
				if (burst > 0.1f && shots < 3) {
					
					burst -= 0.1f;
					shots += 1;
					EntityBubble bubble = new EntityBubble(x + width / 2f, y, 10f); bubble.xMotion = 240f;
					Game.instance().addEntity(bubble);
				}
			}
			else {
				
				head = 3;
				if (fireTime > 0.75f) {
					
					firing = false;
					fireTime = 0f;
				}
			}
		}
		else {
			
			head = 0;
			if (player.air / Player.MAX_AIR < .5f) head = 1;
		}
		
		super.update();
	}
	
	@Override
	public void fireBasic() {

		firing = true;
		basic = true;
	}
	
	public void fireSpecial() {
		
		if (player.air >= 25f) {
			
			EntityBigBubble bubble = new EntityBigBubble(x + width / 2f + 20f, y, 50f); bubble.xMotion = 180f;
			Game.instance().addEntity(bubble);
			
			player.air -= 25f;
			
			firing = true;
			basic = false;
		}
	}
	
	@Override
	public void render() {
		
		float g = 1f, b = 1f;
		
		if (player.air / Player.MAX_AIR < .25f) {
			
			if (time % 0.5f < 0.25f) {
				
				g = 0.25f;
				b = 0.25f;
			}
		}
		
		if (player.invincible) {
			
			g = 0.25f;
			b = 0.25f;
		}
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, rotation, 1f, g, b, 1f, 54 + body * 25f, 18f, 25, 18, true, "set0");
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, rotation, 1f, g, b, 1f, 54 + head * 25f, 0f, 25, 18, true, "set0");
	}
}
