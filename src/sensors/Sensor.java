package sensors;

import java.util.UUID;

public interface Sensor {
	
	public boolean isTriggered();
	public UUID[] getTriggers();
	public void update();
	
}
