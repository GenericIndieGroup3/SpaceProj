package Games;
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
	
	public void update(int frameNum, double deltaTime){
		physicsSystem.update(frameNum);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
			physicsSystem.getChar().gravitationalMass += 0.01;
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && physicsSystem.getChar().gravitationalMass > 0.01)
			physicsSystem.getChar().gravitationalMass -= 0.01;
		if(Keyboard.isKeyDown(Keyboard.KEY_Q))
			imp.keepUpdating = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_R))
			this.setup();
	}
	
	public Shape[] drawShapes(){
		List<PhysicsObject> objects = physicsSystem.getObj();
		Shape[] shapes = new Shape[objects.size()];
		for(int i = 0; i < objects.size(); i++){
			PhysicsObject o = objects.get(i);
			shapes[i] = new Circle(o.getPosition(), o.getRadius());
			if(i == 2)
				shapes[i].color = new Vector4(0, 1, 0, 1);
			//break;
		}
		return shapes;
	}
	public static void main(String[] arg){
		imp = new LWJGLImplementation(new MainGame(), new Vector2(1280, 800), 0.2);
		imp.beginUpdating();
	}
}
