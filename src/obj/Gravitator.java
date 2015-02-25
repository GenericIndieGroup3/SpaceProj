package obj;

import Physics.PhysicsObject;
import Structs.Vector2;

public class Gravitator extends Ship {
	
	public Gravitator(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass){
		super(position, velocity, gravitationalMass, inertialMass);
	}
	
	public Gravitator(Vector2 position, double mass){
		super(position, mass);
	}
	
	public void addMass(double m){
		this.gravitationalMass += m;
	}
	
	public void setMass(double m){
		this.gravitationalMass = m;
	}
	
	public Missile shootMissile(double mass, double relVelocity, Vector2 direction){
		Vector2 position = getPosition().copy();
		direction.magnitude(getRadius() * 2);
		position.add(direction);
		
		Vector2 vel = velocity.copy();
		direction.magnitude(relVelocity);
		vel.add(direction);
		Missile m = new Missile(position, vel, mass);
		return m;
	}
	
}