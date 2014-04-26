package dramen.ld29.texture;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class TextureLibrary {

	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static void load(String path) {
		
		ByteBuffer buf = null;
		int width = 0;
		int height = 0;
		Texture texture;
		
		try {
			
			InputStream in = ClassLoader.getSystemResourceAsStream(path);
			PNGDecoder decoder = new PNGDecoder(in);
			
			width = decoder.getWidth();
			height = decoder.getHeight();
			
			Format format = decoder.decideTextureFormat(Format.RGBA);
			
			buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, format);
			buf.flip();
			
			in.close();
		}
		catch (Exception ex) {
			
			System.err.println("ERROR in loading texture: " + path);
			ex.printStackTrace();
		}
		
		int id = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		
		int par0 = path.lastIndexOf("/");
		
		String name = par0 == -1 ? path:(path.substring(par0 + 1));
		name = name.substring(0, name.lastIndexOf("."));
		
		texture = new Texture(id, width, height);
		
		textures.put(name, texture);
		System.out.println("Loaded texture with name: " + name);
	}
	
	public static Texture get(String name) {
		
		return textures.get(name);
	}
	
	public static void destroy() {
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		for (String s : textures.keySet()) {
			
			GL11.glDeleteTextures(textures.get(s).getTextureID());
		}
	}
}
