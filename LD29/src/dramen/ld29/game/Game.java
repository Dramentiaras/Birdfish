package dramen.ld29.game;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.lwjgl.input.Controllers;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import dramen.ld29.entity.Entity;
import dramen.ld29.entity.EntityBird;
import dramen.ld29.entity.EntityFish;
import dramen.ld29.entity.EntityPlayer;
import dramen.ld29.gui.GuiBar;
import dramen.ld29.gui.GuiLabel;
import dramen.ld29.gui.GuiObject;
import dramen.ld29.input.Input;
import dramen.ld29.render.FontRenderer;
import dramen.ld29.render.RenderEngine;
import dramen.ld29.texture.TextureLibrary;
import dramen.ld29.util.Time;

public class Game {

	private static Game instance;
	private ArrayList<Entity> entities;
	private ArrayList<Entity> additions;
	private ArrayList<Entity> removals;
	private EntityPlayer playerEntity;
	private Player player;
	
	private boolean running;
	
	public ArrayList<GuiObject> objects;
	private GuiBar waterBar;
	private GuiBar airBar;
	
	public int score;
	
	public boolean mainmenu;
	public boolean paused;
	public boolean gameover;
	
	private Spawner spawner;
	
	float bg1x, bg2x;
	
	public static void init() {
		
		new Game();
	}
	
	public ArrayList<Entity> getEntitiesInRadius(float x, float y, float radius) {
		
		ArrayList<Entity> result = new ArrayList<Entity>();
		
		for (Entity e : entities) {
			
			float xDif = e.x - x;
			float yDif = e.y - y;
			
			float dist = (float) Math.sqrt(xDif * xDif + yDif * yDif);
			
			if (dist <= radius) {
				
				result.add(e);
			}
		}
		
		return result;
	}
	
	private Game() {
		
		instance = this;
		Time.init();
		
		bg1x = 512f;
		bg2x = 1024f + 512f;
		
		TextureLibrary.load("textures/set0.png");
		TextureLibrary.load("textures/font.png");
		TextureLibrary.load("textures/background.png");
		
		spawner = new Spawner(4f);
		
		entities = new ArrayList<Entity>();
		additions = new ArrayList<Entity>();
		removals = new ArrayList<Entity>();
		objects = new ArrayList<GuiObject>();
		player = new Player();
		playerEntity = new EntityBird(100f, 300f, player);
		player.setEntity(playerEntity);
		
		mainmenu = true;
		
		start();
	}
	
	public void startGame() {
		
		gameover = false;
		mainmenu = false;
		paused = false;
		
		score = 0;
		
		spawner = new Spawner(4f);
		
		entities = new ArrayList<Entity>();
		additions = new ArrayList<Entity>();
		removals = new ArrayList<Entity>();
		objects = new ArrayList<GuiObject>();
		player = new Player();
		playerEntity = new EntityBird(100f, 300f, player);
		player.setEntity(playerEntity);
		entities.add(playerEntity);
		waterBar = new GuiBar(35, 65, 40, 120) {
			@Override
			public void render() {
				
				RenderEngine.instance().renderTexturedQuad(x, y - 4 - 53f * (1f - value), 36f, 106f * value, 0f, 20, 51 + 53f * (1f - value), 18f, 53 * value, true, "set0");
				RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 0, 51, 20, 60, true, "set0");
			}
		};
		airBar = new GuiBar(Display.getWidth() - 35f, 65f, 40, 120) {
			
			@Override
			public void render() {
				
				RenderEngine.instance().renderTexturedQuad(x, y, width, height, 0f, 38f, 51f, 20f, 60f, true, "set0");
				RenderEngine.instance().renderTexturedQuad(x - 9f, y - 6f - 50 * (1f - value), 6f, 100 * value, 0f, 58f, 51f + 50 * (1f - value), 3f, 50 * value, true, "set0");
			}
		};
		
