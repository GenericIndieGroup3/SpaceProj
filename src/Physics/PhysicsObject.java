package Physics;
import Structs.Vector2;

public class PhysicsObject {
	
	public Vector2 position;
	public Vector2 velocity;
	
	public double gravitationalMass;
	public double inertialMass;
	
	private Vector2 acceleration = new Vector2();
	
	public PhysicsObject(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass){
		this.position = position;
		this.velocity = velocity;
		this.gravitationalMass  = gravitationalMass;
		this.inertialMass = inertialMass;
	}
	
	public PhysicsObject(Vector2 position, double mass){
		this(position, new Vector2(), mass, mass);
	}
	
	public PhysicsObject copy(){
		return new PhysicsObject(this.position.copy(),this.velocity.copy(),this.gravitationalMass,this.inertialMass);
	}
	public boolean equals(PhysicsObject o){
		return (this.position.equals(o.position) && this.velocity.equals(o.velocity) &&
		this.gravitationalMass == o.gravitationalMass && this.inertialMass == o.inertialMass);
	}

	public Vector2 getPosition(){return position;}
	public double getInertialMass(){return inertialMass;}
	public double getGravitationalMass(){return gravitationalMass;}
	public double getRadius(){return Math.sqrt(1000d * Math.sqrt(getGravitationalMass()));}
	
	public void calculateAcceleration(Vector2 force, Vector2 accelerationOut){
		accelerationOut.set(force);
		accelerationOut.multiply( 1 / getInertialMass());
	}
	public double calculateAcceleration(double forceX){
		return forceX * (1/ getInertialMass());
	}
	public void resetAcceleration(){
		acceleration.set(0, 0);
	}
	public void accelerateA(Vector2 accel){
		acceleration.add(accel);
	}
	public void accelerateA(double x, double y){
		acceleration.x += x;
		acceleration.y += y;
	}
	public void accelerateF(double x, double y){
		accelerateA(calculateAcceleration(x), calculateAcceleration(y));
	}
	
	public void updateVelocity(){
		velocity.add(acceleration);
	}
	public void updatePosition(){
		position.add(velocity);
	}

	
}
