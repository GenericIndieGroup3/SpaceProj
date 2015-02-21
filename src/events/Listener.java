package events;

public interface Listener<EventType extends Event> {
	
	public void invoke(EventType e);

	
}