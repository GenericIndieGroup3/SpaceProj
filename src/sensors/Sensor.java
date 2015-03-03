package sensors;

import Physics.PhysicsObject;

public interface Sensor<ObjectType extends PhysicsObject> {
	
	public boolean isTriggered();
	public ObjectType[] getTriggers();
	public void update();
	
}
