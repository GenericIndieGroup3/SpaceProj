package Implementations;

import processing.core.PApplet;
import Games.MainGame;


public class ProcessingExtension extends PApplet{
	public static ProcessingExtension a;
	public static ProcessingImplementation b;
	public void setup(){
		String[] args = new String[0];
		a = this;
		fill(120);
		MainGame.main(args);
		
	}
	
	public void draw(){
		b.update();
		//TODO add logic to skip draw cycles every now and then if lagging
		b.draw(b.game.drawShapes());
	}
	
}
