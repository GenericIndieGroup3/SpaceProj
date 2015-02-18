package Physics;
import Structs.Vector2;

public class PhysicsObject {
	//A basic physics object
	
	
	//Unfortunately, this is Java, so get/set methods for below vars
	//SCRATCH the above, let's adopt the Python rule of thumb - "we're all adults here", and let's leave everything public
	//until we have a concrete base that won't be drastically changing as it is now.
	public Vector2 position;
	//I needed this public for a thing, which is inefficient so needs to be redone
	public Vector2 velocity;
	
	//public Vector2 force = new Vector2();
	
	public double gravitationalMass;
	public double inertialMass;
	
	public PhysicsObject(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass){
		this.position = position;
		this.velocity = velocity;
		this.gravitationalMass  = gravitationalMass;
		this.inertialMass = inertialMass;
	}
	
	public PhysicsObject(Vector2 position, double mass){
		this(position, new Vector2(), mass, mass);
	}
	
	public void updateAcceleration(Vector2 force){
		force.multiply( 1 / getInertialMass());
		velocity.add(force);
	}
	
	public void updateAcceleration(double forceX, double forceY){
		forceX *= (1 / getInertialMass());
		forceY *= (1 / getInertialMass());
		velocity.add(forceX, forceY);
	}
	
	public void updatePosition(){
		position.add(velocity);
	}

	public void calculateAcceleration(Vector2 force, Vector2 accelerationOut){
		accelerationOut.set(force);
		accelerationOut.multiply( 1 / getInertialMass());
	}
	
	//Remember this returns a copy
	public Vector2 getPosition(){return position;}
	public double getInertialMass(){return inertialMass;}
	public double getGravitationalMass(){return gravitationalMass;}
	//TODO This is temporary, radius should be determined by density
	public double getRadius(){return Math.sqrt(1000d * Math.sqrt(getGravitationalMass()));}
	
}
