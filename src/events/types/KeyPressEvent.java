package events.types;

import Physics.PhysicsObject;

public class KeyPressEvent extends Event {
	//TODO
	private int key;
	public KeyPressEvent(int key){
		this.key = key;
	}
	
	public int getKey(){
		return key;
	}
}
