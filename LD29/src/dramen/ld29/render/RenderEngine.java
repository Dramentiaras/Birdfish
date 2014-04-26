package dramen.ld29.render;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import dramen.ld29.texture.Texture;
import dramen.ld29.texture.TextureLibrary;
import dramen.ld29.util.ShaderLoader;
import dramen.ld29.util.Vertex;

public class RenderEngine {

	private static RenderEngine instance;
	
	private int vaoID = 0;
	private int vboID = 0;
	private int vboiID = 0;
	
	private int currentProgram = -1;
	
	private int textureProgramID, programID,
		tfsID, tvsID, fsID, vsID;
	
	private int modelLocation, projectionLocation;
	private Matrix4f model, projection;
	private FloatBuffer matrix44Buffer;
	
	private Vertex v0, v1, v2, v3;
	
	public static RenderEngine instance() {
		return instance;
	}
	
	public static void init() {
		
		instance = new RenderEngine();
	}
	
	public void renderQuad(float x, float y, float width, float height) {
		
		renderQuad(x, y, width, height, 1f, 1f, 1f, 1f);
	}
	
	public void renderQuad(float x, float y, float width, float height, float r, float g, float b, float a) {
		
		renderQuad(x, y, width, height, 0f, r, g, b, a);
	}
	
	public void renderQuad(float x, float y, float width, float height, float rotation, float r, float g, float b, float a) {
		
		if (currentProgram != programID) {
		
			GL20.glUseProgram(programID);
			currentProgram = programID;
		}
		
		v0.setRGBA(r, g, b, a); v1.setRGBA(r, g, b, a); v2.setRGBA(r, g, b, a); v3.setRGBA(r, g, b, a);
		
		x = Math.round(x);
		y = Math.round(y);
		width = Math.round(width);
		height = Math.round(height);
		
		Vertex[] vertices = new Vertex[] {v0, v1, v2, v3};
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.elementCount);
		for (int i = 0; i < vertices.length; i++) {
			vertexBuffer.put(vertices[i].getElements());
		}
		vertexBuffer.flip();
		
		vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, Vertex.positionElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, Vertex.colorElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
		GL20.glVertexAttribPointer(2, Vertex.textureElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.textureByteOffset);
		
		model.translate(new Vector2f(x, y));
		model.rotate(rotation, new Vector3f(0f, 0f, 1f));
		model.scale(new Vector3f(width, height, 1f));
		
