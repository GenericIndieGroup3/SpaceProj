package Implementations;

import processing.core.PApplet;
import Games.MainGame;


public class ProcessingExtension extends PApplet{
	//Neccesary to avoid conflicts with my repeating update loop
	
	//terrible design, but who cares.
	public static ProcessingExtension a;
	public static ProcessingImplementation b;
	public void setup(){
		String[] args = new String[0];
		a = this;
		fill(120);
		MainGame.main(args);
		
	}
	
	public void draw(){
		//this replaces my own update loop, which is never called because it is overriden by an empty function
		b.update();
		b.draw(b.game.drawShapes());
	}
	
}
