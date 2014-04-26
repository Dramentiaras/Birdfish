package dramen.ld29.game;

import dramen.ld29.entity.EntityPlayer;
import dramen.ld29.util.Time;

public class Player {

	private EntityPlayer entity;
	public float air, water;
	public boolean invincible = false;
	float inviTime = 0f;
	public static float MAX_AIR, MAX_WATER, MAX_HEALTH;
	
	public float health;
	
	public Player() {
		
		MAX_AIR = 100f;
		MAX_WATER = 100f;
		MAX_HEALTH = 100f;
		air = MAX_AIR;
		water = MAX_WATER;
		health = MAX_HEALTH;
	}
	
	public void setEntity(EntityPlayer entity) {
		
		this.entity = entity;
	}
	
	public void dealDamage(float damage) {
		
		if (!invincible) {
			health -= damage;
			invincible = true;
		}
	}
	
	public void update() {
		
		if (air <= 0f) {
			
			health -= 10f * Time.delta();
			air = 0f;
		}
		if (air > MAX_AIR) {
			
			air = MAX_AIR;
		}
		if (water <= 0f) {
			
			health -= 10f * Time.delta();
			water = 0f;
		}
		if (water > MAX_WATER) {
			
			water = MAX_WATER;
		}
		if (health <= 0f) {
			
			entity.die();
			health = 0f;
		}
		if (health > MAX_HEALTH) {
			
			health = MAX_HEALTH;
		}
		
		if (invincible) {
			
			inviTime += Time.delta();
			
			if (inviTime > 0.15f) {
				
				invincible = false;
				inviTime = 0f;
			}
		}
	}
}
