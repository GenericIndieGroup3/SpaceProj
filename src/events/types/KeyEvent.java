package events.types;

import Physics.PhysicsObject;

public class KeyEvent extends Event {
	//TODO
	public int key;
	public KeyEventType eventType;
	
	public KeyEvent(int key, KeyEventType eventType){
		this.key = key;
		this.eventType = eventType;
	}
	
	public int getKey(){
		return key;
	}
}
