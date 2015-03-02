package Games;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import obj.Gravitator;
import obj.Missile;
import obj.Station;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import Implementations.ImplementationAbstract;
import Implementations.LWJGLImplementation;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Vector2;
import events.EventDistributor;
import events.EventPriority;
import events.Listener;
import events.types.KeyEvent;
import events.types.KeyEventType;


public class MainGame implements GameInterface, Listener<KeyEvent>{

	public MainGame(){}
	//TODO this is a bad
	public static PhysicsSystem physicsSystem;
	private static ImplementationAbstract imp;
	
	public EventDistributor<KeyEvent> keyPressEventDistributor = new EventDistributor<KeyEvent>();
	
	public void setup(){
		
		keyPressEventDistributor.addListener(this, EventPriority.LOW);
		
		
		PhysicsObject star = new PhysicsObject(new Vector2(0, 0), 10000);
		PhysicsObject planet = new PhysicsObject(new Vector2(-4000, 0), new Vector2(0, -0.3), 200, 200);
		Gravitator moon = new Gravitator(new Vector2(-4500, 0), new Vector2(), 2, 2);
		PhysicsObject planet2 = new PhysicsObject(new Vector2(4000,0), 200);
		PhysicsObject moon2 = new PhysicsObject(new Vector2(4500, 0), new Vector2(), 2, 2);
		Station station = new Station(new Vector2(2000,0),50,star,true);
		
		physicsSystem = new PhysicsSystem(moon);
		
		planet.velocity = physicsSystem.velocityForCircularMotion(planet, star, false);
		moon.velocity = physicsSystem.velocityForCircularMotion(moon, planet, true);
		//moon.accelerate(planet.velocity);
		planet2.velocity = physicsSystem.velocityForCircularMotion(planet2, star, false);
		moon2.velocity = physicsSystem.velocityForCircularMotion(moon2, planet2, true);
		//station.velocity = physicsSystem.velocityForCircularMotion(station, star, true);
		//moon2.accelerate(planet2.velocity);
		
		physicsSystem.addObj(star);
		physicsSystem.addObj(planet);
		physicsSystem.addObj(moon);
		physicsSystem.addObj(planet2);
		physicsSystem.addObj(moon2);
		physicsSystem.addObj(station);
		
	}
	
	int mode = 0;
	double zoom = 1;
	double zoomSpeed = 0.001;
	
	int trajectoryMode = 0;

	int shootSpeed = 10;
	int moveSpeed = 5;
	
