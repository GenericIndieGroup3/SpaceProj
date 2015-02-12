package Games;

import Structs.Shape;

public interface GameInterface {
	
	public void setup();
	public void update(int frameNum, double deltaTime);
	public Shape[] drawShapes();
	
}
