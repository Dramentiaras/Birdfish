package dramen.ld29.render;


public class FontRenderer {

	private static float r = 1f, g = 1f, b = 1f;
	private static float pt = 8f;
	
	public static void setColor(float r1, float g1, float b1) {
		
		r = r1;
		g = g1;
		b = b1;
	}
	
	public static void setSize(float size) {
		
		pt = size;
	}
	
	public static float getStringHeight() {
		
		float scale = pt / 8f;
		return scale * 8f;
	}
	
	public static float getStringHeight(float size) {
		
		float scale = size / 8f;
		return scale * 8f;
	}
	
	public static float getStringWidth(String text) {
		
		float scale = pt / 8f;
		return scale * 6f * text.length();
	}
	
	public static float getStringWidth(String text, float size) {
		
		float scale = size / 8f;
		return scale * 6f * text.length();
	}
	
	public static void renderString(float x, float y, String text) {
		
		float scale = pt / 8f;
		float width = 6f;
		float height = 8f;
		
		for (int i = 0; i < text.length(); i++) {
			
			int c = text.codePointAt(i) - 32;
			
			if (c >= 0 && c < 96) {
				
				float u = (float) ((c % 12f) * 6f);
				float v = (float) (Math.floor(c / 12f) * 8f);

				RenderEngine.instance().renderTexturedQuad(x + width * scale / 2f + i * width * scale, y + height * scale / 2f, width * scale, height * scale, 0f, r, g, b, 1f, u, v, 6f, 8f, true, "font");
			}
		}
	}
}
