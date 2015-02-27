package Games;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import obj.Gravitator;
import obj.Missile;
import obj.Station;

import org.lwjgl.input.Keyboard;

import Implementations.ImplementationAbstract;
import Implementations.LWJGLImplementation;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Circle;
import Structs.Shape;
import Structs.Vector2;
import Structs.Vector4;
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
		
		PhysicsObject star = new PhysicsObject(new Vector2(-500, 0), 1000);
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
		
		/*
		int a = 1;
		int b = 1;
		for(int x = 0; x < 5; x++){
			for(int i = 0; i < 5; i++){
				
				int mass = (int)(Math.random() * 20);
				if (mass == 0)
					mass = 1;
				PhysicsObject o = (new PhysicsObject(
					new Vector2(1000 + x * 200 , i * 200 * a),
					new Vector2(0, 0),
					mass,
					mass
				));
				o.velocity = physicsSystem.velocityForCircularMotion(o, star, true);
				physicsSystem.addObj(o);
				
				a*= -1;
			}
		}*/
		
	}
	
	int mode = 0;
	double zoom = 1;
	double zoomSpeed = 0.001;
	
	int trajectoryMode = 0;

	int shootSpeed = 10;
	int moveSpeed = 15;
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
			if(e.key == Keyboard.KEY_0){
				change(0);
			}
			if(e.key == Keyboard.KEY_1)
				change(1);
			if(e.key == Keyboard.KEY_2)
				change(2);
			if(e.key == Keyboard.KEY_3)
				change(3);
			if(e.key == Keyboard.KEY_4)
				change(4);
			if(e.key == Keyboard.KEY_5)
				change(5);
			if(e.key == Keyboard.KEY_C)
				mode = 1;
			if(e.key == Keyboard.KEY_P)
				mode = 0;
			
			if(e.key == Keyboard.KEY_V)
				zoom -= zoomSpeed;
			if(e.key == Keyboard.KEY_B)
				zoom += zoomSpeed;
			if(e.key == Keyboard.KEY_N)
				zoom -= zoomSpeed * 10;
			if(e.key == Keyboard.KEY_M)
				zoom += zoomSpeed * 10;
			if(e.key == Keyboard.KEY_I)
				physicsSystem.center.add(0, moveSpeed);
			if(e.key == Keyboard.KEY_K)
				physicsSystem.center.add(0, -moveSpeed);
			if(e.key == Keyboard.KEY_J)
				physicsSystem.center.add(-moveSpeed, 0);
			if(e.key == Keyboard.KEY_L)
				physicsSystem.center.add(moveSpeed, 0);
			if(e.key == Keyboard.KEY_T)
				trajectoryMode = 0;
			if(e.key == Keyboard.KEY_U)
				trajectoryMode = 1;
			//if(e.key == Keyboard.KEY_U)
			//	trajectoryMode = 2;
		}
		
		else if(e.eventType == KeyEventType.HOLD){
			
		}
	}
	Map<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
	
	public void update(int frameNum, double deltaTime){
		//Gravitator grav = physicsSystem.getGravitator();
		if(trajectoryMode == 1){
			physicsSystem.update(frameNum);
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
	private void change(int num){
		if(mode == 0)
			physicsSystem.charNum = num;
		else if(mode == 1)
			physicsSystem.centerNum = num;
	}
	public List<Shape> drawShapes(){
		List<PhysicsObject> objects = physicsSystem.getObj();
		List<Shape> shapes = new ArrayList<Shape>(objects.size() + 10);
		
		for(int i = 0; i < objects.size(); i++){
			PhysicsObject o = objects.get(i);
			Vector2 position = o.getPosition().copy();
			position.subtract(physicsSystem.center);
			position.multiply(zoom);
			shapes.add(new Circle(position, o.getRadius() * zoom));
			if(i == physicsSystem.charNum)
				shapes.get(i).color = new Vector4(0, 1, 0, 1);
			//break;
		}
		//if(trajectoryMode == 1){
		//}
		if(trajectoryMode == 0)
			shapes.addAll(physicsSystem.calculateTrajectory(5000, 100, zoom));
		return shapes;
	}
	public static void main(String[] args){
		imp = new LWJGLImplementation(new MainGame(), new Vector2(1280, 800), 0.2);
		imp.beginUpdating();
	}
}
