package Games;

import org.lwjgl.input.Keyboard;

import Implementations.ImplementationAbstract;
import Physics.PhysicsSystem;
import events.Listener;
import events.types.KeyEvent;
import events.types.KeyEventType;

public class GameController implements Listener<KeyEvent> {

	double zoomSpeed = 1.01;
	int moveSpeed = 5;
	
	DisplayManager displayManager;
	
	public GameController(DisplayManager displayManager){
		this.displayManager = displayManager;
	}
	public GameController(DisplayManager displayManager, double zoomSpeed, int moveSpeed){
		this(displayManager);
		this.zoomSpeed = zoomSpeed;
		this.moveSpeed = moveSpeed;
	}

	@Override
	public void invoke(KeyEvent e) {
		
		MainGame mainGame = MainGame.mainGame;
		ImplementationAbstract imp = MainGame.mainGame.imp;
		DisplayManager displayManager = MainGame.mainGame.displayManager;
		
		if(e.eventType == KeyEventType.HOLD){

			if(e.key == Keyboard.KEY_F)
				imp.keepUpdating = false;
			if(e.key == Keyboard.KEY_R)
				MainGame.mainGame.setup();
			if(e.key == Keyboard.KEY_N)
				displayManager.multiplyZoom(1/zoomSpeed);
			if(e.key == Keyboard.KEY_M)
				displayManager.multiplyZoom(zoomSpeed);
			if(e.key == Keyboard.KEY_I)
				displayManager.moveCenterBasedOnZoom(0, moveSpeed);
			if(e.key == Keyboard.KEY_K)
				displayManager.moveCenterBasedOnZoom(0, -moveSpeed);
			if(e.key == Keyboard.KEY_J)
				displayManager.moveCenterBasedOnZoom(-moveSpeed, 0);
			if(e.key == Keyboard.KEY_L)
				displayManager.moveCenterBasedOnZoom(moveSpeed, 0);

		}
		
	}

}
