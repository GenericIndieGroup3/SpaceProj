import processing.core.PApplet;


public class ProcessingImplementation extends PApplet {
	//Yes, yes, we are using an openGL implementation for the final product
	//and processing is horrible. However, it'll take me a while to learn
	//how to use lwjgl, and I want to instantly see if my code works
	
	public void setup(){
		size(1500, 1200);
		boolean check = Vector2.runUnitTests();
		if(check)
			background(255, 0, 0);
		else
			background(0, 255, 0);
	}
}
