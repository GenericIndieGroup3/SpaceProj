package obj;

import java.util.UUID;

import util.Vars;
import Games.MainGame;
import Physics.PhysicsObject;
import Structs.Vector2;

public class Station extends Ship{
	private static final double GRAVITATIONAL_CONSTANT = Vars.GRAVITATIONAL_CONSTANT;
	
	private UUID starUUID;
	private Vector2 targetVel = new Vector2();
	private Vector2 gravPull = new Vector2();
	private boolean clockwise;
	private Vector2 acceleration = new Vector2();
	private Vector2 distance = new Vector2();
	
	public Station(){
		super();
	}
	public Station(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass, UUID starUUID, boolean clockwise){
		super(position, velocity, gravitationalMass, inertialMass);
		this.starUUID = starUUID;
		this.clockwise = clockwise;
	}
	
	public Station(Vector2 position, double mass, UUID starUUID, boolean clockwise){
		super(position, mass);
		this.starUUID = starUUID;
		this.clockwise = clockwise;
	}
	
	private void getGrav(){
		distance.set(getStar().getPosition().copy());
		distance.subtract(this.getPosition());
		
		double distanceMag = distance.getMagnitude();
		if(distanceMag == 0){
			gravPull.set(0, 0);
			return;
		}
		
		double gravForceMag = GRAVITATIONAL_CONSTANT * getGravitationalMass() * getStar().getGravitationalMass() / (distanceMag * distanceMag); 
		gravPull.set(distance);
		gravPull.multiply(gravForceMag);
	}
	
	private PhysicsObject getStar(){
		return MainGame.physicsSystem.getObject(starUUID);
	}
	
	private void getTargetVel(){
		gravPull.clear();
		getGrav();
		this.calculateAcceleration(gravPull, acceleration);
		distance.set(getStar().getPosition().copy());
		distance.subtract(this.getPosition());
		double radius = distance.getMagnitude();
		double speed = Math.sqrt(acceleration.getMagnitude() * radius);
		if(clockwise)
			distance = acceleration.createClockwisePerpendicular();
		else
			distance = acceleration.createCounterClockwisePerpendicular();
		distance.magnitude(speed);
		distance.add(getStar().velocity);
		targetVel.set(distance);
	}
	
	public void updatePosition(){
		if(getStar() != null){
			this.getTargetVel();
			this.velocity.set(targetVel);
		}
		position.add(velocity);
	}
	
	@Override
	public PhysicsObject copy(){
		Station o = new Station();
		o.set(this);
		return o;
	}
	
	public void set(Station a){
		super.set(a);
		this.starUUID = a.starUUID;
		this.clockwise = a.clockwise;
	}
	
}
