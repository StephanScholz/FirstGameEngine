package com.sirkarpfen.engineTester;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import com.sirkarpfen.entities.Camera;
import com.sirkarpfen.entities.Entity;
import com.sirkarpfen.entities.Light;
import com.sirkarpfen.models.RawModel;
import com.sirkarpfen.models.TexturedModel;
import com.sirkarpfen.renderEngine.DisplayManager;
import com.sirkarpfen.renderEngine.Loader;
import com.sirkarpfen.renderEngine.MasterRenderer;
import com.sirkarpfen.renderEngine.OBJLoader;
import com.sirkarpfen.textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("dragon", loader);
		
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("dragonTexture")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel, new Vector3f(0,-2.5f,-25),0,0,0,1);
		Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
		
		Camera camera = new Camera();
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()) {
			//entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}

}
