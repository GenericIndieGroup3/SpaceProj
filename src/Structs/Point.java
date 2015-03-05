package Structs;

public class Point extends Shape{
	
	public Vector2 position;
	
	public Point(double x, double y){
		this.position = new Vector2(x, y);
	}
	
	public Point(Vector2 position, Vector4 color){
		this.position = position;
		this.color = color;
	}
}
