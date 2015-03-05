package sensors;

import obj.Ship;

public class CloseOrbitSensor implements Sensor<Ship> {

	private boolean isTriggered = false;
	
	@Override
	public boolean isTriggered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Ship[] getTriggers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
