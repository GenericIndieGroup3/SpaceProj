package Structs;


public class Polygon extends Shape {

	public final Point[] points;
	
	public Polygon(Point[] points){
		this.points = points;
	}
	public Polygon(List<Point> points){
		this.points = (Point[]) points.toArray();
	}
}
