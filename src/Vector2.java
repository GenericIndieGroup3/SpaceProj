//basic Vector class with x and y values
public class Vector2 {

	//Should these be floats our doubles?
	//How much precision do we need for every physics calculation?
	
	public float x;
	public float y;
	
	public Vector2(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	//Add is the equivalent of '+' on a vector, AddTo is '+='
	public Vector2 add(Vector2 other){
		return new Vector2(x + other.x, y + other.y);
	}
	public void addTo(Vector2 other){
		x += other.x;
		y += other.y;
	}
	
	public Vector2 subtract(Vector2 other){
		return new Vector2(x - other.x, y - other.y);
	}
	public void subtractTo(Vector2 other){
		x -= other.x;
		y -= other.y;
	}
	
	
}
