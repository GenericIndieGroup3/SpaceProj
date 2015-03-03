package sensors;

//import java.util.UUID;

import Physics.PhysicsObject;

public interface Sensor<objType extends PhysicsObject> {
	
	public boolean isTriggered();
	public objType[] getTriggers();
	public void update();
	
}
