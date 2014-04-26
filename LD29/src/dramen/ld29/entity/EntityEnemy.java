package dramen.ld29.entity;

import dramen.ld29.game.Game;

public class EntityEnemy extends Entity {

	public float damage = 0f;
	public float health, maxHealth;
	public boolean invincible = false;
	
	public EntityEnemy(float x, float y, float width, float height, float health) {
		super(x, y, width, height);
		this.maxHealth = health;
		this.health = maxHealth;
	}
	
	@Override
	public void onCollisionWithEntity(Entity e) {
		
		if (e instanceof EntityPlayer) {
			
			((EntityPlayer) e).player.dealDamage(damage);
			die();
		}
	}
	
	public void dealDamage(float damage) {
		
		if (!invincible) {
			health -= damage;
		}
	}
	
	public void die() {
		
		Game.instance().removeEntity(this);
	}
	
	@Override
	public void update() {
	
		if (health <= 0) {
			
			die();
		}
		
		super.update();
	}
}
