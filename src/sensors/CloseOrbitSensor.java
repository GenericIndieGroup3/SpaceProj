package sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import obj.Missile;
import obj.Ship;
import Games.MainGame;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Vector2;

public class CloseOrbitSensor implements Sensor<Ship> {

	private boolean isTriggered = false;
	private double radius;
	private int time;
	private UUID targetId;
	private PhysicsObject target;
	private Vector2 pos;
	private List<Ship> triggers = new ArrayList<Ship>();
	private List<Integer> triggerData = new ArrayList<Integer>();
	private PhysicsSystem sys = MainGame.mainGame.getActiveSystem();
	
	public CloseOrbitSensor(PhysicsObject target, double radius, int time){
		this.target = target;
		this.radius = radius;
		this.time = time;
		targetId = target.getUUID();
	}
	
	public CloseOrbitSensor(UUID id, double radius, int time){
		targetId = id;
		target = sys.getObject(id);
		this.radius = radius;
		this.time = time;
	}
	
	public CloseOrbitSensor(double x, double y, double radius, int time){
		pos = new Vector2(x,y);
		this.radius = radius;
		this.time = time;
	}
	
	@Override
	public boolean isTriggered() {
		return isTriggered;
	}

	@Override
	public Ship[] getTriggers() {
		Ship[] ans = new Ship[triggers.size()];
		for(int i = 0; i < ans.length; i++){
			ans[i] = triggers.get(i);
		}
		return ans;
	}
	
	@Override
	public void update() {
		sys = MainGame.mainGame.getActiveSystem();
		for(PhysicsObject p : sys.objects){
			if(p instanceof Ship){
				if((target != null && sys.distanceTo(target, p) <= radius) || (pos != null && sys.distanceTo(pos,p) <= radius)){
					
				}
			}
		}
	}

}
