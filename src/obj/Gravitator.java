package obj;

import org.lwjgl.opengl.GL11;

import Physics.PhysicsObject;
import Structs.Vector2;

public class Gravitator extends Ship {
	
	double gMass, iMass;
	
	public Gravitator(){
		
	}
	public Gravitator(Vector2 position, Vector2 velocity, double gravitationalMass, double inertialMass){
		super(position, velocity, 0);
		gMass = gravitationalMass;
		iMass = inertialMass;
	}
	
	public Gravitator(Vector2 position, double mass){
		super(position, mass);
	}
	
	public void addMass(double m){
		if(this.gMass + m > 0)
			this.gMass += m;
	}
	
	public void setMass(double m){
		this.gMass = m;
	}
	
	@Override 
	public double getGMass(){
		return gMass;
	}
	@Override
	public double getIMass(){
		return iMass;
	}
	@Override
	public PhysicsObject copy(){
		Gravitator o = new Gravitator();
		o.set(this);
		return o;
	}
	public void set(Gravitator a){
		super.set(a);
		this.gMass = a.getGMass();
		this.iMass = a.getIMass();
	}
	
}