		projection.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(projectionLocation, false, matrix44Buffer);
		model.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(modelLocation, false, matrix44Buffer);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		model = new Matrix4f();
	}
	
	public void renderTexturedQuad(float x, float y, float width, float height, String texture) {
		
		renderTexturedQuad(x, y, width, height, 0f, texture);
	}
	
	public void renderTexturedQuad(float x, float y, float width, float height, float rotation, String texture) {
		
		renderTexturedQuad(x, y, width, height, rotation, 0f, 0f, 1f, 1f, false, texture);
	}
	
	public void renderTexturedQuad(float x, float y, float width, float height, float rotation,
			float u, float v, float twidth, float theight, boolean normalize, String texture) {
		
		renderTexturedQuad(x, y, width, height, rotation, 1f, 1f, 1f, 1f, u, v, twidth, theight, normalize, texture);
	}
	
	public void renderTexturedQuad(float x, float y, float width, float height, float rotation, float r, float g, float b, float a,
			float u, float v, float twidth, float theight, boolean normalize, String texture) {
		
		if (currentProgram != textureProgramID) {
			
			GL20.glUseProgram(textureProgramID);
			currentProgram = textureProgramID;
		}
		
		Texture tex = TextureLibrary.get(texture);
		
		if (normalize) {
			u /= tex.getTextureWidth();
			v /= tex.getTextureHeight();
			twidth /= tex.getTextureWidth();
			theight /= tex.getTextureHeight();
		}
		
		v0.setRGBA(r, g, b, a); v1.setRGBA(r, g, b, a); v2.setRGBA(r, g, b, a); v3.setRGBA(r, g, b, a);
		v0.setST(u, v); v1.setST(u, v + theight); v2.setST(u + twidth, v + theight); v3.setST(u + twidth, v);
		
		Vertex[] vertices = new Vertex[] {v0, v1, v2, v3};
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.elementCount);
		for (int i = 0; i < vertices.length; i++) {
			vertexBuffer.put(vertices[i].getElements());
		}
		vertexBuffer.flip();
		
		vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, Vertex.positionElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, Vertex.colorElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
		GL20.glVertexAttribPointer(2, Vertex.textureElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.textureByteOffset);
		
		model.translate(new Vector2f(x, y));
		model.rotate(rotation, new Vector3f(0f, 0f, 1f));
		model.scale(new Vector3f(width, height, 1f));
		
		projection.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(projectionLocation, false, matrix44Buffer);
		model.store(matrix44Buffer); matrix44Buffer.flip();
		GL20.glUniformMatrix4(modelLocation, false, matrix44Buffer);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getTextureID());
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_BYTE, 0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		model = new Matrix4f();
	}
	
	public void destroy() {
		
		GL20.glUseProgram(0);
		GL20.glDetachShader(textureProgramID, tvsID);
		GL20.glDetachShader(textureProgramID, tfsID);
		
		GL20.glDeleteShader(tvsID);
		GL20.glDeleteShader(tfsID);
		GL20.glDeleteProgram(textureProgramID);
		
		GL30.glBindVertexArray(vaoID);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboID);
		GL15.glDeleteBuffers(vboiID);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoID);
		
		instance = null;
	}
	
	private RenderEngine() {
		
		model = new Matrix4f();
		projection = new Matrix4f();
		
		projection.m00 = 2f / Display.getWidth();
		projection.m11 = 2f / Display.getHeight();
		projection.m22 = 2f / (1f - -1f);
		projection.m30 = -Display.getWidth() / Display.getWidth();
		projection.m31 = -Display.getHeight() / Display.getHeight();
		projection.m32 = -(1f + -1f) / (1f - -1f);
		
		matrix44Buffer = BufferUtils.createFloatBuffer(16);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		instance = this;
		
		v0 = new Vertex(); v0.setXY(-0.5f, 0.5f); v0.setRGB(1f, 1f, 1f); v0.setST(0f, 0f);
		v1 = new Vertex(); v1.setXY(-0.5f, -0.5f); v1.setRGB(1f, 1f, 1f); v1.setST(0f, 1f);
		v2 = new Vertex(); v2.setXY(0.5f, -0.5f); v2.setRGB(1f, 1f, 1f); v2.setST(1f, 1f);
		v3 = new Vertex(); v3.setXY(0.5f, 0.5f); v3.setRGB(1f, 1f, 1f); v3.setST(1f, 0f);
		
		Vertex[] vertices = new Vertex[] {v0, v1, v2, v3};
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.elementCount);
		for (int i = 0; i < vertices.length; i++) {
			
			vertexBuffer.put(vertices[i].getElements());
		}
		
		vertexBuffer.flip();
		
		vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		
		vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, Vertex.positionElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.positionByteOffset);
		GL20.glVertexAttribPointer(1, Vertex.colorElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.colorByteOffset);
		GL20.glVertexAttribPointer(2, Vertex.textureElementCount, GL11.GL_FLOAT, false, Vertex.stride, Vertex.textureByteOffset);
		
		GL30.glBindVertexArray(0);
		
		byte[] indices = {
			0, 1, 2,
			2, 3, 0
		};
		
		ByteBuffer indexBuffer = BufferUtils.createByteBuffer(indices.length);
		indexBuffer.put(indices);
		indexBuffer.flip();
		
		vboiID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboiID);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		tvsID = ShaderLoader.loadShader("shader/texture_vertex.glsl", GL20.GL_VERTEX_SHADER);
		tfsID = ShaderLoader.loadShader("shader/texture_fragment.glsl", GL20.GL_FRAGMENT_SHADER);
		
		vsID = ShaderLoader.loadShader("shader/vertex.glsl", GL20.GL_VERTEX_SHADER);
		fsID = ShaderLoader.loadShader("shader/fragment.glsl", GL20.GL_FRAGMENT_SHADER);
		
		textureProgramID = GL20.glCreateProgram();
		GL20.glAttachShader(textureProgramID, tvsID);
		GL20.glAttachShader(textureProgramID, tfsID);
		
		GL20.glBindAttribLocation(textureProgramID, 0, "in_Position");
		GL20.glBindAttribLocation(textureProgramID, 1, "in_Color");
		GL20.glBindAttribLocation(textureProgramID, 2, "in_TexCoord");
		
		GL20.glLinkProgram(textureProgramID);
		GL20.glValidateProgram(textureProgramID);
		
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vsID);
		GL20.glAttachShader(programID, fsID);
		
		GL20.glBindAttribLocation(programID, 0, "in_Position");
		GL20.glBindAttribLocation(programID, 1, "in_COlor");
		
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		modelLocation = GL20.glGetUniformLocation(textureProgramID, "model");
		projectionLocation = GL20.glGetUniformLocation(textureProgramID, "projection");
		
		GL11.glClearColor(178f / 255f, 244f / 255f, 255f / 255f, 1f);
		
		int error = GL11.glGetError();
		if (error != GL11.GL_NO_ERROR) {
			System.out.println("ERROR 0 - Could not instantiate render engine: " + GLU.gluErrorString(error));
			System.exit(-1);
		}
	}
}
