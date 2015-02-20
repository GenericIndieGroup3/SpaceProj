package Implementations;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Games.GameInterface;
import Games.MainGame;
import Physics.PhysicsObject;
import Structs.Circle;
import Structs.Point;
import Structs.Polygon;
import Structs.Vector2;
import Structs.Vector4;

public class LWJGLImplementation extends ImplementationAbstract {

	
	public LWJGLImplementation(GameInterface game, Vector2 screenDimensions, double unitToPixelRatio) {
		super(game, screenDimensions, unitToPixelRatio);
	}

	@Override
	public void create() {
		try {
			Display.setDisplayMode(new DisplayMode((int)screenDimensions.x , (int)screenDimensions.y));
		    Display.create();
		}
		catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
		}
		
		//GL11.glLoadIdentity();
		double w = screenDimensions.x / unitToPixelRatio;
		double h = screenDimensions.y / unitToPixelRatio;
	    GL11.glOrtho(-w, w, -h, h, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
	}

	@Override
	public void input() {
		
		
		//This shouldn't be in draw, but...
		keepUpdating = !Display.isCloseRequested();
		
		
		
	}
	
	@Override
	public void remove() {
		Display.destroy();
		
	}
	@Override
	public void clear(){
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
	}
	@Override
	public void flip(){
		Display.update();
	}
	@Override
	public void point(Point p){
		GL11.glBegin(GL11.GL_POINT);
			Vector4 color = p.color();
			GL11.glColor4d(color.a, color.b, color.c, color.d);
			GL11.glVertex2d(p.position.x , p.position.y);
		GL11.glEnd();
	}
	@Override
	public void polygon(Polygon pol){
		
		Vector4 defaultColor = pol.color();
		
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			for(Point p : pol.points){
				
				Vector4 color;
				if(p.isColorDefined())
					color = p.color();
				else
					color = defaultColor;
				
				GL11.glColor4d(color.a, color.b, color.c, color.d);
				GL11.glVertex2d(p.position.x , p.position.y);
				
			}
		GL11.glEnd();
		
	}
	@Override
	public void circle(Circle c){
		//circle(0, 0, 2000, 100);
		//TODO add logic to optimize segment count depending on zoom
		int segments = 20;
		Vector4 color = c.color();
		
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			GL11.glColor4d(color.a, color.b, color.c, color.d);
			for( int n = 0; n <= segments; ++n ) {
	            double t = 2 * Math.PI * n / segments;
	           // GL11.glColor3f(0, n, 0);
	            GL11.glVertex2d(c.center.x + Math.sin(t)*c.radius, c.center.y + Math.cos(t)* c.radius);
	        }
		GL11.glEnd();
	}
	
	//public void type(String text,TrueTypeFont font){
		
	//}

	
	
}
