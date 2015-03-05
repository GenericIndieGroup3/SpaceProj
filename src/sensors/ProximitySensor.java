package sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Games.MainGame;

public class ProximitySensor implements Sensor {

	private List<UUID> triggers = new ArrayList<UUID>();
	
	private PhysicsSystem currentSystem = MainGame.mainGame.getActiveSystem();
	
	public ProximitySensor(PhysicsObject target, double radius){
		
	}
	
	@Override
	public boolean isTriggered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID[] getTriggers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
