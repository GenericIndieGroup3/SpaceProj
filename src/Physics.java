import java.util.*;

public class Physics {
	private static final int GRAVITATIONAL_CONSTANT = 3;
	
	private List<PhysicsObject> obj = new ArrayList<PhysicsObject>();
	public Physics(){
		
	}
	
	public void addObj(PhysicsObject o){
		obj.add(o);
	}
	
	//TODO Will return a one-way vector. Need to figure out force buffer and optimize for 2 objects
	private Vector2 getGrav(PhysicsObject a, PhysicsObject b){
		Vector2 distance = b.getPosition().subtract(a.getPosition());
		double distanceMag = distance.magnitude();
		double gravForceMag = GRAVITATIONAL_CONSTANT * a.getGravitationalMass() * b.getGravitationalMass() / (distanceMag * distanceMag); 
		return distance.copy().magnitude(gravForceMag);
	}
	
}
