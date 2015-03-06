package obj;

import Physics.PhysicsObject;
import Structs.Vector2;

public class Ship extends PhysicsObject{
	
	public Ship(){
		super();
	}
	public Ship(Vector2 position, Vector2 velocity, double mass){
		super(position, velocity, mass);
	}
	public Ship(Vector2 position, double mass){
		super(position,mass);
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
	
	@Override
	public PhysicsObject copy(){
		Ship o = new Ship();
		o.set(this);
		return o;
	}
	
	public void set(Ship a){
		super.set(a);
	}
	
}
