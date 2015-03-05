package events;

public enum EventPriority {
	
	//All the priorities before monitor can modify the event
	
	//Monitor should NEVER alter the event
	//TODO? prevent monitor from altering an event
	LOWEST(1), LOW(2), HIGH(3), HIGHEST(4), MONITOR(5);
	
	private int priority;
	
	EventPriority(int priority){
		this.priority = priority;
	}
	
	public int getNumericValue(){
		return priority;
	}
	
	public static int minValue(){
		return LOWEST.getNumericValue();
	}
	
	public static int maxValue(){
		return MONITOR.getNumericValue();
	}
	
	public static EventPriority fromNumericValue(int val){
		
		for(EventPriority eventPriority : EventPriority.values()){
			if(eventPriority.getNumericValue() == val)
				return eventPriority;
		}
		return LOWEST;
		
	}
	
}
