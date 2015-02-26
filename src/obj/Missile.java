package obj;

import Games.MainGame;
import Physics.PhysicsObject;
import Structs.Vector2;
import events.EventPriority;
import events.Listener;
import events.types.CollisionEvent;

public class Missile extends PhysicsObject implements Listener<CollisionEvent> {

	public Missile(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass) {
		super(position, velocity, gravitationalMass, inertialMass);
		MainGame.physicsSystem.collisionEventDistributor.addListener(this, EventPriority.HIGH);
	}
	public Missile(Vector2 position, Vector2 velocity, double mass){
		super(position, velocity, mass, mass);
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
		other.shouldBeRemoved = true;
		
	}
	
	
	
	

}
