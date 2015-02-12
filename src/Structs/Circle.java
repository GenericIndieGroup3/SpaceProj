package Structs;

public class Circle extends Shape {

	public Vector2 center;
	public double radius;
	
	public Circle(double x, double y, double r){
		center = new Vector2(x, y);
		radius = r;
	}
	
	public Circle(double x, double y, double r, Vector4 color){
		this(x, y, r);
		this.color = color;
	}
	
	public Circle(Vector2 center, double r){
		this.center = center;
		this.radius = r;
	}
}
