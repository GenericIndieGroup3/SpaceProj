package sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Games.MainGame;

public class ProximitySensor<ObjType extends PhysicsObject> implements Sensor<ObjType> {

	private List<UUID> triggers = new ArrayList<UUID>();
	private boolean isTriggered = false;
	
	private PhysicsSystem currentSystem = MainGame.mainGame.getActiveSystem();
	
	private PhysicsObject target;
	private UUID targetId;
	private double x;
	private double y;
	private double radius;
	
	public ProximitySensor(PhysicsObject target, double radius){
		this.target = target;
		targetId = target.getUUID();
		this.radius = radius;
	}
	
	public ProximitySensor(double x, double y, double radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public ProximitySensor(UUID id, double radius){
		this.targetId = id;
		this.target = currentSystem.searchById(id);
		this.radius = radius;
	}
	
	@Override
	public boolean isTriggered() {
		return isTriggered;
	}

	@Override
	public Object[] getTriggers() {
		return triggers.toArray();
	}

	@Override
	public void update() {
		
	}
	
}
