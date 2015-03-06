package Games;

import java.util.UUID;

import obj.Gravitator;
import obj.Missile;

import org.lwjgl.input.Keyboard;

import Physics.PhysicsSystem;
import Structs.Vector2;
import events.Listener;
import events.types.KeyEvent;
import events.types.KeyEventType;

public class PlayerController implements Listener<KeyEvent> {

	int shootSpeed = 10;
	UUID gravUUID;
	
	public PlayerController(UUID gravUUID){
		this.gravUUID = gravUUID;
	}
	public PlayerController(UUID gravUUID, int shootSpeed){
		this(gravUUID);
		this.shootSpeed = shootSpeed;
	}
	
	@Override
	public void invoke(KeyEvent e) {
	
		//TODO what should I do about NPE's?
		//Also this whole static reference thing needs to be changed
		PhysicsSystem activeSystem = MainGame.mainGame.getActiveSystem();
		Gravitator grav = (Gravitator) activeSystem.getObject(gravUUID);

		if(e.eventType == KeyEventType.PRESS){
			if(grav != null && activeSystem != null){
				if(e.key == Keyboard.KEY_LEFT){
					Missile missile = grav.shootMissile(1, shootSpeed, new Vector2(-shootSpeed, 0));
					activeSystem.addObj(missile);
				}
				if(e.key == Keyboard.KEY_DOWN){
					Missile missile = grav.shootMissile(1, shootSpeed, new Vector2(0, -shootSpeed));
					activeSystem.addObj(missile);
				}
				if(e.key == Keyboard.KEY_UP){
					Missile missile = grav.shootMissile(1, shootSpeed, new Vector2(0, shootSpeed));
					activeSystem.addObj(missile);
				}
				if(e.key == Keyboard.KEY_RIGHT){
					Missile missile = grav.shootMissile(1, shootSpeed, new Vector2(shootSpeed, 0));
					activeSystem.addObj(missile);
				}
				if(e.key == Keyboard.KEY_SPACE){
					Missile missile = grav.shootMissile(1, shootSpeed, grav.velocity.copy());
					activeSystem.addObj(missile);
				}
			}
		}
		if(e.eventType == KeyEventType.HOLD){
			
			if(grav != null){
				if(e.key == Keyboard.KEY_W)
					grav.addMass(0.01);
				if(e.key == Keyboard.KEY_S)
					grav.addMass(-0.01);
				if(e.key == Keyboard.KEY_E)
					grav.addMass(0.1);
				if(e.key == Keyboard.KEY_D)
					grav.addMass(-0.1);
				if(e.key == Keyboard.KEY_Q)
					grav.addMass(1);
				if(e.key == Keyboard.KEY_A)
					grav.addMass(-1);
				if(e.key == Keyboard.KEY_Z)
					grav.addMass(-0.001);
			}
		}
	}

}
