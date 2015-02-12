package Implementations;
import Games.GameInterface;
import Structs.Circle;
import Structs.Point;
import Structs.Polygon;
import Structs.Shape;
import Structs.Vector2;


public abstract class ImplementationAbstract {

	protected Vector2 screenDimensions;
	protected double unitToPixelRatio;
	
	protected GameInterface game;
	
	protected int frameNum = 0;
	protected double deltaTime = 0.0001;
	
	protected boolean keepUpdating = true;
	
	public ImplementationAbstract(GameInterface game, Vector2 screenDimensions, double unitToPixelRatio){
		this.screenDimensions = screenDimensions;
		this.unitToPixelRatio = unitToPixelRatio;
		this.game = game;
		
		create();
		
		this.game.setup();
		beginUpdating();
		remove();
		
	}
	
	public void beginUpdating(){
		while(keepUpdating){
			update();
			//TODO add logic to skip draw cycles every now and then if lagging
			draw(this.game.drawShapes());
		}
	}
	//If this is overriden, make sure to call it in the override method
	public void update(){
		frameNum++;
		//TODO calculate deltaTime
		deltaTime = 0.0001;
		input();
		game.update(frameNum, deltaTime);
	}
	
	public void draw(Shape[] shapes){
		clear();
		
		for(Shape s : shapes){
			if( s instanceof Circle )
				circle((Circle) s);
			else if(s instanceof Polygon)
				polygon((Polygon) s);
			else if(s instanceof Point)
				point((Point) s);
			else{
				//unimplemented shape
			}
		}
		
		flip();
	}
	
	public abstract void create();
	//Somehowreaddata from this
	public abstract void input();
	public abstract void remove();
	
	public abstract void clear();
	public abstract void point(Point p);
	public abstract void polygon(Polygon p);
	public abstract void circle(Circle c);
	public abstract void flip();
	
}
