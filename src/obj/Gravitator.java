package obj;

import org.lwjgl.opengl.GL11;

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
	
	
	@Override
	public void draw(){
		int segments = 20;
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		for(int n = 0; n <= segments; ++n){
			double t = 2 * Math.PI * n / segments;
            GL11.glVertex2d(position.x + Math.sin(t)* getAltRadius(), position.y + Math.cos(t)* getAltRadius());
		}
		GL11.glEnd();
		GL11.glColor3d(0, 1, 0);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for( int n = 0; n <= segments; ++n ) {
	            double t = 2 * Math.PI * n / segments;
	            GL11.glVertex2d(position.x + Math.sin(t)* getRadius(), position.y + Math.cos(t)* getRadius());
	        }
		GL11.glEnd();
	}
	
}