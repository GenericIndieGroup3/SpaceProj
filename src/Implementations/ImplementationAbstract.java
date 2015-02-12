package Implementations;
import Util.Vector2;
import Games.GameInterface;


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
			draw();
		}
	}
	//If this is overriden, make sure to call it in the override method
	public void update(){
		frameNum++;
		//TODO calculate deltaTime
		deltaTime = 0.0001;
		game.update(frameNum, deltaTime);
	}
	
	public abstract void create();
	public abstract void draw();
	public abstract void remove();
	public abstract void circle(double x, double y, double r, int segments);
	
}
