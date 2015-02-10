public class PhysicsObject {
	//A basic physics object
	
	
	//Unfortunately, this is Java, so get/set methods for below vars
	private Vector2 position;
	private Vector2 velocity;
	private Vector2 acceleration = new Vector2();
	
	private double gravitationMass;
	private double inertialMass;
	
	public PhysicsObject(Vector2 position, Vector2 velocity, double gravitationMass, double inertialMass){
		this.position = position;
		this.velocity = velocity;
		this.gravitationMass  = gravitationMass;
		this.inertialMass = inertialMass;
	}
	
	public PhysicsObject(Vector2 position, double mass){
		this(position, new Vector2(), mass, mass);
	}
	
	private Vector2 calculateAcceleration(Vector2 force){
		return force.multiply( 1 / inertialMass);
	}
	
	public void addForce(Vector2 force){
		Vector2 deltaAcceleration = calculateAcceleration(force);
		acceleration.setAdd(deltaAcceleration);
	}
	
	//My Version
	private Vector2 addForces(Vector2[] forces){
		Vector2 netForce = new Vector2();
		for(int i=0;i<forces.length;i++){
			netForce.setAdd(forces[i]);
		}
		return netForce;
	}
	
	public void update(){
		velocity.setAdd(acceleration);
		//So I reset acceleration every update cycle
		//Is this how we should do it or store forces somehow or what?
		/* The way I did it is you pass force into update, and update
		 * calculates acceleration. Neither acceleration nor force is stored
		 */
		acceleration.setEquals(new Vector2());
		position.setAdd(velocity);
	}
	
	//My Version
	public void update(Vector2[] forces){
		Vector2 netForce = addForces(forces);
		Vector2 acceleration = calculateAcceleration(netForce);
		position.setAdd(velocity);
		velocity.setAdd(acceleration);
	}
	
	public Vector2 getPosition(){return position;}
}
