package obj;

import util.Vars;
import Physics.PhysicsObject;
import Structs.Vector2;

public class Station extends Ship{
	private static final double GRAVITATIONAL_CONSTANT = Vars.GRAVITATIONAL_CONSTANT;
	
	private PhysicsObject star;
	private Vector2 targetVel = new Vector2();
	private Vector2 gravPull = new Vector2();
	private boolean clockwise;
	private Vector2 acceleration = new Vector2();
	private Vector2 distance = new Vector2();
	
	public Station(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass, PhysicsObject star, boolean clockwise){
		super(position, velocity, gravitationalMass, inertialMass);
		this.star = star;
		this.clockwise = clockwise;
	}
	
	public Station(Vector2 position, double mass, PhysicsObject star, boolean clockwise){
		super(position, mass);
		this.star = star;
		this.clockwise = clockwise;
	}
	
	private void getGrav(){
		distance.set(star.getPosition().copy());
		distance.subtract(this.getPosition());
		
		double distanceMag = distance.getMagnitude();
		if(distanceMag == 0){
			gravPull.set(0, 0);
			return;
		}
		
		double gravForceMag = GRAVITATIONAL_CONSTANT * this.getGravitationalMass() * star.getGravitationalMass() / (distanceMag * distanceMag); 
		gravPull.set(distance);
		gravPull.multiply(gravForceMag);
	}
	
	private void getTargetVel(){
		gravPull.clear();
		getGrav();
		this.calculateAcceleration(gravPull, acceleration);
		distance.set(star.getPosition().copy());
		distance.subtract(this.getPosition());
		double radius = distance.getMagnitude();
		double speed = Math.sqrt(acceleration.getMagnitude() * radius);
		if(clockwise)
			distance = acceleration.createClockwisePerpendicular();
		else
			distance = acceleration.createCounterClockwisePerpendicular();
		distance.magnitude(speed);
		distance.add(star.velocity);
		targetVel.set(distance);
	}
	
	public void updatePosition(){
		this.getTargetVel();
		this.velocity.set(targetVel);
		position.add(velocity);
	}
	
	@Override
	public Object copy(){
		return new Station(this.position.copy(), this.velocity.copy(), this.gravitationalMass, this.inertialMass, star, clockwise);
	}
	
}
