package Physics;
import java.util.UUID;

import org.lwjgl.opengl.GL11;

import Structs.Vector2;

public class PhysicsObject {
	
	public Vector2 position;
	public Vector2 velocity;
	
	private double mass;
	
	public boolean shouldBeRemoved = false;
	public boolean isRemoved = false;
	
	private UUID uuid;
	
	private Vector2 acceleration = new Vector2();
	
	public PhysicsObject(){
		set(new Vector2(), new Vector2(), 1);
	}
	public PhysicsObject(Vector2 position, Vector2 velocity, double mass){
		set(position, velocity, mass);
	}
	public PhysicsObject(Vector2 position, double mass){
		set(position, new Vector2(), mass);
	}
	public void set(PhysicsObject a){
		this.position = a.position.copy();
		this.velocity = a.velocity.copy();
		this.mass = a.mass;
		this.uuid = a.uuid;
	}
	public void set(Vector2 position, Vector2 velocity, double mass){
		this.position = position;
		this.velocity = velocity;
		this.mass = mass;
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

	public Vector2 getVelocity(){return velocity;}
	public Vector2 getPosition(){return position;}
	public double getIMass(){return mass;}
	public double getGMass(){return mass;}
	public double getRadius(){
		return Math.cbrt(getIMass() * 10000);
	}
	public double getAltRadius(){
		//return getGMass() * 3;
		//return getGMass() * 100 / getRadius();
		//return getRadius() * 3;
		return 20 * Math.cbrt(getGMass() * 10000);
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
	public void drawGravitationalInfluence(double r, double g, double b){
		
		int segments = 20;
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glColor4d(r, g, b, 0.2);
		GL11.glVertex2d(position.x, position.y);
			for( int n = 0; n <= segments; ++n ) {
				GL11.glColor4d(r, g, b, 0.001);
	            double t = 2 * Math.PI * n / segments;
	            GL11.glVertex2d(position.x + Math.sin(t)* getAltRadius(), position.y + Math.cos(t)* getAltRadius());
	        }
		GL11.glEnd();
	}

	@Override
	public String toString(){
		String ans = "Physics Object with inert mass " + this.getIMass() + ", gravitational mass " + this.getGMass() + ", at position" + this.position + ", and velocity" + this.velocity;
		
		return ans;
	}
}
