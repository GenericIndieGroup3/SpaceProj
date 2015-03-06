package obj;

import java.util.UUID;

import sensors.MissileSensor;
import Games.MainGame;
import Physics.PhysicsObject;
import Physics.PhysicsSystem;
import Structs.Vector2;
import sensors.Sensor;

public class Station extends Ship{
	
	private UUID starUUID;
	private Vector2 targetVel = new Vector2();
	private boolean clockwise;
	private Sensor<Missile> attackSensor;
	
	public Station(){
		super();
		this.attackSensor = new MissileSensor(this,10*getRadius());//,MainGame.mainGame.getActiveSystem());
	}
	public Station(Vector2 position, Vector2 velocity, double mass, UUID starUUID, boolean clockwise){
		super(position, velocity, mass);
		this.starUUID = starUUID;
		this.clockwise = clockwise;
		this.attackSensor = new MissileSensor(this,10*getRadius());//,MainGame.mainGame.getActiveSystem());
	}
	
	public Station(Vector2 position, double mass, UUID starUUID, boolean clockwise){
		super(position, mass);
		this.attackSensor = new MissileSensor(this,10*getRadius());//,MainGame.mainGame.getActiveSystem());
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
		attackSensor.update();
		if(attackSensor.isTriggered()){
			MainGame.mainGame.setup();
		}
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
