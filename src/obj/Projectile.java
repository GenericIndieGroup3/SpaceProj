package obj;

import Physics.PhysicsObject;
import Structs.Vector2;

public class Projectile extends PhysicsObject {

	public Projectile(Vector2 position, Vector2 velocity, double gMass, double iMass){
		super(position,velocity,gMass,iMass);
	}
	
}
