package Structs;

public class Shape {

	public Vector4 color;
	private static Vector4 defaultColor = new Vector4(255, 255, 255, 0);
	
	public Vector4 color(){
		if(color != null)
			return color;
		else
			return defaultColor;
	}
	
	public boolean isColorDefined(){
		return color != null;
	}
}
