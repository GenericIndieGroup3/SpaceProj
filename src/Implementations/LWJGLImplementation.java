package Implementations;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import Games.GameInterface;
import Games.MainGame;
import Physics.PhysicsObject;
import Util.Vector2;


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
	public void draw() {
		
		
		//This shouldn't be in draw, but...
		keepUpdating = !Display.isCloseRequested();
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		
		
		
		//Graphics is supposed to be independent of game, but...
		MainGame mg = (MainGame) game;
		for(PhysicsObject object : mg.physicsSystem.getObj()){
			circle(object.getPosition().x, object.getPosition().y, object.getRadius() * 30, 100);
		}
		
		Display.update();
		
	}
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void circle(double d, double e, double f, int segments)
    {
        GL11.glBegin( GL11.GL_POLYGON);
            //GL11.glVertex2d(d, e);
            for( int n = 0; n <= segments; ++n ) {
                double t = 2 * Math.PI * n / segments;
                //GL11.glColor3f(0, n, 0);
                GL11.glVertex2d(d + Math.sin(t)*f, e + Math.cos(t)*f);
            }
        GL11.glEnd();
    }
	



}
