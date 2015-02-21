package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import events.types.Event;

public class EventDistributor<EventType extends Event> {

	private Map<EventPriority, List<Listener>> listeners = new HashMap<EventPriority, List<Listener>>(5);
	
	public EventDistributor(){
		
	}
	
	public void addListener(Listener listener, EventPriority priority){
		if(!listeners.containsKey(priority))
			listeners.put(priority, new ArrayList<Listener>());
		
		List<Listener> list = listeners.get(priority);
		
		if(!list.contains(listener))
			list.add(listener);
	}
	
	public void removeListener(Listener listener){
		for(List<Listener> list : listeners.values()){
			list.remove(listener);
		}
	}
	public void invoke(EventType event){
	
		List<Listener> list;
		
		for(int i = EventPriority.minValue(); i <= EventPriority.maxValue(); i++){
			
			list = listeners.get(EventPriority.fromNumericValue(i));
			
			if(list != null)
				for(Listener listener : list){
					listener.invoke(event);
				}
		}
		
	}
}

