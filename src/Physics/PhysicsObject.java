package Physics;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import Structs.Vector2;

public class PhysicsObject {
	
	public Vector2 position;
	public Vector2 velocity;
	
	public double gravitationalMass;
	public double inertialMass;
	
	public boolean shouldBeRemoved = false;
	public boolean isRemoved = false;
	
	private UUID uuid;
	
	private Vector2 acceleration = new Vector2();
	
	public PhysicsObject(){
		set(new Vector2(), new Vector2(), 1, 1);
	}
	public PhysicsObject(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass){
		set(position, velocity, gravitationalMass, inertialMass);
	}
	public PhysicsObject(Vector2 position, double mass){
		set(position, new Vector2(), mass, mass);
	}
	public void set(PhysicsObject a){
		this.position = a.position.copy();
		this.velocity = a.velocity.copy();
		this.gravitationalMass = a.gravitationalMass;
		this.inertialMass = a.inertialMass;
		this.uuid = a.uuid;
	}
	public void set(Vector2 position, Vector2 velocity, double gMass, double iMass){
		this.position = position;
		this.velocity = velocity;
		this.gravitationalMass = gMass;
		this.inertialMass = iMass;
		this.uuid = UUID.randomUUID();
	}
	public PhysicsObject copy(){
		PhysicsObject o = new PhysicsObject();
		o.set(this);
		return o;
	}
	public void setUUID(UUID uuid){
		this.uuid = uuid;
	}
	public boolean equals(PhysicsObject o){
		return (this.position.equals(o.position) && this.velocity.equals(o.velocity) &&
		this.gravitationalMass == o.gravitationalMass && this.inertialMass == o.inertialMass);
	}

	public Vector2 getVelocity(){return velocity;}
	public Vector2 getPosition(){return position;}
	public double getIMass(){return inertialMass;}
	public double getGMass(){return gravitationalMass;}
	public double getRadius(){
		return Math.cbrt(getGMass() * 10000);
	}
	//public double getRadius(){return Math.sqrt(2000d * Math.sqrt(gravitationalMass));}
	public UUID getUUID(){return uuid;}
	
	public void calculateAcceleration(Vector2 force, Vector2 accelerationOut){
		accelerationOut.set(force);
		accelerationOut.multiply( 1 / getIMass());
	}
	public double calculateAcceleration(double forceX){
		return forceX * (1/ getIMass());
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
	
	/**
	 * Updates the velocity of the object. In most cases, this just means adding its acceleration to
	 * its velocity, but complicated objects such as stations have to calculate a required velocity,
	 * and need their parentSystem for that.<br>
	 * @param parentSystem The parent system of the object
	 */
	public void updateVelocity(PhysicsSystem parentSystem){
		velocity.add(acceleration);
	}
	public void updatePosition(){
		position.add(velocity);
	}
	
	public void draw(){
		int segments = 20;	
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for( int n = 0; n <= segments; ++n ) {
	            double t = 2 * Math.PI * n / segments;
	            GL11.glVertex2d(position.x + Math.sin(t)* getRadius(), position.y + Math.cos(t)* getRadius());
	        }
		GL11.glEnd();
	}
	
	//TODO This will be really slow, but since it's only for debugging, no one will know...
	@Override
	public String toString(){
		String ans = "Physics Object with inert mass " + this.inertialMass + ", gravitational mass " + this.gravitationalMass + ", at position" + this.position + ", and velocity" + this.velocity;
		
		return ans;
	}
}
