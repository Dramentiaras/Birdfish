package dramen.ld29.game;

import org.lwjgl.opengl.Display;

import dramen.ld29.entity.EntityBoat;
import dramen.ld29.entity.EntityEnemyFish;
import dramen.ld29.entity.EntityHelicopter;
import dramen.ld29.entity.EntityJet;
import dramen.ld29.entity.EntitySeaweed;
import dramen.ld29.entity.EntitySubmarine;
import dramen.ld29.util.Time;

public class Spawner {

	float time;
	float rate;
	float baseRate;
	
	public Spawner(float rate) {
		
		this.baseRate = rate;
		this.rate = baseRate;
	}
	
	public void setBaseRate(float rate) {
		
		this.baseRate = rate;
	}
	
	public float getBaseRate() {
		
		return baseRate;
	}
	
	public void update() {
		
		time += Time.delta();
		
		if (time > rate) {
			
			time -= rate;
			
			float random = (float) Math.random();
			
			if (random > 0.9f) {  
				Game.instance().addEntity(new EntityBoat(Display.getWidth() + 52f, Display.getHeight() / 2f + 20f));
				rate = baseRate;
			}
			else if (random > 0.8f) {
				Game.instance().addEntity(new EntitySeaweed(Display.getWidth() + 22.5f, 3f + 150f));
				rate = baseRate;
			}
			else if (random > 0.65f) {
				Game.instance().addEntity(new EntityHelicopter(Display.getWidth() + 28f, (float) (Display.getHeight() / 2f + 50 + Math.random() * 235f)));
				rate = baseRate / 2f;
			}
			else if (random > 0.5f) {
				Game.instance().addEntity(new EntitySubmarine(Display.getWidth() + 38f, (float) (50 + Math.random() * 230)));
				rate = baseRate / 2f;
			}
			else if (random > 0.275f) {
				Game.instance().addEntity(new EntityJet(Display.getWidth() + 24f, (float) (Display.getHeight() / 2f + 50 + Math.random() * 239f)));
				rate = baseRate / 4f;
			}
			else if (random > 0.05) {
				Game.instance().addEntity(new EntityEnemyFish(Display.getWidth() + 24f, (float) (50 + Math.random() * 239f)));
				rate = baseRate / 4f;
			}
		}
	}
}
