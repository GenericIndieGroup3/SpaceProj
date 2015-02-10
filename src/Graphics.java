import static org.lwjgl.opengl.GL11.*;


public class Graphics {
//This will be the temporary (or permanent) graphics API. Actually I don't know, it seems like it would
//be better to use our own graphics API, so at any point we can change implementations, or
//even have different ones.

//On the other hand, if we only use our own graphics API, then we pretty much have to
//write wrappers for every single openGL function that we use.

	public void circle(double x, double y, double r, int segments)
    {
        glBegin( GL_TRIANGLE_FAN);
            glVertex2d(x, y);
            for( int n = 0; n <= segments; n++ ) {
                double t = 2 * Math.PI * n / segments;
                glVertex2d(x + Math.sin(t)*r, y + Math.cos(t)*r);
            }
        glEnd();
    }
}

