package dramen.ld29.entity;

import dramen.ld29.util.Time;

public class Entity {

	public float x, y, width, height, xMotion, yMotion;
	
	public Entity(float x, float y, float width, float height) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update() {
		
		move();
	}
	
	public void onCollisionWithEntity(Entity e) {
		
	}
	
	public void move() {
		
		x += xMotion * Time.delta();
		y += yMotion * Time.delta();
	}
	
	public void render() {
		
	}
}
