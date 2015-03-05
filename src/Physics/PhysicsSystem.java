package Physics;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Structs.Vector2;
import events.EventDistributor;
import events.types.AddObjectEvent;
import events.types.CollisionEvent;
import events.types.RemoveObjectEvent;

public class PhysicsSystem{
	
	private static final double GRAVITATIONAL_CONSTANT = 5;
	
	public int charNum = 2;
	public int centerNum = 0;
	
	
	
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
				//TODO n^2 complexity is not optimal, optimize with quad trees once done with mechanics
				if(o != p){
					if(checkCollision(p, o) && !toRemove.contains(p) && !toRemove.contains(o)){
						
						CollisionEvent event = new CollisionEvent(p, o);
						this.collisionEventDistributor.invoke(new CollisionEvent(p, o));
						
						if(!event.isCanceled()){
						
							if(p.getIMass() > o.getIMass()){
								toRemove.add(o);
							}
							else if(o.getIMass() > p.getIMass()){
								toRemove.add(p);
							}
							else if(o.getIMass() == p.getIMass()){
								toRemove.add(o);
							}
						}
						//I think proper momentum calculations should be implemented, but
						//currently it just uses your idea of destroying the smaller object
						
					}
					else{
						getGrav(p, o, grav);
						Vector2 a = new Vector2();
						p.calculateAcceleration(grav, a);
						p.accelerateA(a);
					}
				}
			}
		}
		
		for(PhysicsObject p: objects){
			p.updateVelocity(this);
			p.updatePosition();
			p.resetAcceleration();
			
			if(p.shouldBeRemoved){
				removeObj(p);
				p.isRemoved = true;
			}
		}
	}
	
	public List<PhysicsObject> getObj(){
		return objects;
	}
	
	private static Vector2 checkCollisionDistanceCache = new Vector2();
	/**
	 * Checks whether or not two PhysicsObjects are currently colliding.
	 * Assumes that all physics objects are circles.
	 * 
	 * @param objectA PhysicsObject to check collision on
	 * @param objectB PhysicsObject to check collision on
	 * @return whether or not the objects are colliding
	 */
	public boolean checkCollision(PhysicsObject objectA, PhysicsObject objectB){
		
		getDistance(objectA, objectB, checkCollisionDistanceCache);
		
		//If their distance between the two center points is less than the combined radii, the 2 objects collided
		return checkCollisionDistanceCache.getMagnitude() <= objectA.getRadius() + objectB.getRadius();
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
		distance = acceleration.constructPerpendicular(clockwise);
		//sets distance to the required velocity
		distance.magnitude(speed);
		//adds the other objects velocity so that it stays relative to it
		distance.add(sun.velocity);
		return distance;
		
	}
	/*
	public void calculateVelocityForCircularMotion(PhysicsObject planet, PhysicsObject sun, boolean clockwise, Vector2 velocityOut){
		velocityOut.set(velocityForCircularMotion(planet, sun, clockwise));
	}*/
	Vector2 calcVelDistCache = new Vector2();
	/**<p>
	 * Calculates the required velocity for circular motion around the sun,<br>
	 * and sets velocityOut to be this value. <br><br>
	 * This does take into account the current velocity of the sun, and adds<br>
	 * it to the calculated required velocity of the planet.<br><br>
	 * This does NOT take into account any other objects or future events, <br>
	 * and will not guarantee a circular orbit<br></p>
	 * @param planet the PhysicsObject doing the revolving
	 * @param sun PhysicsObject to revolve around, named sun for ease of use
	 * @param clockwise whether or not the velocity should be pointing clockwise
	 * @param velocityOut object to be modified to the required velocity for circular motion
	 */
	public void calculateVelocityForCircularMotion(PhysicsObject planet, PhysicsObject sun, boolean clockwise, Vector2 velocityOut){
		
		getDistance(sun, planet, calcVelDistCache);

		//Uses the fact that calcVelDistCache is pointing from sun to planet
		velocityOut.set(calcVelDistCache.constructPerpendicular(clockwise));
		
		double gravityMagnitude = getGravityMagnitude(planet.getGMass(), sun.getGMass(), calcVelDistCache.getMagnitude());
		//Uses Ac formula =>  Fc = mv^2 / r => v = sqrt(Fc * r / m)
		double velocityMagnitude = Math.sqrt(gravityMagnitude * calcVelDistCache.getMagnitude() / planet.getRadius());
		
		velocityOut.magnitude(velocityMagnitude);
		
		//adds the other objects velocity so that it stays relative to it
		velocityOut.add(sun.velocity);
		
	}
	
	/**<p>
	 * 	Calculates the gravitational force on objectA from objectB, and on objectB from objectA.<br>
	 *  Sets gravOnAOut and gravOnBOut to these two values respectively.<br><br>
	 *  If the distance between these two objects is 0, a force of 0 is returned.<br>
	 * @param objectA first of 2 objects to calculate gravity on
	 * @param objectB second of 2 objects to calculate gravity on
	 * @param gravOnAOut is set to the force of gravity on objectA produced by objectB
	 * @param gravOnBOut is set to the force of gravity on objectB produced by objectA
	 * @throws NullPointerException if objectA, objectB, gravOnAOut, or gravOnBOut is null
	 */
	public void getGrav(PhysicsObject objectA, PhysicsObject objectB, Vector2 gravOnAOut, Vector2 gravOnBOut){
		
		getGrav(objectA, objectB, gravOnAOut);
		
		gravOnBOut.set(gravOnAOut);
		gravOnBOut.negate();
	}
	Vector2 getGravDistanceCache = new Vector2();
	/** <p>
	 * 	Calculates the gravitational force on objectA from objectB.<br>
	 *  Sets gravOnAOut to this value.<br><br>
	 *  If the distance between these two objects is 0, a force of 0 is returned.<br>
	 * @param objectA object on which gravity is acting
	 * @param objectB object that produces gravity
	 * @param gravOnAOut is set to the force of gravity on objectA produced by objectB
	 * @throws NullPointerException if objectA, objectB, or gravOnAOut is null
	 */
	public void getGrav(PhysicsObject objectA, PhysicsObject objectB, Vector2 gravOnAOut){
		
		getDistance(objectA, objectB, getGravDistanceCache);
		double gravMagnitude = getGravityMagnitude(objectA.getGMass(), objectB.getGMass(), getGravDistanceCache.getMagnitude());
		
		gravOnAOut.set(getGravDistanceCache);
		gravOnAOut.magnitude(gravMagnitude);
	}
	
	/**
	 * <p>
	 * Returns the magnitude of the force of gravity that would be caused by two objects of 
	 * gMassA and gMassB, if the distance between them is equal to distance.<br>Returns 0 if distance is 0.<br>
	 * 
	 * @param gMassA mass of first object
	 * @param gMassB mass of second object
	 * @param distance distance between the two objects
	 * @return magnitude of the force of gravity caused by the aforementioned objects
	 */
	public double getGravityMagnitude(double gMassA, double gMassB, double distance){
		
		if(distance == 0)
			return 0;
		else
			return GRAVITATIONAL_CONSTANT * gMassA * gMassB / (distance * distance); 
	}
	
	/**
	 * <p>Sets distanceOut to be the distance from objectA to objectB, such that <br>
	 * objectA.position + distanceOut = objectB.position. <br>
	 * @param objectA
	 * @param objectB
	 * @param distanceOut is set to be distance from objectA to objectB
	 * @throws NullPointerException if objectA, objectB, or distanceOut is null */
	public void getDistance(PhysicsObject objectA, PhysicsObject objectB, Vector2 distanceOut){
		
		distanceOut.set(objectB.getPosition());
		distanceOut.subtract(objectA.getPosition());
	}
	Vector2 ansCache = new Vector2();
	public double distanceTo(PhysicsObject p, PhysicsObject q){
		getDistance(p,q,ansCache);
		return ansCache.getMagnitude();
	}
	Vector2 distCache = new Vector2();
	public double distanceTo(Vector2 pos, PhysicsObject q){
		distCache.set(pos);
		distCache.subtract(q.getPosition());
		return distCache.getMagnitude();
	}
	
}
