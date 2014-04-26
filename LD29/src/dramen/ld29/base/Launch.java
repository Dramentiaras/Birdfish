package dramen.ld29.base;

import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import dramen.ld29.game.Game;
import dramen.ld29.render.RenderEngine;

public class Launch {

	public static void main(String[] args) {
		
		new Launch();
	}
	
	public Launch() {
		
		try {
			
			PixelFormat format = new PixelFormat();
			ContextAttribs contextAttribs = new ContextAttribs(3, 2)
					.withForwardCompatible(true).withProfileCore(true);
			
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("Birdfish");
			Display.create(format, contextAttribs);
			Mouse.create();
			Keyboard.create();
			Controllers.create();
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		RenderEngine.init();
		Game.init();
	}
}
