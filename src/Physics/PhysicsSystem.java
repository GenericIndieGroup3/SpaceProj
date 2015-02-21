package Physics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Structs.Circle;
import Structs.Shape;
import Structs.Vector2;
import Structs.Vector4;
import events.CollisionEvent;
import events.EventDistributor;
import events.EventPriority;
import events.Listener;

public class PhysicsSystem implements Listener<CollisionEvent> {
	
	private static final double GRAVITATIONAL_CONSTANT = 0.005;
	
	public int charNum =2;
	public int centerNum = 0;
	
	public EventDistributor<CollisionEvent> collisionEventDistributor = new EventDistributor<CollisionEvent>();
	
	public ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	public PhysicsSystem(PhysicsSystem copyFrom){
		this();
		objects = new ArrayList<PhysicsObject>(copyFrom.objects.size());
		for(PhysicsObject o: copyFrom.objects){
			objects.add(o.copy());
		}
		charNum = copyFrom.charNum;
		centerNum = copyFrom.centerNum;
	}
	
	public PhysicsSystem(){
		collisionEventDistributor.addListener(this, EventPriority.LOW);
	}
	public PhysicsSystem(PhysicsObject[] obj){
		this();
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
	
	@Override
	public void invoke(CollisionEvent e){
		
		e.cancel();
		int val = (int) (Math.random() * 200 + 0.5);
		if(val == 55)
			e.unCancel();
	}
	
	List<PhysicsObject> toRemove = new ArrayList<PhysicsObject>();
	public void update(int frameNum){
		
		//These are used because you can't modify the List when you're iterating over it

		for(PhysicsObject p: toRemove)
			objects.remove(p);
		
		toRemove.clear();
		
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
						
						CollisionEvent event = new CollisionEvent(p, o);
						this.collisionEventDistributor.invoke(event);
						
						if(!event.isCanceled()){
						
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
						//I think proper momentum calculations should be implemented, but
						//currently it just uses your idea of destroying the smaller object
						
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
			//forceBuffer.get(p).set(force);
		}
		
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
	
	public List<Shape> calculateTrajectory(int positions, int skip, double zoom){
		
		List<Shape> trajectories = new ArrayList<Shape>(positions * objects.size());
		PhysicsSystem copy = new PhysicsSystem(this);
		
		for(int i = 0; i < positions; i++){
			copy.update(i);
			if(i % skip == 0 && i != 0)
				for(PhysicsObject o: copy.objects){
					Vector2 position = o.getPosition().copy();
					position.subtract(copy.getCenter().position);
					position.multiply(zoom);
					Shape s = new Circle(position, o.getRadius() * zoom / 2);
					if(o == copy.getChar())
						s.color = new Vector4(0, 0.4, 0, 1);
					else
						s.color = new Vector4(0.5, 0.5, 0.5, 0.2);
					trajectories.add(s);
				}
		}
		return trajectories;
	}
	
	public List<PhysicsObject> calculateTrajectory(PhysicsObject p, int positions){
		List<PhysicsObject> trajectory = new ArrayList<PhysicsObject>(positions);
		PhysicsObject tmp = p.copy();
		Vector2 out = new Vector2();
		Vector2 buff = new Vector2();
		
		for(int i = 0; i < positions; i++){
			buff.set(0, 0);
			for(PhysicsObject o : objects){
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
		if (charNum >= objects.size()){
			charNum -= 1;
			return getChar();
		}
		return objects.get(charNum);
	}
	public PhysicsObject getCenter(){
		if (centerNum >= objects.size()){
			centerNum -= 1;
			return getCenter();
		}
		return objects.get(centerNum);
	}
	
	public List<PhysicsObject> getObj(){
		return objects;
	}
	
}
