package dramen.ld29.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.lwjgl.opengl.GL20;

public class ShaderLoader {

	public static int loadShader(String file, int type) {
		
		StringBuilder src = new StringBuilder();
		int shaderID = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(file)));
			String line;
			while ((line = reader.readLine()) != null) {
				src.append(line).append("\n");
			}
			reader.close();
		}
		catch (Exception ex) {
			
			ex.printStackTrace();
			System.exit(-1);
		}
		
		shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, src);
		GL20.glCompileShader(shaderID);
		
		return shaderID;
	}
}
