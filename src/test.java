import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;


public class test {

	public static void main(String[] args){
		
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    boolean keepUpdating = true;
	    while(keepUpdating){
	    	double bla = 12312321.23212;
	    	keepUpdating = !Display.isCloseRequested();
	    	bla = Math.sqrt(bla);
	    	if(Keyboard.isKeyDown(Keyboard.KEY_F))
	    		keepUpdating = false;
			
			//Display.destroy();
	    }
	    Display.destroy();

	}
}
