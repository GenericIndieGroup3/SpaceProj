package events.listeners;

import Physics.PhysicsObject;

public interface collisionListener {

	public void onCollide(PhysicsObject a, PhysicsObject b);
}
