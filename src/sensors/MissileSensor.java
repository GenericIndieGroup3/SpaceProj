package sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import obj.Missile;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Games.MainGame;

public class MissileSensor implements Sensor<Missile> {

	private List<Missile> triggers = new ArrayList<Missile>();
	private boolean isTriggered = false;
	
	private PhysicsSystem currentSystem;
	
	private PhysicsObject target;
	public UUID targetId;
	private double x;
	private double y;
	private double radius;
	
	public MissileSensor(PhysicsObject target, double radius, PhysicsSystem system){
		this.target = target;
		targetId = target.getUUID();
		this.radius = radius;
		currentSystem = system;
	}
	
	public MissileSensor(double x, double y, double radius, PhysicsSystem system){
		this.x = x;
		this.y = y;
		this.radius = radius;
		currentSystem = system;
	}
	
	public MissileSensor(UUID id, double radius, PhysicsSystem system){
		currentSystem = system;
		this.targetId = id;
		this.target = currentSystem.searchById(id);
		this.radius = radius;
	}
	
	@Override
	public boolean isTriggered() {
		return isTriggered;
	}

	@Override
	public Missile[] getTriggers() {
		Missile[] ans = new Missile[triggers.size()];
		for(int i = 0; i < ans.length; i++){
			ans[i] = triggers.get(i);
		}
		return ans;
	}

	@Override
	public void update() {
		triggers.clear();
		isTriggered = false;
		for(PhysicsObject p : currentSystem.objects){
			if(p instanceof Missile){
				if((target != null && currentSystem.distanceTo(target, p) <= radius) || currentSystem.distanceTo(x,y,p) <= radius){
					isTriggered = true;
					triggers.add((Missile)p);
				}
			}
		}
	}
	
}
