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
	
	//once again, I don't know if this should be public or not, but it is currently used
	//in the lwjgl implementation for drawing
	
	public PhysicsSystem physicsSystem;
	
	public void setup(){
		physicsSystem = new PhysicsSystem();
		PhysicsObject star = new PhysicsObject(new Vector2(), 100);
		PhysicsObject planet = new PhysicsObject(new Vector2(-2100, 0), new Vector2(0, -0.3), 10, 10);
		PhysicsObject moon = new PhysicsObject(new Vector2(-2000, 0), new Vector2(), 2, 2);
		planet.velocity = physicsSystem.velocityForCircularMotion(planet, star, false);
		moon.velocity = physicsSystem.velocityForCircularMotion(moon, planet, true);
		physicsSystem.addObj(planet);
		physicsSystem.addObj(star);
		physicsSystem.addObj(moon);
	}
	
	public void update(int frameNum, double deltaTime){
		physicsSystem.update();
	}
	
	public Shape[] drawShapes(){
		PhysicsObject[] objects = physicsSystem.getObj();
		Shape[] shapes = new Shape[objects.length];
		for(int i = 0; i < objects.length; i++){
			PhysicsObject o = objects[i];
			shapes[i] = new Circle(o.getPosition(), o.getRadius());
		}
		return shapes;
	}
	public static void main(String[] arg){
		ImplementationAbstract imp = new ProcessingImplementation(new MainGame(), new Vector2(1500, 1000), 0.2);
	}
}