		objects.add(waterBar);
		objects.add(airBar);
		objects.add(new GuiLabel(5f, Display.getHeight() - 18f) {
			
			public void update() {
				
				setText("HP: " + ((int)player.health) + "/" + ((int)Player.MAX_HEALTH));
			};
		}.setSize(16f).setColor(1f, 0f, 0f).setText(""));
		objects.add(new GuiLabel(Display.getWidth() - FontRenderer.getStringWidth("Score: " + score, 16f) - 5f, Display.getHeight() - 18f) {
			
			public void update() {
				
				setText("Score: " + score);
				x = Display.getWidth() - FontRenderer.getStringWidth(text, size);
			}
		}.setSize(16f).setColor(1f, 1f, 0f).setText(""));
	}
	
	public static Game instance() {
		
		return instance;
	}
	
	public void start() {
		
		running = true;
		loop();
	}
	
	public void loop() {
		
		while (!Display.isCloseRequested() && running) 
		{
			
			update();
			render();
			
			Display.sync(60);
		}
		
		destroy();
	}
	
	public void stop() {
		
		running = false;
	}
	
	public void addEntity(Entity e) {
		
		additions.add(e);
	}
	
	public void addEntity(int index, Entity e) {
		
		additions.add(index, e);
	}
	
	public void removeEntity(Entity e) {
		
		removals.add(e);
	}
	
	public void reset() {
		
		entities.clear();
		additions.clear();
		removals.clear();
	}
	
	public void lose() {
		
		gameover = true;
		paused = false;
		mainmenu = false;
	}
	
	public void update() {
		
		Time.update();
		spawner.update();
		
		if (!mainmenu && !paused && !gameover) {
			for (int i = 0; i < entities.size(); i++) {
				
				Entity e = entities.get(i);
				
				e.update();
				
				for (int j = i + 1; j < entities.size(); j++) {
					
					Entity e1 = entities.get(j);
					
					Rectangle r0 = new Rectangle((int) (e.x - e.width / 2f), (int) (e.y - e.height / 2f), (int) e.width, (int) e.height);
					Rectangle r1 = new Rectangle((int) (e1.x - e1.width / 2f), (int) (e1.y - e1.height / 2f), (int) e1.width, (int) e1.height);
					
					if (r0.intersects(r1)) {
						
						e.onCollisionWithEntity(e1);
						e1.onCollisionWithEntity(e);
					}
				}
			}
			for (Entity e : removals) {
				
				entities.remove(e);
			}
			for (Entity e : additions) {
				
				entities.add(e);
			}
			
			additions.clear();
			removals.clear();
			
			player.update();
			
			if (playerEntity instanceof EntityFish && playerEntity.y < 300) {
				
				entities.remove(playerEntity);
				float yMotion = playerEntity.yMotion;
				playerEntity = new EntityBird(playerEntity.x, playerEntity.y, player);
				playerEntity.yMotion = yMotion;
				player.setEntity(playerEntity);
				entities.add(playerEntity);
			}
			else if (playerEntity instanceof EntityBird && playerEntity.y > 300) {
				
				entities.remove(playerEntity);
				float yMotion = playerEntity.yMotion;
				playerEntity = new EntityFish(playerEntity.x, playerEntity.y, player);
				playerEntity.yMotion = yMotion;
				player.setEntity(playerEntity);
				entities.add(playerEntity);
			}
			
			for (GuiObject o : objects) {
				
				o.update();
			}
			
			waterBar.setValue(player.water / Player.MAX_WATER);
			airBar.setValue(player.air / Player.MAX_AIR);
		}
		
		if (mainmenu) {
			
			if (Input.keyReleased(Keyboard.KEY_Z)) {
				startGame();
			}
			if (Input.keyReleased(Keyboard.KEY_X)) {
				running = false;
			}
		}
		else if (paused) {
			
			if (Input.keyReleased(Keyboard.KEY_X)) {
				paused = false;
				gameover = false;
				mainmenu = true;
				reset();
			}
		}
		else if (gameover) {
			
			if (Input.keyReleased(Keyboard.KEY_Z)) {
				startGame();
			}
			if (Input.keyReleased(Keyboard.KEY_X)) {
				paused = false;
				gameover = false;
				mainmenu = true;
				reset();
			}
		}
		
		if (Input.keyReleased(Keyboard.KEY_ESCAPE) && !mainmenu && !gameover) {
			paused = !paused;
		}
		
		if (!gameover && !paused) {
			bg1x -= 360f * Time.delta();
			bg2x -= 360f * Time.delta();
		}
		
		if (bg1x < -512f) bg1x = bg2x + 1024f;
		if (bg2x < -512f) bg2x = bg1x + 1024f;
		
		if (score >= 500) {
			spawner.setBaseRate(4f - ((float)((score >= 6500 ? 6000:score) / 1000)) / 2f);
		}
		
		Input.update();
	}
	
	public void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		RenderEngine.instance().renderTexturedQuad(bg1x, 512f, 1024, 1024, 0f, "background");
		RenderEngine.instance().renderTexturedQuad(bg2x, 512f, 1024, 1024, 0f, "background");
		
		for (Entity e : entities) {
			
			e.render();
		}
		
		RenderEngine.instance().renderQuad(400, 150, 800, 300, 0f, 0.15f, 1f, 0.25f);
		RenderEngine.instance().renderQuad(400, 300, 800, 3);
		
		if (!mainmenu) {
			for (GuiObject o : objects) {

				o.render();
			}
		}
		
		if (mainmenu) {
			
			FontRenderer.setSize(48f);
			FontRenderer.setColor(1f, 1f, 0f);
			FontRenderer.renderString(Display.getWidth() / 2f - FontRenderer.getStringWidth("Birdfish") / 2f, Display.getHeight() - 100f, "Birdfish");
			
			String text = "Press [Z] to start or [X] to quit!";
			
			FontRenderer.setSize(16f);
			FontRenderer.setColor(1f, 1f, 1f);
			FontRenderer.renderString(Display.getWidth() / 2f - FontRenderer.getStringWidth(text) / 2f, Display.getHeight() - 200f, text);
		}
		
		if (paused) {
			
			FontRenderer.setSize(48f);
			FontRenderer.setColor(1f, 1f, 0f);
			FontRenderer.renderString(Display.getWidth() / 2f - FontRenderer.getStringWidth("Paused") / 2f, Display.getHeight() - 100f, "Paused");
			
			String text = "Press [ESC] to start or [X] to return to menu!";
			
			FontRenderer.setSize(16f);
			FontRenderer.setColor(1f, 1f, 1f);
			FontRenderer.renderString(Display.getWidth() / 2f - FontRenderer.getStringWidth(text) / 2f, Display.getHeight() - 200f, text);
		}
		
		if (gameover) {
			
			FontRenderer.setSize(48f);
			FontRenderer.setColor(1f, 0f, 0f);
			FontRenderer.renderString(Display.getWidth() / 2f - FontRenderer.getStringWidth("Game Over!") / 2f, Display.getHeight() - 100f, "Game Over!");
			
			String text = "Press [Z] to restart or [X] to return to menu!";
			
			FontRenderer.setSize(16f);
			FontRenderer.setColor(1f, 1f, 1f);
			FontRenderer.renderString(Display.getWidth() / 2f - FontRenderer.getStringWidth(text) / 2f, Display.getHeight() - 200f, text);
			
			text = "You got a score of: " + score;
			
			FontRenderer.setSize(32f);
			FontRenderer.setColor(1f, 1f, 0f);
			FontRenderer.renderString(Display.getWidth() / 2f - FontRenderer.getStringWidth(text) / 2f, Display.getHeight() - 150f, text);
		}
		
		Display.update();
	}
	
	public void destroy() {
		
		RenderEngine.instance().destroy();
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
		Controllers.destroy();
	}
}
