package Physics;
import java.util.*;

import Structs.Vector2;

public class PhysicsSystem {
	private static final double GRAVITATIONAL_CONSTANT = 0.0010;
	
	private ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
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
		return null;
	}
	
	public Vector2 velocityForCircularMotion(PhysicsObject planet, PhysicsObject sun, boolean clockwise){
		Vector2 gravForce = new Vector2();
		getGrav(planet, sun, gravForce);
		Vector2 acceleration = new Vector2();
		planet.calculateAcceleration(gravForce, acceleration);
		Vector2 distance = sun.getPosition().copy();
		distance.subtract(planet.getPosition());
		double radius = distance.getMagnitude();
		double speed = Math.sqrt(acceleration.getMagnitude() * radius);
		if(clockwise)
			//sets distance to the required velocities direction
			distance = acceleration.createClockwisePerpendicular();
		else
			distance = acceleration.createClockwisePerpendicular();
		//sets distance to the required velocity
		distance.magnitude(speed);
		//adds the other objects velocity so that it stays relative to it
		distance.add(sun.velocity);
		return distance;
		
	}
	Map<PhysicsObject,Vector2> forceBuffer = new HashMap<PhysicsObject,Vector2>();
	
	//TODO Will return a one-way vector. Need to figure out force buffer and optimize for 2 objects
	private void getGrav(PhysicsObject a, PhysicsObject b, Vector2 gravOut){
		
		Vector2 distance = b.getPosition().copy();
		distance.subtract(a.getPosition());
		
		double distanceMag = distance.getMagnitude();
		if(distanceMag == 0){
			gravOut.set(0, 0);
			return;
		}
		
		double gravForceMag = GRAVITATIONAL_CONSTANT * a.getGravitationalMass() * b.getGravitationalMass() / (distanceMag * distanceMag); 
		gravOut.set(distance);
		gravOut.multiply(gravForceMag);
	}
	

	
	public void update(int frameNum){
		Vector2 grav = new Vector2();
		for(PhysicsObject p : objects){
			
			if ((frameNum % 1) == 0 || frameNum == 1)
			{
				//p.force.clear();
				Vector2 pForce = forceBuffer.get(p);
				for(PhysicsObject o: objects){
					getGrav(p, o, grav);
					pForce.add(grav);
				}
				p.updateAcceleration(pForce);
			}
			p.updatePosition();
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
	}
	private void update(PhysicsObject o){
		Vector2[] localBuffer = new Vector2[objects.size()];
		for(int i = 0; i < objects.size(); i++){
			localBuffer[i] = getGrav(o,objects.get(i));
		}
		forceBuffer.replace(o, Vector2.addVectors(localBuffer));
	}
	*/
	
	public PhysicsObject[] getObj(){
		PhysicsObject[] ans = new PhysicsObject[objects.size()];
		for(int i = 0; i < objects.size(); i++){ans[i] = objects.get(i);}
		return ans;
	}
	
}
