import java.util.*;

public class PhysicsSystem {
	private static final int GRAVITATIONAL_CONSTANT = 3;
	
	private List<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	public PhysicsSystem(){
		
	}
	
	public PhysicsSystem(PhysicsObject[] obj){
		for(int i = 0; i < obj.length; i++){
			forceBuffer.put(obj[i], new Vector2());
			objects.add(obj[i]);
		}
	}
	
	public void addObj(PhysicsObject o){
		objects.add(o);
		forceBuffer.put(o, new Vector2());
	}
	
	Map<PhysicsObject,Vector2> forceBuffer = new HashMap<PhysicsObject,Vector2>();
	
	//TODO Will return a one-way vector. Need to figure out force buffer and optimize for 2 objects
	private Vector2 getGrav(PhysicsObject a, PhysicsObject b){
		Vector2 distance = b.getPosition().subtract(a.getPosition());
		double distanceMag = distance.magnitude();
		double gravForceMag = GRAVITATIONAL_CONSTANT * a.getGravitationalMass() * b.getGravitationalMass() / (distanceMag * distanceMag); 
		return distance.copy().magnitude(gravForceMag);
	}
	
}
