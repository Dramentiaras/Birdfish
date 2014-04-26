package dramen.ld29.entity;

import org.lwjgl.input.Keyboard;

import dramen.ld29.game.Game;
import dramen.ld29.game.Player;
import dramen.ld29.input.Input;
import dramen.ld29.render.RenderEngine;
import dramen.ld29.util.Time;

public class EntityFish extends EntityPlayer {
	
	float rotation = 0f;
	int body = 0;
	int head = 0;
	
	float time;
	float animTime = 0f;
	float fireTime = 0f;
	float burst = 0f;
	boolean basic = false;
	
	public EntityFish(float x, float y, Player player) {
		
		super(x, y, 54f, 34f, player);
	}

	public void update() {
		
		float delta = Time.delta();
		
		time += delta;
		player.water -= 10f * delta;
		player.air += 20f * delta;
		
		if (Input.keyDown(Keyboard.KEY_UP)) {
			
			animTime += delta;
			
			if (animTime > 0.15f) {
				
				animTime -= 0.15f;
				if (body == 0) {
					body = 1;
				}
				else if (body == 1) {
					body = 0;
				}
			}
		}
		else {
			
			animTime = 0;
			body = 0;
		}
		
		rotation = (float) Math.atan(yMotion / MAX_SPEED);
		
		if (firing) {
			
			fireTime += delta;
			rotation = 0f;
			
			if (basic) {
				
				head = 3;
				if (fireTime > 0.2f) {
					
					firing = false;
					fireTime = 0f;
				}
			}
			else {
				
				burst += delta;
				
				head = 2;
				if (fireTime > 1f) {
					
					firing = false;
					fireTime = 0f;
				}
				
				if (burst > 0.01f) {
					
					burst -= 0.01f;
					EntityDrop drop = new EntityDrop(x + width / 2f + 6f, (float) (y - height / 4f + Math.random() * height / 2f), 5f); drop.xMotion = 560f;
					Game.instance().addEntity(drop);
				}
			}
 		}
		else {
			
			head = 0;
			if (player.water / Player.MAX_WATER < 0.5f) head = 1;
		}
		
		super.update();
	}
	
	@Override
	public void fireBasic() {
		
		EntityDrop drop = new EntityDrop(x + width / 2f, y, 10f);
		drop.xMotion = 560f;
		Game.instance().addEntity(drop);
		
		firing = true;
		basic = true;
	}
	
	public void fireSpecial() {
		
		if (player.water >= 25f) {
			
			player.water -= 25f;
			firing = true;
			basic = false;
		}
	}
	
	@Override
	public void render() {
		
		float u = 0;
		float v = 0;
		
		if (head == 1 || head == 3) u = 27f;
		if (head == 2 || head == 3) v = 17f;
		
		float g = 1f, b = 1f;
		
		if (player.water / Player.MAX_WATER < .25f) {
			
			if (time % 0.5f < 0.25f) {
				
				g = 0.25f;
				b = 0.25f;
			}
		}
		
		if (player.invincible) {
			
			g = 0.25f;
			b = 0.25f;
		}
		
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, rotation, 1f, g, b, 1f, body * 27f, 34, 27, 17, true, "set0");
		RenderEngine.instance().renderTexturedQuad(x, y, width, height, rotation, 1f, g, b, 1f, u, v, 27, 17, true, "set0");
	}
}
