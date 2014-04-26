package dramen.ld29.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import dramen.ld29.game.Game;
import dramen.ld29.game.Player;
import dramen.ld29.input.Input;
import dramen.ld29.util.Time;

public class EntityPlayer extends Entity {

	public Player player;
	
	public EntityPlayer(float x, float y, float width, float height, Player player) {
		
		super(x, y, width, height);
		this.player = player;
	}
	public static float MAX_SPEED = 560f;
	public static float ACCELERATION = 560f;
	public static float GRAVITY = 560f;
	
	public boolean firing = false;
	
	@Override
	public void update() {
		
		float delta = Time.delta();
		
		if (Input.keyDown(Keyboard.KEY_UP)) {
			if ((yMotion += ACCELERATION * delta) > MAX_SPEED) {
				
				yMotion = MAX_SPEED;
			}
		}
		else {
			
			if ((yMotion -= GRAVITY * delta) < -MAX_SPEED) {
				
				yMotion = -MAX_SPEED;
			}
		}
		
		if (Input.keyPressed(Keyboard.KEY_Z) && !firing) {
			
			fireBasic();
		}
		if (Input.keyPressed(Keyboard.KEY_X) && !firing) {
			
			fireSpecial();
		}
		
		super.update();
		
		if (y < height / 2f) {
			
			y = height / 2f;
			yMotion = 0f;
		}
		
		if (y > Display.getHeight() - height / 2f) {
			
			y = Display.getHeight() - height / 2f;
			yMotion = 0f;
		}
	}
	
	public void die() {
		
		Game.instance().removeEntity(this);
		Game.instance().lose();
	}
	
	public void fireBasic() {
		
	}
	
	public void fireSpecial() {
		
	}
}
