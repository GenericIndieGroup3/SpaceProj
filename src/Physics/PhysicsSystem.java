package Physics;
import java.util.*;

import Structs.Vector2;

public class PhysicsSystem {
	private static final double GRAVITATIONAL_CONSTANT = 0.0001;
	
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
	
	public PhysicsObject[] explodify(PhysicsObject o){
		
	}
	
	public Vector2 velocityForCircularMotion(PhysicsObject planet, PhysicsObject sun, boolean clockwise){
		Vector2 temp = new Vector2();
		getGrav(planet, sun, temp);
		Vector2 acceleration = planet.calculateAcceleration(temp);
		double radius = sun.getPosition().subtract(planet.getPosition()).magnitude();
		double speed = Math.sqrt(acceleration.magnitude() * radius);
		Vector2 velocity;
		if(clockwise)
			velocity = acceleration.clockwisePerpendicular().magnitude(speed);
		else
			velocity = acceleration.counterClockwisePerpendicular().magnitude(speed);
		return velocity.add(sun.velocity);
		
	}
	Map<PhysicsObject,Vector2> forceBuffer = new HashMap<PhysicsObject,Vector2>();
	
	//TODO Will return a one-way vector. Need to figure out force buffer and optimize for 2 objects
	private void getGrav(PhysicsObject a, PhysicsObject b, Vector2 gravOut){
		Vector2 distance = b.getPosition().subtract(a.getPosition());
		double distanceMag = distance.magnitude();
		if(distanceMag == 0)
		{
			gravOut.x = 0;
			gravOut.y = 0;
			return;
		}
		double gravForceMag = GRAVITATIONAL_CONSTANT * a.getGravitationalMass() * b.getGravitationalMass() / (distanceMag * distanceMag); 
		gravOut.CopyFrom(distance);
		gravOut.MultInPlace(gravForceMag);
	}
	
	private void update(PhysicsObject o){
		Vector2[] localBuffer = new Vector2[objects.size()];
		for(int i = 0; i < objects.size(); i++){
			localBuffer[i] = getGrav(o,objects.get(i));
		}
		forceBuffer.replace(o, Vector2.addVectors(localBuffer));
	}
	
	public void update(){
		Vector2 grav = new Vector2();
		for(PhysicsObject p : objects){
			for(PhysicsObject o: objects){
				getGrav(p, o, grav);
				p.force.setAdd(grav);
				
			}
			p.update();
		}
	}
	
	/*
	public void update(){
		PhysicsObject curr;
		for(int i = 0; i < objects.size(); i++){
			curr = objects.get(i);
			update(curr);
			curr.update(forceBuffer.get(curr));
		}
	}*/
	
	public PhysicsObject[] getObj(){
		PhysicsObject[] ans = new PhysicsObject[objects.size()];
		for(int i = 0; i < objects.size(); i++){ans[i] = objects.get(i);}
		return ans;
	}
	
}
