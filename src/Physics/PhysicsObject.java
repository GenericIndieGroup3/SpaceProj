package Physics;
import Structs.Vector2;

public class PhysicsObject {
	
	public Vector2 position;
	public Vector2 velocity;
	
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
	
	public PhysicsObject copy(){
		return new PhysicsObject(this.position.copy(),this.velocity.copy(),this.gravitationalMass,this.inertialMass);
	}
	public boolean equals(PhysicsObject o){
		return (this.position.equals(o.position) && this.velocity.equals(o.velocity) &&
		this.gravitationalMass == o.gravitationalMass && this.inertialMass == o.inertialMass);
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
	
	public void accelerate(Vector2 accel){
		velocity.add(accel);
	}
	
	public void accelerate(double x, double y){
		velocity.x += x;
		velocity.y += y;
	}
	
}
