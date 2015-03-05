package Games;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import obj.Gravitator;
import obj.Station;

import org.lwjgl.input.Keyboard;

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

	public static MainGame mainGame;
	private PhysicsSystem physicsSystem;
	public static ImplementationAbstract imp;
	public EventDistributor<KeyEvent> keyPressEventDistributor;
	public DisplayManager displayManager;
	public GameController gameController;
	public PlayerController playerController;
	
	public UUID gravUUID = UUID.randomUUID();
	
	public int trajectoryMode = 0;
	
	public MainGame(){
		mainGame = this;
		
		keyPressEventDistributor = new EventDistributor<KeyEvent>();
		displayManager = new DisplayManager();
		gameController = new GameController(displayManager);
		playerController = new PlayerController(gravUUID);
		
		keyPressEventDistributor.addListener(this, EventPriority.LOW);
		keyPressEventDistributor.addListener(gameController, EventPriority.LOW);
		keyPressEventDistributor.addListener(playerController, EventPriority.LOW);
	}
	
	public void setup(){
		physicsSystem = new PhysicsSystem();
		activeSystem = physicsSystem;
	
		PhysicsObject star = new PhysicsObject(new Vector2(0, 0), 5000);
		PhysicsObject planet = new PhysicsObject(new Vector2(-4000, 0), new Vector2(0, -0.3), 50, 50);
		Gravitator moon = new Gravitator(new Vector2(-4500, 0), new Vector2(), 5, 5);
		PhysicsObject planet2 = new PhysicsObject(new Vector2(4000,0), 50);
		PhysicsObject moon2 = new PhysicsObject(new Vector2(4500, 0), new Vector2(), 5, 5);
		Station station = new Station(new Vector2(2000,0),50,star.getUUID(),true);
		
		moon.setUUID(gravUUID);

		Vector2 velocityCache = new Vector2();
		physicsSystem.calculateVelocityForCircularMotion(planet, star, false, velocityCache);
		planet.velocity.set(velocityCache);
		physicsSystem.calculateVelocityForCircularMotion(moon, planet, true, velocityCache);
		moon.velocity.set(velocityCache);
		physicsSystem.calculateVelocityForCircularMotion(planet2, star, false, velocityCache);
		planet2.velocity.set(velocityCache);
		physicsSystem.calculateVelocityForCircularMotion(moon2, planet2, true, velocityCache);
		moon2.velocity.set(velocityCache);
		
		physicsSystem.addObj(star);
		physicsSystem.addObj(planet);
		physicsSystem.addObj(moon);
		physicsSystem.addObj(planet2);
		physicsSystem.addObj(moon2);
		physicsSystem.addObj(station);
		
		displayManager.setCenterObject(star);
	}
	
	public void invoke(KeyEvent e){
		
		if(e.eventType == KeyEventType.PRESS){
			//if(e.key == Keyboard.KEY_U)
				//trajectoryMode = 0;
			if(e.key == Keyboard.KEY_T && trajectoryMode == 0){
				sandboxSystem = new PhysicsSystem(physicsSystem);
				activeSystem = sandboxSystem;
				trajectoryMode = 1;
			}
			if(e.key == Keyboard.KEY_RETURN && trajectoryMode == 1){
				physicsSystem = sandboxSystem;
				sandboxSystem = null;
				activeSystem = physicsSystem;
				trajectoryMode = 0;
			}
			if(e.key == Keyboard.KEY_ESCAPE && trajectoryMode == 1){
				activeSystem = physicsSystem;
				sandboxSystem = null;
				trajectoryMode = 0;
			}
			//if(e.key == Keyboard.KEY_Y)
				//trajectoryMode = 2;
		}
	}
	PhysicsSystem activeSystem;
	PhysicsSystem sandboxSystem;
	
	public void fireKeyEvents(){
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
	Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
	public int f = 0;
	
	public void update(int frameNum, double deltaTime){
		fireKeyEvents();
		f = frameNum;
		if(trajectoryMode == 0 && frameNum % 1 == 0){
			physicsSystem.update();
		}
	}

	public PhysicsSystem getActiveSystem(){
		return activeSystem;
	}
	
	private int trajectoryDistance = 2000;
	public void draw(){
		
		if(f % 1 == 0){
			displayManager.clearScreen();
			displayManager.drawPhysicsSystem(imp, activeSystem, 1);
			if(trajectoryMode == 1){
				PhysicsSystem copy = new PhysicsSystem(activeSystem);
				for(int i = 0; i < trajectoryDistance; i ++){
					copy.update();
					if(i % 150 == 0 && i != 0)
						displayManager.drawPhysicsSystem(imp, copy, 0.6);
				}
			}
			displayManager.updateDisplay();
		}
		
	}
	public static void main(String[] args){
		imp = new LWJGLImplementation(new MainGame(), new Vector2(1280, 800), 0.1);
		imp.beginUpdating();
	}
}
