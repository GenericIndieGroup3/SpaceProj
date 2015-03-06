package obj;

import Games.MainGame;
import Physics.PhysicsObject;
import Structs.Vector2;
import events.EventPriority;
import events.Listener;
import events.types.CollisionEvent;

public class Missile extends PhysicsObject implements Listener<CollisionEvent> {

	public Missile(){
		super();
		registerListener();
	}
	public Missile(Vector2 position, Vector2 velocity, double mass) {
		super(position, velocity, mass);
		registerListener();
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
		other.shouldBeRemoved = true;
		
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
