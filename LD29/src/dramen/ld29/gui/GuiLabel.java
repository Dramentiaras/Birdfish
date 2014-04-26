package dramen.ld29.gui;

import dramen.ld29.render.FontRenderer;

public class GuiLabel extends GuiObject {

	protected String text;
	protected float size;
	protected float r, g, b;
	
	public GuiLabel(float x, float y) {
		super(x, y, 0f, 0f);
	}

	public GuiLabel setText(String text) {
		
		this.text = text;
		return this;
	}
	
	public GuiLabel setColor(float r, float g, float b) {
		
		this.r = r;
		this.g = g;
		this.b = b;
		return this;
	}
	
	public GuiLabel setSize(float size) {
		
		this.size = size;
		return this;
	}
	
	@Override
	public void render() {
		
		FontRenderer.setSize(size);
		FontRenderer.setColor(r, g, b);
		FontRenderer.renderString(x, y, text);
	}
}
