package Games;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import Implementations.ImplementationAbstract;
import Implementations.LWJGLImplementation;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Circle;
import Structs.Shape;
import Structs.Vector2;
import Structs.Vector4;


public class MainGame implements GameInterface{

	public MainGame(){}
	
	private PhysicsSystem physicsSystem;
	
	private static ImplementationAbstract imp;
	
	public void setup(){
		physicsSystem = new PhysicsSystem();
		PhysicsObject star = new PhysicsObject(new Vector2(-500, 0), 1000);
		PhysicsObject planet = new PhysicsObject(new Vector2(-4000, 0), new Vector2(0, -0.3), 200, 200);
		PhysicsObject moon = new PhysicsObject(new Vector2(-4500, 0), new Vector2(), 2, 2);
		PhysicsObject planet2 = new PhysicsObject(new Vector2(4000,0), 200);
		PhysicsObject moon2 = new PhysicsObject(new Vector2(4500, 0), new Vector2(), 2, 2);
		planet.velocity = physicsSystem.velocityForCircularMotion(planet, star, false);
		moon.velocity = physicsSystem.velocityForCircularMotion(moon, planet, true);
		planet2.velocity = physicsSystem.velocityForCircularMotion(planet2, star, false);
		moon2.velocity = physicsSystem.velocityForCircularMotion(moon2, planet2, true);
		
		physicsSystem.addObj(star);
		physicsSystem.addObj(planet);
		physicsSystem.addObj(moon);
		physicsSystem.addObj(planet2);
		physicsSystem.addObj(moon2);
		
		
		
		
		/*
		int a = 1;
		int b = 1;
		for(int x = 0; x < 20; x++){
			for(int i = 0; i < 20; i++){
				
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
		}
		*/
		
	}
	
	int mode = 0;
	double zoom = 1;
	double zoomSpeed = 0.001;
	
	boolean showTrajectories = true;
	
	public void update(int frameNum, double deltaTime){
		physicsSystem.update(frameNum);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			physicsSystem.getChar().gravitationalMass += 0.01;
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && physicsSystem.getChar().gravitationalMass > 0.01)
			physicsSystem.getChar().gravitationalMass -= 0.01;
		if(Keyboard.isKeyDown(Keyboard.KEY_E))
			physicsSystem.getChar().gravitationalMass += 0.1;
		if(Keyboard.isKeyDown(Keyboard.KEY_D) && physicsSystem.getChar().gravitationalMass > 0.1)
			physicsSystem.getChar().gravitationalMass -= 0.1;
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
			physicsSystem.getChar().gravitationalMass += 1;
		if(Keyboard.isKeyDown(Keyboard.KEY_A) && physicsSystem.getChar().gravitationalMass > 1)
			physicsSystem.getChar().gravitationalMass -= 1;
		if(Keyboard.isKeyDown(Keyboard.KEY_UP))
			physicsSystem.getChar().updateAcceleration(0, 0.001);
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			physicsSystem.getChar().updateAcceleration(0, -0.001);
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			physicsSystem.getChar().updateAcceleration(-0.001, 0);
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			physicsSystem.getChar().updateAcceleration(0.001, 0);
		if(Keyboard.isKeyDown(Keyboard.KEY_F))
			imp.keepUpdating = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_R))
			this.setup();
		if(Keyboard.isKeyDown(Keyboard.KEY_0)){
			change(0);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_1))
			change(1);
		if(Keyboard.isKeyDown(Keyboard.KEY_2))
			change(2);
		if(Keyboard.isKeyDown(Keyboard.KEY_3))
			change(3);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_C))
			mode = 1;
		if(Keyboard.isKeyDown(Keyboard.KEY_P))
			mode = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_L))
			zoom -= zoomSpeed;
		if(Keyboard.isKeyDown(Keyboard.KEY_K))
			zoom += zoomSpeed;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_T))
			showTrajectories = true;
		if(Keyboard.isKeyDown(Keyboard.KEY_Y))
			showTrajectories = false;
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
			position.subtract(physicsSystem.getCenter().position);
			position.multiply(zoom);
			shapes.add(new Circle(position, o.getRadius() * zoom));
			if(i == physicsSystem.charNum)
				shapes.get(i).color = new Vector4(0, 1, 0, 1);
			//break;
		}
		/*
		int i = 0;
		for(PhysicsObject a : physicsSystem.calculateTrajectory(physicsSystem.getChar(), 10000)){
			if(i % 50 == 0 && i > 50){
				Shape s = new Circle(a.getPosition(), a.getRadius());
				s.color = new Vector4(0, 0.5, 0, 0.5);
				shapes.add(s);
			}
			i++;
		}*/
		if(showTrajectories){
			for(PhysicsObject b : physicsSystem.objects){
				int i = 0;
				for(PhysicsObject a : physicsSystem.calculateTrajectory(b, 5000)){
					if(i % 500 == 0 && i > 0){
						Vector2 position = a.getPosition().copy();
						position.subtract(physicsSystem.getCenter().position);
						position.multiply(zoom);
						Shape s = new Circle(position, a.getRadius() / 2);
						if(b == physicsSystem.getChar())
							s.color = new Vector4(0, 0.4, 0, 1);
						else
							s.color = new Vector4(0.5, 0.5, 0.5, 0.2);
						shapes.add(s);
					}
					i++;
				}
			}
		}
		return shapes;
	}
	public static void main(String[] arg){
		imp = new LWJGLImplementation(new MainGame(), new Vector2(1280, 800), 0.2);
		imp.beginUpdating();
	}
}
