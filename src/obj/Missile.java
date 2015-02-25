package obj;

import Physics.PhysicsObject;
import Structs.Vector2;

public class Missile extends PhysicsObject {

	public Missile(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass) {
		super(position, velocity, gravitationalMass, inertialMass);
	}
	public Missile(Vector2 position, Vector2 velocity, double mass){
		super(position, velocity, mass, mass);
	}
	
	

}
