package events.arguments;

import Physics.PhysicsObject;

public class collisionEventArgs {

	PhysicsObject a;
	PhysicsObject b;
	
	boolean isCanceled = false;
	
	public collisionEventArgs(){
		
	}
	
	public void set(PhysicsObject a, PhysicsObject b){
		this.a = a;
		this.b = b;
	}
}
