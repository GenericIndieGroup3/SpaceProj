package obj;

import Physics.PhysicsObject;
import Structs.Vector2;

public class Gravitator extends Ship {
	
	public Gravitator(){
		
	}
	public Gravitator(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass){
		super(position, velocity, gravitationalMass, inertialMass);
	}
	
	public Gravitator(Vector2 position, double mass){
		super(position, mass);
	}
	
	public void addMass(double m){
		if(this.gravitationalMass + m > 0)
			this.gravitationalMass += m;
	}
	
	public void setMass(double m){
		this.gravitationalMass = m;
	}
	
	@Override
	public PhysicsObject copy(){
		Gravitator o = new Gravitator();
		o.set(this);
		return o;
	}
	
	public void set(Gravitator a){
		super.set(a);
	}
	
}