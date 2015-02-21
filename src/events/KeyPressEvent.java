package events;

import Physics.PhysicsObject;

public class KeyPressEvent extends Event {

	public PhysicsObject a;
	public PhysicsObject b;
	
	public KeyPressEvent(PhysicsObject a, PhysicsObject b){
		this.a = a;
		this.b = b;
	}
	
}
