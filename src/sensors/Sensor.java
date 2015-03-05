package sensors;

import java.util.UUID;
import Physics.PhysicsObject;

public interface Sensor<ObjectType extends PhysicsObject> {
	
	public boolean isTriggered();
	public ObjectType[] getTriggers();
	public void update();
	
}