package obj;

import java.util.UUID;

import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Vector2;

public class Station extends Ship{
	
	private UUID starUUID;
	private Vector2 targetVel = new Vector2();
	private boolean clockwise;
	
	public Station(){
		super();
<<<<<<< HEAD
		this.attackSensor = new MissileSensor(this,10*getRadius());//,MainGame.mainGame.getActiveSystem());
=======
>>>>>>> FETCH_HEAD
	}
	public Station(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass, UUID starUUID, boolean clockwise){
		super(position, velocity, gravitationalMass, inertialMass);
		this.starUUID = starUUID;
		this.clockwise = clockwise;
<<<<<<< HEAD
		this.attackSensor = new MissileSensor(this,10*getRadius());//,MainGame.mainGame.getActiveSystem());
=======
>>>>>>> FETCH_HEAD
	}
	
	public Station(Vector2 position, double mass, UUID starUUID, boolean clockwise){
		super(position, mass);
		this.starUUID = starUUID;
		this.clockwise = clockwise;
	}
	
	private PhysicsObject getStar(PhysicsSystem parentSystem){
		return parentSystem.getObject(starUUID);
	}

	@Override
	public void updateVelocity(PhysicsSystem parentSystem){
		PhysicsObject star = getStar(parentSystem);
		if(star != null){
			parentSystem.calculateVelocityForCircularMotion(this, star, clockwise, targetVel);
			this.velocity.set(targetVel);
		}
		else
			super.updateVelocity(parentSystem);
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