	public void invoke(KeyEvent e){
		Gravitator grav = physicsSystem.getGravitator();
		if(e.eventType == KeyEventType.PRESS){
			if(e.key == Keyboard.KEY_LEFT){
				Missile missile = grav.shootMissile(1, 0.5, shootSpeed, new Vector2(-shootSpeed, 0));
				physicsSystem.addObj(missile);
			}
			if(e.key == Keyboard.KEY_DOWN){
				Missile missile = grav.shootMissile(1, 0.5, shootSpeed, new Vector2(0, -shootSpeed));
				physicsSystem.addObj(missile);
			}
			if(e.key == Keyboard.KEY_UP){
				Missile missile = grav.shootMissile(1, 0.5, shootSpeed, new Vector2(0, shootSpeed));
				physicsSystem.addObj(missile);
			}
			if(e.key == Keyboard.KEY_RIGHT){
				Missile missile = grav.shootMissile(1, 0.5, shootSpeed, new Vector2(shootSpeed, 0));
				physicsSystem.addObj(missile);
			}
			if(e.key == Keyboard.KEY_SPACE){
				Missile missile = grav.shootMissile(1, 0.5, shootSpeed, grav.velocity.copy());
				physicsSystem.addObj(missile);
			}
		}
		if(e.eventType == KeyEventType.HOLD){
			
			if(e.key == Keyboard.KEY_W)
				physicsSystem.getChar().gravitationalMass += 0.01;
			if(e.key == Keyboard.KEY_S && physicsSystem.getChar().gravitationalMass > 0.01)
				physicsSystem.getChar().gravitationalMass -= 0.01;
			if(e.key == Keyboard.KEY_E)
				physicsSystem.getChar().gravitationalMass += 0.1;
			if(e.key == Keyboard.KEY_D && physicsSystem.getChar().gravitationalMass > 0.1)
				physicsSystem.getChar().gravitationalMass -= 0.1;
			if(e.key == Keyboard.KEY_Q)
				physicsSystem.getChar().gravitationalMass += 1;
			if(e.key == Keyboard.KEY_A && physicsSystem.getChar().gravitationalMass > 1)
				physicsSystem.getChar().gravitationalMass -= 1;
			if(e.key == Keyboard.KEY_F)
				imp.keepUpdating = false;
			if(e.key == Keyboard.KEY_R)
				this.setup();

			if(e.key == Keyboard.KEY_V)
				zoom /= 1 + zoomSpeed;
			if(e.key == Keyboard.KEY_B)
				zoom *= 1 + zoomSpeed;
			if(e.key == Keyboard.KEY_N)
				zoom /= 1 + zoomSpeed * 10;
			if(e.key == Keyboard.KEY_M)
				zoom *= 1 + zoomSpeed * 10;
			if(e.key == Keyboard.KEY_I)
				physicsSystem.centerVector.add(0, moveSpeed / zoom);
			if(e.key == Keyboard.KEY_K)
				physicsSystem.centerVector.add(0, -moveSpeed / zoom);
			if(e.key == Keyboard.KEY_J)
				physicsSystem.centerVector.add(-moveSpeed / zoom, 0);
			if(e.key == Keyboard.KEY_L)
				physicsSystem.centerVector.add(moveSpeed / zoom, 0);
			if(e.key == Keyboard.KEY_U)
				trajectoryMode = 0;
			if(e.key == Keyboard.KEY_T)
				trajectoryMode = 1;
			if(e.key == Keyboard.KEY_Y)
				trajectoryMode = 2;
		}
		
		else if(e.eventType == KeyEventType.HOLD){
			
		}
	}
	Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
	
	public void update(int frameNum, double deltaTime){
		if(trajectoryMode == 0){
			physicsSystem.update();
		}
			
		while (Keyboard.next()) {
			int key = Keyboard.getEventKey();
		    if (Keyboard.getEventKeyState()){
		    	pressedKeys.put(key, true);
		        keyPressEventDistributor.invoke(new KeyEvent(key, KeyEventType.PRESS));
		    }
        	else{
        		pressedKeys.put(key, false);
		    	keyPressEventDistributor.invoke(new KeyEvent(key, KeyEventType.RELEASE));
        	}
		}
		for(Entry<Integer, Boolean> entry : pressedKeys.entrySet()){
			if(entry.getValue())
				keyPressEventDistributor.invoke(new KeyEvent(entry.getKey().intValue(), KeyEventType.HOLD));
		}
	}

	public void draw(){
		Vector2 center = physicsSystem.getCenter();
		
		imp.clear();
		GL11.glScaled(zoom, zoom, 1);
		GL11.glTranslated(-center.x, -center.y, 1);
		
		drawPhysicsSystem(physicsSystem);
		
		
		
		if(trajectoryMode == 1){
			PhysicsSystem copy = new PhysicsSystem(physicsSystem);
			for(int i = 0; i < 500; i ++){
				copy = new PhysicsSystem(copy);
				copy.update();
				if(i % 50 == 0)
					drawPhysicsSystem(copy);
			}
		}
	}
	public void drawPhysicsSystem(PhysicsSystem system){
		for(PhysicsObject object : system.getObj()){
			if(object == system.getGravitator())
				GL11.glColor3d(0, 1, 0);
			else if(object instanceof Missile)
				GL11.glColor3d(.5, .5, 0.5);
			else if(object instanceof Station)
				GL11.glColor3d(0, 0.2, 0.8);
			else
				GL11.glColor3d(1, 1, 1);
			object.draw();
		}
	}
	
	public static void main(String[] args){
		imp = new LWJGLImplementation(new MainGame(), new Vector2(1280, 800), 0.1);
		imp.beginUpdating();
	}
}
