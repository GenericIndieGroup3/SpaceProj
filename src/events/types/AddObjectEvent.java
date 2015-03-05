package events.types;

import Physics.PhysicsObject;

public class AddObjectEvent extends Event {

	PhysicsObject object;
	
	public AddObjectEvent(PhysicsObject o){
		this.object = o;
	}
	
	public PhysicsObject getObject(){
		return object;
	}
}
