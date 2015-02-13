package Structs;
public class Vector2{
	
	//Double Vector class with x and y
	
	public double x;
	public double y;
	
	public Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2(){
		this.x = 0;
		this.y = 0;
	}
	
	public boolean equals(Vector2 a){
		return (x == a.x && y == a.y);
	}
	public Vector2 copy(){
		return new Vector2(x, y);
	}
	
	public void set(Vector2 a){
		x = a.x;
		y = a.y;
	}
	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector2 a){
		x += a.x;
		y += a.y;
	}
	public void add(double x, double y){
		x += x;
		x += y;
	}
	public void subtract(Vector2 a){
		x -= a.x;
		y -= a.y;
	}
	public void subtract(double x, double y){
		this.x -= x;
		this.y -= y;
	}
	public void multiply(Vector2 a){
		x *= a.x;
		y *= a.y;
	}
	public void multiply(double x, double y){
		this.x *= x;
		this.y *= y;
	}
	public void multiply(double a){
		x *= a;
		y *= a;
	}
	//Returns magnitude of this vector
	public double getMagnitude(){
		//TODO cache this for optimization
		return Math.sqrt(x * x + y * y);
	}
	//Sets magnitude 
	public void magnitude(double mag){
		double ratio = mag / getMagnitude();
		multiply(ratio);
	}
	//Sets this vector to have mag of 1
	public void normalize(){
		magnitude(1);
	}
	public void negate(){
		multiply(-1);
	}
	
	//TODO do in place and chose number of rotations
	//TODO rotate by number of degrees?
	public Vector2 createClockwisePerpendicular(){
		return new Vector2(y, -x);
	}
	public Vector2 createCounterClockwisePerpendicular(){
		return new Vector2(-y, x);
	}
	
	
	public static Vector2 addVectors(Vector2[] forces){
		Vector2 netForce = new Vector2();
		for(Vector2 force : forces){
			netForce.add(force);
		}
		return netForce;
	}
	
	
	
}
