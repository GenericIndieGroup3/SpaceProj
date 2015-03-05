package events.types;

import Physics.PhysicsObject;

public class RemoveObjectEvent extends Event {

	PhysicsObject object;
	
	public RemoveObjectEvent(PhysicsObject o){
		this.object = o;
	}
	
	public PhysicsObject getObject(){
		return object;
	}
}
