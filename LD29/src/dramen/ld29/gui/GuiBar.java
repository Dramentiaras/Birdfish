package dramen.ld29.gui;

public class GuiBar extends GuiObject {
	
	protected float value = 1f;
	
	public GuiBar(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public void setValue(float value) {
		
		this.value = value;
	}
	
	@Override
	public void update() {
		
		if (value > 1f) value = 1f;
		if (value < 0) value = 0;
		
		super.update();
	}
}
