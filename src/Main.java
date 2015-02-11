import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;




//import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;

public class Main {
	
	private int screenWidth = 1500;
	private int screenHeight = 800;
	private double pixelToUnitRatio = 10;
	
	public Main(){
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth , screenHeight));
		    Display.create();
		}
		catch (LWJGLException e) {
	        e.printStackTrace();
	        System.exit(0);
		}
		
		//GL11.glLoadIdentity();
	    GL11.glOrtho(-screenWidth / pixelToUnitRatio, screenWidth / pixelToUnitRatio, -screenHeight / pixelToUnitRatio, screenHeight / pixelToUnitRatio, 1, -1);
	    GL11.glMatrixMode(GL11.GL_MODELVIEW);
	    
	    //EXECUTES INIT LOGIC
	    this.init();
	    
	    //number of current frame
	    int frameNum = 0;
	    //time since last frame, not currently updated
	    double deltaTime = 0.00001;
	    //loop until window is closed
	    while (!Display.isCloseRequested()) {
	    	
	        // Clear the screen and depth buffer
	        glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
	            
	        //UPDATE LOGIC
	        this.update(frameNum, deltaTime);
	        
	        
	        Display.update();
	       // pixelToUnitRatio *= 0.9;
			//GL11.glOrtho(0.9, 0.9, 0.9, 0.9, 1, -1);
			//GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        frameNum++;
	    }
	}
	
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
	
	 
	private PhysicsSystem physicsSystem;
	
	public void init(){
		physicsSystem = new PhysicsSystem();
		physicsSystem.addObj(new PhysicsObject(
				new Vector2(),
				new Vector2(),
				10,
				100
		));
		physicsSystem.addObj(new PhysicsObject(
				new Vector2(-100, 0),
				new Vector2(0, -0.5),
				1,
				1
		));
	}
	
	public void update(int frameNum, double deltaTime){
		physicsSystem.update();
		for(PhysicsObject object : physicsSystem.getObj()){
			circle(object.getPosition().x, object.getPosition().y, object.getRadius(), 100);
		}
	}
	
	public static void main(String[] args){
		Main m = new Main();
	}
}
