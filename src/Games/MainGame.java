package Games;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import obj.Gravitator;
import obj.Missile;
import obj.Station;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
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
	
	private UUID gravUUID;
	
	public EventDistributor<KeyEvent> keyPressEventDistributor = new EventDistributor<KeyEvent>();
	
	public void setup(){
		
		keyPressEventDistributor.addListener(this, EventPriority.LOW);
		
		
		PhysicsObject star = new PhysicsObject(new Vector2(0, 0), 10000);
		PhysicsObject planet = new PhysicsObject(new Vector2(-4000, 0), new Vector2(0, -0.3), 200, 200);
		Gravitator moon = new Gravitator(new Vector2(-4500, 0), new Vector2(), 2, 2);
		PhysicsObject planet2 = new PhysicsObject(new Vector2(4000,0), 200);
		PhysicsObject moon2 = new PhysicsObject(new Vector2(4500, 0), new Vector2(), 2, 2);
		Station station = new Station(new Vector2(2000,0),50,star.getUUID(),true);
		
		gravUUID = moon.getUUID();
		physicsSystem = new PhysicsSystem();
		
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
		Gravitator grav = (Gravitator) physicsSystem.getObject(gravUUID);
		if(e.eventType == KeyEventType.PRESS){
			if(grav != null){
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
		}
		if(e.eventType == KeyEventType.HOLD){
			
			if(grav != null){
				if(e.key == Keyboard.KEY_W)
					grav.addMass(0.01);
				if(e.key == Keyboard.KEY_S)
					grav.addMass(-0.01);
				if(e.key == Keyboard.KEY_E)
					grav.addMass(0.1);
				if(e.key == Keyboard.KEY_D)
					grav.addMass(-0.1);
				if(e.key == Keyboard.KEY_Q)
					grav.addMass(1);
				if(e.key == Keyboard.KEY_A)
					grav.addMass(-1);
			}
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
	public int f = 0;
	public void update(int frameNum, double deltaTime){
		f = frameNum;
		if(trajectoryMode == 0 && frameNum % 1 == 0){
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
		
<<<<<<< Updated upstream
		if(f % 3 == 0){
			Vector2 center = physicsSystem.getCenter();
			
			imp.clear();
			GL11.glScaled(zoom, zoom, 1);
			GL11.glTranslated(-center.x, -center.y, 1);
			
			drawPhysicsSystem(physicsSystem);
			
			if(trajectoryMode == 1){
				PhysicsSystem copy = new PhysicsSystem(physicsSystem);
				for(int i = 0; i < 5000; i ++){
				
					copy.update();
					if(i % 200 == 0)
						drawPhysicsSystem(copy);
				}
=======
		imp.clear();
		GL11.glScaled(zoom, zoom, 1);
		GL11.glTranslated(-center.x, -center.y, 1);
		
		drawPhysicsSystem(physicsSystem);
		
		
		
		if(trajectoryMode == 1){
			PhysicsSystem copy = new PhysicsSystem(physicsSystem);
			for(int i = 0; i < 500; i ++){
			//	copy = new PhysicsSystem(copy);
				copy.update();
				if(i % 50 == 0)
					drawPhysicsSystem(copy);
>>>>>>> Stashed changes
			}
			Display.update();
		}
	}
	public void drawPhysicsSystem(PhysicsSystem system){
		for(PhysicsObject object : system.getObj()){
			if(object.getUUID().equals(gravUUID))
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
