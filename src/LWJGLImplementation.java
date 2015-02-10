import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11.*;
  
public class LWJGLImplementation {
  
    public void start() {
        try {
        Display.setDisplayMode(new DisplayMode(800,600));
        Display.create();
    } catch (LWJGLException e) {
        e.printStackTrace();
        System.exit(0);
    }
  
    // init OpenGL
    GL11.glMatrixMode(GL11.GL_PROJECTION);
    GL11.glLoadIdentity();
    GL11.glOrtho(0, 800, 0, 600, 1, -1);
    GL11.glMatrixMode(GL11.GL_MODELVIEW);
  
    float x = 0;
    while (!Display.isCloseRequested()) {
        // Clear the screen and depth buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
         
        // set the color of the quad (R,G,B,A)
        GL11.glColor3f(0.5f,0.5f,1.0f);
             
        // draw quad
            
        GL11.glBegin(GL11.GL_QUADS);
        	GL11.glVertex2f(100,100);
	        GL11.glVertex2f(100+200,100);
	        GL11.glVertex2f(100+200,100+200);
	        GL11.glVertex2f(100,100+200);
        GL11.glEnd();

        this.circle(500 + x,  300,  200,  1000);
        x += 0.01;
        Display.update();
    }
  
    Display.destroy();
    }
    
    //Draws a circle at position x, y, radius r, using the given number of line segments
    public void circle(float x, float y, float r, int segments)
    {
        GL11.glBegin( GL11.GL_TRIANGLE_FAN);
            GL11.glVertex2f(x, y);
            for( int n = 0; n <= segments; ++n ) {
                double t = 2 * Math.PI * n / segments;
                GL11.glVertex2d(x + Math.sin(t)*r, y + Math.cos(t)*r);
            }
        GL11.glEnd();
    }
    public static void main(String[] argv) {
        LWJGLImplementation LWJGLImplementation = new LWJGLImplementation();
        LWJGLImplementation.start();
    }
}