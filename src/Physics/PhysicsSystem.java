package Physics;
import java.util.*;

import Structs.Vector2;

public class PhysicsSystem {
	private static final double GRAVITATIONAL_CONSTANT = 0.001;
	
	public int charNum =2;
	public int centerNum = 0;
	
	public ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
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
	//Stores the forces sum 
	Map<PhysicsObject,Vector2> forceBuffer = new HashMap<PhysicsObject,Vector2>();
	
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
	
	//TODO this can easily be improved with a collision quad-tree
	private boolean checkCollision(PhysicsObject a, PhysicsObject b){
		
		//These exact calculations are done in getGrav(), maybe we can reuse them somehow by
		//merging into 1 function
		Vector2 distance = b.getPosition().copy();
		distance.subtract(a.getPosition());
		
		double distanceMag = distance.getMagnitude();
		double minDistance = a.getRadius() + b.getRadius();
		
		return distanceMag <= minDistance;
		
	}
	
	public void update(int frameNum){
		
		//These are used because you can't modify the List when you're iterating over it
		List<PhysicsObject> toRemove = new ArrayList<PhysicsObject>();
		List<PhysicsObject> toAdd = new ArrayList<PhysicsObject>();
		
		//Vector2 instances to be re-used for calculating gravity and forces 
		Vector2 grav = new Vector2();
		Vector2 force = new Vector2();
		
		for(PhysicsObject p : objects){
			force.set(0, 0);
			for(PhysicsObject o: objects){
				//TODO n^2 complexity is really bad and slow, once we get the basic mechanics
				//we need to heavily optimize this using quad trees and such and centers of mass
				if(o != p){
					if(checkCollision(p, o) && !toRemove.contains(p) && !toRemove.contains(o)){
						//Objects collided and were not already checked
						
						//I think proper momentum calculations should be implemented, but
						//currently it just uses your idea of destroying the smaller object
						if(p.getInertialMass() > o.getInertialMass()){
							toRemove.add(o);
						}
						else if(o.getInertialMass() > p.getInertialMass()){
							toRemove.add(p);
						}
						else if(o.getInertialMass() == p.getInertialMass()){
							//I just decided to remove o
							toRemove.add(o);
						}
					}
					else{
						//Objects did not collide
						
						//this modifies the grav variable to be equal to the gravForce
						getGrav(p, o, grav);
						force.add(grav);
					}
				}
			}
			p.updateAcceleration(force);
			
			//forceBuffer is currently not used for anything, but it might be later on
			forceBuffer.get(p).set(force);
		}
		
		for(PhysicsObject p: toRemove)
			objects.remove(p);
		for(PhysicsObject p: toAdd)
			objects.add(p);
		
		for(PhysicsObject p: objects){
			//This centers the star. It shouldn't be part of the physics system, but it was easiest to put it here.
			//p.position.subtract(getStar().position);
			p.updatePosition();
		}
		
		
	}
	
	//This is me re-adding the calcGrav function for individual objects
	//This should be used in both the update() and calculateTrajectory() methods
	//However, it needs to be extended for more general use before that
	public void calc(PhysicsObject p){
		
		Vector2 grav = new Vector2();
		Vector2 force = new Vector2();
		//This was copy-pasta'd, so it doesn't make any contextual sense
		force.set(0, 0);
			
		for(PhysicsObject o: objects){
			if(o != p){
				
					getGrav(p, o, grav);
					force.add(grav);
			}
		}
		p.updateAcceleration(force);
		p.updatePosition();
		
	}
	
	public List<PhysicsObject> calculateTrajectory(PhysicsObject p, int positions){
		List<PhysicsObject> trajectory = new ArrayList<PhysicsObject>(positions);
		PhysicsObject tmp = p.copy();
		Vector2 out = new Vector2();
		Vector2 buff = new Vector2();
		
		for(int i = 0; i < positions; i++){
			//You forgot to reset the buffer!
			buff.set(0, 0);
			//This will be horribly inefficient
			//That's fine
			for(PhysicsObject o : objects){
				
				//THIS is the bug that I spent forever trying to locate
				//the object who's path this is calculating was greatly influencing its own trajectory
				if(o != tmp && o != p){
					getGrav(tmp,o,out);
					buff.add(out);
				}
			}
			tmp.updateAcceleration(buff);
			tmp.updatePosition();
			trajectory.add(tmp.copy());
		}
		return trajectory;
	}
	
	public PhysicsObject getStar(){
		return objects.get(0);
	}
	public PhysicsObject getChar(){
		return objects.get(charNum);
	}
	public PhysicsObject getCenter(){
		return objects.get(centerNum);
	}
	
	public List<PhysicsObject> getObj(){
		return objects;
	}
	
}
