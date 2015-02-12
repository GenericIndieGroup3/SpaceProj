public class PhysicsObject {
	//A basic physics object
	
	//Physics variables
	private static double gravitationalConstant = 30;
	
	
	//Unfortunately, this is Java, so get/set methods for below vars
	private Vector2 position;
	//I needed this public for a thing, which is inefficient so needs to be redone
	public Vector2 velocity;
	
	private Vector2 acceleration = new Vector2();
	
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
	
	public void update(Vector2 netForce){
		//I moved your addForces method - it seems that
		//we will use this method repeatedly, so I made it a 
		//static method in the Vector2 class
		//Vector2 netForce = Vector2.addVectors(forces);
		Vector2 acceleration = calculateAcceleration(netForce);
		position.setAdd(velocity);
		velocity.setAdd(acceleration);
	}
	
	public Vector2 calculateAcceleration(Vector2 force){
		return force.multiply( 1 / inertialMass);
	}
	
	//Returns the gravity that another object inflicts on this
//	public Vector2 calculateGravity(PhysicsObject other){
//		Vector2 distance = other.getPosition().subtract(getPosition());
//		double distanceMag = distance.magnitude();
//		double gravForceMag = gravitationalConstant * getGravitationalMass() * other.getGravitationalMass() / (distanceMag * distanceMag); 
//		return distance.copy().magnitude(gravForceMag);
//	}
	
	//Remember this returns a copy
	public Vector2 getPosition(){return position.copy();}
	public double getInertialMass(){return inertialMass;}
	public double getGravitationalMass(){return gravitationalMass;}
	//TODO This is temporary, radius should be determined by density and also square rooted
	public double getRadius(){return Math.sqrt(Math.sqrt(getGravitationalMass()));}
	
}
