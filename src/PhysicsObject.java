public class PhysicsObject {
	//A basic physics object
	
	//Physics variables
	private static double gravitationalConstant = 3;
	
	
	//Unfortunately, this is Java, so get/set methods for below vars
	private Vector2 position;
	private Vector2 velocity;
	//See comment line 35
	private Vector2 acceleration = new Vector2(0, 0);
	
	private double gravitationalMass;
	private double inertialMass;
	
	public PhysicsObject(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass){
		this.position = position;
		this.velocity = velocity;
		this.gravitationalMass  = gravitationalMass;
		this.inertialMass = inertialMass;
	}
	
	public PhysicsObject(Vector2 position, double mass){
		this(position, new Vector2(0, 0), mass, mass);
	}
	
	public Vector2 calculateAcceleration(Vector2 force){
		return force.multiply( 1 / inertialMass);
	}
	public void addForce(Vector2 force){
		Vector2 deltaAcceleration = calculateAcceleration(force);
		acceleration.setAdd(deltaAcceleration);
	}
	
	public void update(){
		velocity.setAdd(acceleration);
		//So I reset acceleration every update cycle
		//Is this how we should do it or store forces somehow or what?
		acceleration.setEquals(new Vector2(0, 0));
		position.setAdd(velocity);
	}
	
	public Vector2 calculateGravity(PhysicsObject other){
		Vector2 distance = getPosition().subtract(other.getPosition());
		double distanceMag = distance.magnitude();
		double gravForceMag = gravitationalConstant * getGravitationalMass() * other.getGravitationalMass() / (distanceMag * distanceMag); 
		Vector2 gravForce = distance.copy().setMagnitude(gravForceMag);
	}
	
	public Vector2 getPosition(){return position.copy();}
	public double getInertialMass(){return inertialMass;}
	public double getGravitationalMass(){return gravitationalMass;}
	public double getRadius(){return getGravitationalMass();}
}
