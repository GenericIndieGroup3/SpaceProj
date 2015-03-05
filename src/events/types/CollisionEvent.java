package events.types;

import Physics.PhysicsObject;

public class CollisionEvent extends Event {

	public PhysicsObject a;
	public PhysicsObject b;
	
	public CollisionEvent(PhysicsObject a, PhysicsObject b){
		this.a = a;
		this.b = b;
	}
	
}
