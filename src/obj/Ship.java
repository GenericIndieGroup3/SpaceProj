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
	
}
