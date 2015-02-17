package Games;
import Implementations.ImplementationAbstract;
import Implementations.LWJGLImplementation;
import Implementations.ProcessingImplementation;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Circle;
import Structs.Shape;
import Structs.Vector2;


public class MainGame implements GameInterface{

	public MainGame(){}
	
	private PhysicsSystem physicsSystem;
	
	public void setup(){
		physicsSystem = new PhysicsSystem();
		PhysicsObject star = new PhysicsObject(new Vector2(-500, 0), 100000);
		PhysicsObject planet = new PhysicsObject(new Vector2(-4000, 0), new Vector2(0, -0.3), 200, 200);
		PhysicsObject moon = new PhysicsObject(new Vector2(-4200, 0), new Vector2(), 40, 40);
		planet.velocity = physicsSystem.velocityForCircularMotion(planet, star, false);
		moon.velocity = physicsSystem.velocityForCircularMotion(moon, planet, true);
		physicsSystem.addObj(planet);
		physicsSystem.addObj(star);
		physicsSystem.addObj(moon);
		
		
		
		int a = 1;
		int b = 1;
		for(int x = 0; x < 10; x++){
			for(int i = 0; i < 10; i++){
				
				int mass = (int)(Math.random() * 20);
				if (mass == 0)
					mass = 1;
				physicsSystem.addObj(new PhysicsObject(
					new Vector2(1000 + x * 200 , i * 200 * a),
					new Vector2(0, 0),
					2,
					2
				));
				
				a*= -1;
			}
		}
		
	}
	
	public void update(int frameNum, double deltaTime){
		physicsSystem.update(frameNum);
	}
	
	public Shape[] drawShapes(){
		PhysicsObject[] objects = physicsSystem.getObj();
		Shape[] shapes = new Shape[objects.length];
		for(int i = 0; i < objects.length; i++){
			PhysicsObject o = objects[i];
			shapes[i] = new Circle(o.getPosition(), o.getRadius());
			//break;
		}
		return shapes;
	}
	public static void main(String[] arg){
		ImplementationAbstract imp = new LWJGLImplementation(new MainGame(), new Vector2(1280, 800), 0.2);
	}
}
