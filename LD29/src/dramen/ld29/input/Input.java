package dramen.ld29.input;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {

	private static boolean[] keys = new boolean[256];
	private static boolean[] buttons = new boolean[3];
	
	public static void update() {
		
		for (int i = 0; i < keys.length; i++) {
			
			keys[i] = Keyboard.isKeyDown(i);
		}
		
		for (int i = 0; i < buttons.length; i++) {
			
			buttons[i] = Mouse.isButtonDown(i);
		}
	}
	
	public static boolean keyPressed(int key) {
		
		return Keyboard.isKeyDown(key) && !keys[key];
	}
	
	public static boolean keyReleased(int key) {
		
		return !Keyboard.isKeyDown(key) && keys[key];
	}
	
	public static boolean keyDown(int key) {
		
		return Keyboard.isKeyDown(key);
	}
	
	public static boolean buttonPressed(int button) {
		
		return Mouse.isButtonDown(button) && !buttons[button];
	}
	
	public static boolean buttonReleased(int button) {
		
		return !Mouse.isButtonDown(button) && buttons[button];
	}
	
	public static boolean buttonDown(int button) {
		
		return Mouse.isButtonDown(button);
	}
	
	public static float mouseX() {
		
		return Mouse.getX();
	}
	
	public static float mouseY() {
		
		return Mouse.getY();
	}
}
