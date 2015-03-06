package sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import obj.Ship;
import obj.Station;
import Games.MainGame;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Vector2;

public class OrbitSensor implements Sensor<Ship> {

	private boolean isTriggered = false;
	private double radius;
	private int time;
	private UUID targetId;
	private PhysicsObject target;
	private Vector2 pos;
	private List<Ship> triggers = new ArrayList<Ship>();
	private List<Integer> triggerData = new ArrayList<Integer>();
	//private PhysicsSystem sys = MainGame.mainGame.getActiveSystem();
	
	public OrbitSensor(PhysicsObject target, double radius, int time){
		this.target = target;
		this.radius = radius;
		this.time = time;
		targetId = target.getUUID();
	}
	
	public OrbitSensor(UUID id, double radius, int time){
		targetId = id;
		target = MainGame.mainGame.physicsSystem.getObject(id);
		this.radius = radius;
		this.time = time;
	}
	
	public OrbitSensor(double x, double y, double radius, int time){
		pos = new Vector2(x,y);
		this.radius = radius;
		this.time = time;
	}
	
	@Override
	public boolean isTriggered() {
		return isTriggered;
	}

	List<Ship >ansBuffer = new ArrayList<Ship>();
	@Override
	public Ship[] getTriggers() {
		ansBuffer.clear();
		for(int i = 0; i < triggers.size(); i++){
			if(triggerData.get(i) >= time){
				ansBuffer.add(triggers.get(i));
			}
		}
		Ship[] ans = new Ship[ansBuffer.size()];
		for(int i = 0; i < ans.length; i++){	
			ans[i] = ansBuffer.get(i);
		}
		return ans;
	}
	
	Integer tmp;
	@Override
	public void update() {
		isTriggered = false;
		//sys = MainGame.mainGame.getActiveSystem();
		for(PhysicsObject p : MainGame.mainGame.physicsSystem.objects){
			if(p != target && !(p instanceof Station) && p instanceof Ship){
				if((target != null && MainGame.mainGame.physicsSystem.distanceTo(target, p) <= radius) || (pos != null && MainGame.mainGame.physicsSystem.distanceTo(pos,p) <= radius)){
					if(triggers.contains(p)){
						tmp = triggerData.get(triggers.indexOf(p));
						tmp++;
						triggerData.set(triggers.indexOf(p), tmp);
					} else{
						triggers.add((Ship)p);
						triggerData.add(0);
					}
				} else if(triggers.contains(p)){
					int i = triggers.indexOf(p);
					triggers.remove(p);
					triggerData.remove(i);
				}
			}
		}
		for(Ship s : triggers){
			if(triggerData.get(triggers.indexOf(s)) >= time){
				isTriggered = true;
			}
		}
	}

}
