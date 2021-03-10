package com.sirkarpfen.engineTester;

import org.lwjgl.opengl.Display;

import com.sirkarpfen.models.RawModel;
import com.sirkarpfen.models.TexturedModel;
import com.sirkarpfen.renderEngine.DisplayManager;
import com.sirkarpfen.renderEngine.Loader;
import com.sirkarpfen.renderEngine.Renderer;
import com.sirkarpfen.shaders.StaticShader;
import com.sirkarpfen.textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		StaticShader shader = new StaticShader();
		
		//OpenGL expects vertices to be defined counter clockwise by default
		float[] vertices = {
				-0.5f, 0.5f, 0,		//V0
				-0.5f, -0.5f, 0,	//V1
				0.5f, -0.5f, 0,		//V2
				0.5f, 0.5f, 0		//V3
		};
		
		int[] indices = {
				0,1,3,	//Top left triangle (V0,V1,V3)
				3,1,2	//Bottom right triangle (V3,V1,V2)
		};
		
		float[] textureCoords = {
				0,0,	//V0
				0,1,	//V1
				1,1,	//V2
				1,0		//V3
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("sample"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		while(!Display.isCloseRequested()) {
			renderer.prepare();
			
			//game logic
			shader.start();
			renderer.render(texturedModel);
			shader.stop();
			DisplayManager.updateDisplay();
			
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
