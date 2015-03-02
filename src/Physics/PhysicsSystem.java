package Physics;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import obj.Gravitator;
import util.Vars;
import Structs.Vector2;
import events.EventDistributor;
import events.types.AddObjectEvent;
import events.types.CollisionEvent;
import events.types.RemoveObjectEvent;

public class PhysicsSystem{
	
	private static final double GRAVITATIONAL_CONSTANT = Vars.GRAVITATIONAL_CONSTANT;
	
	public int charNum = 2;
	public int centerNum = 0;
	
	public Vector2 centerVector = new Vector2(-500, 0);
	private PhysicsObject centerObject = null;
	
	public EventDistributor<CollisionEvent> collisionEventDistributor = new EventDistributor<CollisionEvent>();
	public EventDistributor<AddObjectEvent> addObjectEventDistributor = new EventDistributor<AddObjectEvent>();
	public EventDistributor<RemoveObjectEvent> removeObjectEventDistributor = new EventDistributor<RemoveObjectEvent>();
	
	public ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	
	public PhysicsSystem(PhysicsSystem copyFrom){
		this();
		objects = new ArrayList<PhysicsObject>(copyFrom.objects.size());
		for(PhysicsObject o: copyFrom.objects){
			PhysicsObject p = (PhysicsObject) o.copy();
			objects.add(p);
			if(copyFrom.toRemove.contains(o))
				toRemove.add(p);
		}
		charNum = copyFrom.charNum;
		centerNum = copyFrom.centerNum;
	}
	public PhysicsSystem(){
	}
	
	public PhysicsSystem(PhysicsObject[] obj){
		this();
		for(PhysicsObject o : obj)
			objects.add(o);
	}
	public void removeObj(PhysicsObject o){
		toRemove.add(o);
	}
	public void addObj(PhysicsObject o){
		AddObjectEvent event = new AddObjectEvent(o);
		addObjectEventDistributor.invoke(event);
		if(!event.isCanceled())
			objects.add(o);
	}
	public PhysicsObject getObject(UUID uuid){
		for(PhysicsObject o : objects){
			if(o.getUUID().equals(uuid))
				return o;
		}
		return null;
	}
	
	//TODO this can easily be improved with a collision quad-tree
	private boolean checkCollision(PhysicsObject a, PhysicsObject b){
	
		Vector2 distance = b.getPosition().copy();
		distance.subtract(a.getPosition());
		
		double distanceMag = distance.getMagnitude();
		double minDistance = a.getRadius() + b.getRadius();
		
		return distanceMag <= minDistance;
	}
	
	List<PhysicsObject> toRemove = new ArrayList<PhysicsObject>();
	public void update(){

		for(PhysicsObject p: toRemove){
			RemoveObjectEvent event = new RemoveObjectEvent(p);
			removeObjectEventDistributor.invoke(event);
			if(!event.isCanceled())
				objects.remove(p);
			
		}
		toRemove.clear();
		
		
		Vector2 grav = new Vector2();
		for(PhysicsObject p : objects){
			for(PhysicsObject o: objects){
				//TODO n^2 complexity is really bad and slow, once we get the basic mechanics
				//we need to heavily optimize this using quad trees and such and centers of mass
				if(o != p){
					if(checkCollision(p, o) && !toRemove.contains(p) && !toRemove.contains(o)){
						
						CollisionEvent event = new CollisionEvent(p, o);
						this.collisionEventDistributor.invoke(new CollisionEvent(p, o));
						
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
						Vector2 a = new Vector2();
						p.calculateAcceleration(grav, a);
						p.accelerateA(a);
					}
				}
			}
		}
		
		for(PhysicsObject p: objects){
			p.updateVelocity();
			p.updatePosition();
			p.resetAcceleration();
			
			if(p.shouldBeRemoved){
				removeObj(p);
				p.isRemoved = true;
			}
		}
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
	
	
	Vector2 centerCache = new Vector2();
	
	public Vector2 getCenter(){
		if(centerObject != null)
			centerCache.set(centerObject.position);
		else
			centerCache.set(0, 0);
		centerCache.add(centerVector);
		
		return centerCache;
		
	}
	public List<PhysicsObject> getObj(){
		return objects;
	}
	
}
