package events;

import events.types.Event;

public interface Listener<EventType extends Event> {
	
	public void invoke(EventType e);

	
}