package Games;

import static org.lwjgl.opengl.GL11.glClear;

import java.util.UUID;

import obj.Gravitator;
import obj.Missile;
import obj.Station;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import Implementations.ImplementationAbstract;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Vector2;

public class DisplayManager {

	double zoom = 1;
	UUID centerObjectUUID;
	Vector2 centerDisplacement = new Vector2(-500, 0);
	
	public DisplayManager(){}
	
	public void draw(ImplementationAbstract imp, PhysicsSystem... physicsSystems){
		
		clearScreen();
		boolean first = true;
		for(PhysicsSystem physicsSystem : physicsSystems){
			if(first)
				drawPhysicsSystem(imp, physicsSystem, 1);
			else
				drawPhysicsSystem(imp, physicsSystem, 0.6);
			first = false;
		}
		updateDisplay();
	}
	
	public void drawPhysicsSystem(ImplementationAbstract imp, PhysicsSystem system, double alpha){
		
		imp.loadIdentity();
		GL11.glScaled(zoom, zoom, 1);
		updateCenterCache(system);
		GL11.glTranslated(-centerCache.x, -centerCache.y, 1);
		
		for(PhysicsObject object : system.getObj()){
			//TODO we need an actual color-coding system
			//maybe a map between object uuids and colors or types of objects and colors

			if(object instanceof Gravitator)//.getUUID().equals(MainGame.mainGame.gravUUID))
				GL11.glColor3d(0, 0.5, 0);
			else if(object instanceof Missile)
				GL11.glColor3d(.5 * alpha, .5 * alpha, 0.5 * alpha);
			else if(object instanceof Station)
				GL11.glColor3d(0 * alpha, 0.2 * alpha, 0.8 * alpha);
			else
				GL11.glColor3d(1 * alpha, 1 * alpha, 1 * alpha);
			object.draw();
		}
	}
	
	private Vector2 centerCache = new Vector2();
	private void updateCenterCache(PhysicsSystem physicsSystem){
		PhysicsObject centerObject = physicsSystem.getObject(centerObjectUUID);
		if(centerObject != null)
			centerCache.set(centerObject.getPosition());
		else
			centerCache.set(0, 0);
		
		centerCache.add(centerDisplacement);
	}
	
	public void setCenterObject(UUID uuid){
		centerObjectUUID = uuid;
	}
	public void setCenterObject(PhysicsObject p){
		centerObjectUUID = p.getUUID();
	}
	public void setCenter(double x, double y){
		centerDisplacement.set(x, y);
	}
	public void moveCenter(double x, double y){
		centerDisplacement.add(x, y);
	}
	public void moveCenterBasedOnZoom(double x, double y){
		centerDisplacement.add(x / zoom, y / zoom);
	}
	public void setZoom(double z){
		zoom = z;
	}
	public void multiplyZoom(double z){
		zoom *= z;
	}
	public void clearScreen(){
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
	}
	public void updateDisplay(){
		Display.update();
	}
}
