package sensors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import obj.Missile;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Vector2;
import Games.MainGame;

public class MissileSensor implements Sensor<Missile> {

	private List<Missile> triggers = new ArrayList<Missile>();
	private boolean isTriggered = false;
	
	private PhysicsObject target;
	public UUID targetId;
	private Vector2 pos;
	private double radius;
	
	public MissileSensor(PhysicsObject target, double radius){//, PhysicsSystem system){
		this.target = target;
		targetId = target.getUUID();
		this.radius = radius;
		//currentSystem = system;
	}
	
	public MissileSensor(Vector2 pos, double radius){//, PhysicsSystem system){
		this.pos = pos;
		this.radius = radius;
		//currentSystem = system;
	}
	
	public MissileSensor(UUID id, double radius){//, PhysicsSystem system){
		//currentSystem = system;
		this.targetId = id;
		this.target = MainGame.mainGame.getActiveSystem().getObject(id);
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
		
		for(PhysicsObject p : MainGame.mainGame.getActiveSystem().objects){
			if(p instanceof Missile){
				if(target != null){
					if(MainGame.mainGame.getActiveSystem().distanceTo(target, p) <= radius){
						isTriggered = true;
						triggers.add((Missile)p);
					}
				} else if(MainGame.mainGame.getActiveSystem().distanceTo(pos,p) <= radius){
					isTriggered = true;
					triggers.add((Missile)p);
				}
			}
		}
	}
	
}
