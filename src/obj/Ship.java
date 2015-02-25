package obj;

import Physics.PhysicsObject;
import Structs.Vector2;

public class Ship extends PhysicsObject{
	
	public Ship(Vector2 position, Vector2 velocity, double gMass, double iMass){
		super(position, velocity, gMass, iMass);
	}
	
	public Ship(Vector2 position, double mass){
		super(position,mass);
	}
	
	public void thrust(Vector2 thrust){
		thrust.multiply(1/this.inertialMass);
		this.accelerate(thrust);
	}
	
	public Missile shootMissile(double mass, double relVelocity, Vector2 direction){
		return shootMissile(mass, mass, relVelocity, direction);
	}
	public Missile shootMissile(double gMass, double iMass, double relVelocity, Vector2 direction){
		Vector2 position = getPosition().copy();
		direction.magnitude(getRadius() * 2);
		position.add(direction);
		
		Vector2 vel = velocity.copy();
		direction.magnitude(relVelocity);
		vel.add(direction);
		Missile m = new Missile(position, vel, gMass, iMass);
		return m;
	}
	
}
