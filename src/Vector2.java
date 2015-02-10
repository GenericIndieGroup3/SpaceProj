public class Vector2{
	
	public static boolean runUnitTests(){
		//returns true if it fails the tests
		boolean check = false;
		check = check || !new Vector2(3, 4).equals(new Vector2(1, 2).add(new Vector2(2, 2)));
		check = check || !new Vector2(3, 4).equals(new Vector2(7, 6).subtract(new Vector2(4, 2)));
		check = check || !new Vector2(6, 4).equals(new Vector2(3, 2).multiply(new Vector2(2, 2)));
		check = check || !(new Vector2(4, 9).magnitude() == (Math.sqrt(16 + 81)));
		check = check || !((float)new Vector2(4, 8).normalize().magnitude() == 1);
		return check;
	}
	//Double Vector class with x and y
	//
	
	//Add is like '+', setAdd is like '+='
	
	public double x;
	public double y;
	
	public Vector2(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setEquals(Vector2 a){
		this.x = a.x;
		this.y = a.y;
	}
	
	public boolean equals(Vector2 a){
		return (x == a.x && y == a.y);
	}
	
	public Vector2 add(Vector2 b){
		return new Vector2(x + b.x, y + b.y);
	}
	public void setAdd(Vector2 a){
		this.setEquals(add(a));
	}
	
	public Vector2 subtract(Vector2 b){
		return new Vector2(x - b.x, y - b.y);
	}
	public void setSubtract(Vector2 a){
		this.setEquals(subtract(a));
	}
	
	public Vector2 multiply(Vector2 b){
		return new Vector2(x * b.x, y * b.y);
	}
	public Vector2 multiply(double a){
		return new Vector2(x * a, y * a);
	}
	public void setMultiply(Vector2 a){
		this.setEquals(multiply(a));
	}
	public void setMultiply(double a){
		this.setEquals(multiply(a));
	}
	
	public double magnitude(){
		return Math.sqrt(x * x + y * y);
	}
	public void setMagnitude(double mag){
		double ratio = mag / magnitude();
		setMultiply(ratio);
	}
	
	//Sets the vector's magnitude to 1
	public Vector2 normalize(){
		Vector2 vec =  new Vector2(0, 0);
		vec.setEquals(this);
		vec.setMagnitude(1);
		return vec;
		
	}
	public void setNormalize(){
		this.setEquals(normalize());
	}
	
}
