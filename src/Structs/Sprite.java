package Structs;

import java.util.ArrayList;
import java.util.List;

public class Sprite {

	Vector2 position = new Vector2();
	List<Shape> shapes = new ArrayList<Shape>();
	
	public Sprite(){
	}
	
	public Sprite(List<Shape> shapes){
		this.shapes = shapes;
	}
	
	public void setPosition(Vector2 position){
		this.position.set(position);
	}
	public void setPoisition(double x, double y){
		this.position.set(x, y);
	}
	
	public void addShape(Shape shape){
		shapes.add(shape);
	}
	
	public List<Shape> getShapes(){
		return shapes;
	}
}
