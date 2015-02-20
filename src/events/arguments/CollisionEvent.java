package events.arguments;

import Physics.PhysicsObject;

public class CollisionEvent {

	PhysicsObject a;
	PhysicsObject b;
	
	boolean isCanceled = false;
	
	public CollisionEvent(PhysicsObject a, PhysicsObject b){
		set(a, b);
	}
	
	public void set(PhysicsObject a, PhysicsObject b){
		this.a = a;
		this.b = b;
	}
	
	public void setCanceled(){
		
	}
}
