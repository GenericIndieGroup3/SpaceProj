package obj;

import java.util.UUID;

import Games.MainGame;
import Physics.PhysicsObject;
import Structs.Vector2;
import events.EventPriority;
import events.Listener;
import events.types.CollisionEvent;

public class Missile extends PhysicsObject implements Listener<CollisionEvent> {

	private UUID shooterID;
	
	public Missile(){
		super();
		registerListener();
	}
	public Missile(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass,UUID id) {
		super(position, velocity, gravitationalMass, inertialMass);
		registerListener();
		shooterID = id;
	}
	public Missile(Vector2 position, Vector2 velocity, double mass,UUID id){
		super(position, velocity, mass, mass);
		registerListener();
		shooterID = id;
	}
	
	void registerListener(){
		MainGame.mainGame.getActiveSystem().collisionEventDistributor.addListener(this, EventPriority.HIGH);
	}
	
	@Override
	public void invoke(CollisionEvent e) {
		PhysicsObject other;
		if(e.a == this)
			other = e.b;
		if(e.b == this)
			other = e.a;
		else
			return;
		
		e.cancel();
		shouldBeRemoved = true;
		if(!(other instanceof Station)){
			other.shouldBeRemoved = true;
		}
		
	}
	
	@Override
	public PhysicsObject copy(){
		Missile o = new Missile();
		o.set(this);
		return o;
	}
	
	public void set(Missile a){
		super.set(a);
	}
	
	
	

}
