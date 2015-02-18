package Physics;
import Structs.Vector2;

public class PhysicsObject {
	//A basic physics object
	
	//Physics variables
	//private static double gravitationalConstant = 30;
	
	
	//Unfortunately, this is Java, so get/set methods for below vars
	public Vector2 position;
	//I needed this public for a thing, which is inefficient so needs to be redone
	public Vector2 velocity;
	
	//public Vector2 force = new Vector2();
	
	private double gravitationalMass;
	private double inertialMass;
	
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
	
	public void updatePosition(){
		position.add(velocity);
	}

	public void calculateAcceleration(Vector2 force, Vector2 accelerationOut){
		accelerationOut.set(force);
		accelerationOut.multiply( 1 / getInertialMass());
	}
	
	//Returns the gravity that another object inflicts on this
//	public Vector2 calculateGravity(PhysicsObject other){
//		Vector2 distance = other.getPosition().subtract(getPosition());
//		double distanceMag = distance.magnitude();
//		double gravForceMag = gravitationalConstant * getGravitationalMass() * other.getGravitationalMass() / (distanceMag * distanceMag); 
//		return distance.copy().magnitude(gravForceMag);
//	}
	
	//Remember this returns a copy
	public Vector2 getPosition(){return position;}
	public double getInertialMass(){return inertialMass;}
	public double getGravitationalMass(){return gravitationalMass;}
	//TODO This is temporary, radius should be determined by density
	public double getRadius(){return Math.sqrt(1000d * Math.sqrt(getGravitationalMass()));}
	
}
