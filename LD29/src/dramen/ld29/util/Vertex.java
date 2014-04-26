package dramen.ld29.util;


public class Vertex {

	protected float[] xy = new float[] {0f, 0f};
	protected float[] rgba = new float[] {1f, 1f, 1f, 1f};
	private float[] st = new float[] {0f, 0f};
	
	public static int elementBytes = 4;
	
	public static int positionElementCount = 2;
	public static int colorElementCount = 4;
	public static int textureElementCount = 2;
	
	public static int positionBytesCount = positionElementCount * elementBytes;
	public static int colorByteCount = colorElementCount * elementBytes;
	public static int textureByteCount = textureElementCount * elementBytes;
	
	public static int positionByteOffset = 0;
	public static int colorByteOffset = positionByteOffset + positionBytesCount;
	public static int textureByteOffset = colorByteOffset + colorByteCount;
	
	public static int elementCount = positionElementCount + 
			colorElementCount + textureElementCount;	

	public static int stride = positionBytesCount + colorByteCount + 
			textureByteCount;
	
	public void setXY(float x, float y) {
		xy = new float[] {x, y};
	}
	
	public void setRGB(float r, float g, float b) {
		setRGBA(r, g, b, 1f);
	}
	
	public void setRGBA(float r, float g, float b, float a) {
		this.rgba = new float[] {r, g, b, a};
	}
	
	public float[] getXY() {
		return xy;
	}
	
	public float[] getRGBA() {
		return rgba;
	}

	public void setST(float s, float t) {
		this.st = new float[] {s, t};
	}
	
	public float[] getST() {
		return new float[] {this.st[0], this.st[1]};
	}

	public float[] getElements() {
		float[] out = new float[Vertex.elementCount];
		int i = 0;
			
		out[i++] = this.xy[0];
		out[i++] = this.xy[1];

		out[i++] = this.rgba[0];
		out[i++] = this.rgba[1];
		out[i++] = this.rgba[2];
		out[i++] = this.rgba[3];

		out[i++] = this.st[0];
		out[i++] = this.st[1];
			
		return out;
	}
}
