package Implementations;
import Games.GameInterface;
import Structs.Circle;
import Structs.Point;
import Structs.Polygon;
import Structs.Vector2;
import Structs.Vector4;


public class ProcessingImplementation extends ImplementationAbstract {
	//Currently this exists just to make sure that the graphics abstracting works
	//and that it is easily possible to switch graphics libraries
	
	ProcessingExtension a;
	
	public ProcessingImplementation(GameInterface game, Vector2 screenDimensions, double unitToPixelRatio) {
		super(game, screenDimensions, unitToPixelRatio);
	}

	@Override
	public void beginUpdating(){
		
	}
	@Override
	public void create() {
		a = ProcessingExtension.a;
		a.size((int) screenDimensions.x, (int) screenDimensions.y);
		a.background(150);
		a.b = this;
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		a.background(0);
	}

	@Override
	public void point(Point p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void polygon(Polygon p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void circle(Circle c) {
		
		
		Vector4 color = c.color();
		a.fill((float)color.a, (float)color.b, (float)color.c);
		a.stroke(120);
		
		float x = (float) (c.center.x * unitToPixelRatio + 500);
		float y = (float) (c.center.y * unitToPixelRatio + 500);
		float r = (float) (c.radius * unitToPixelRatio) / 2;
		a.ellipse(x, y, r, r);
	}

	@Override
	public void flip() {
		// TODO Auto-generated method stub
		
	}
	
	
}
