package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import events.types.Event;

public class EventDistributor<T extends Event> {

	private Map<EventPriority, List<Listener<T>>> listeners = new HashMap<EventPriority, List<Listener<T>>>(5);
	
	public EventDistributor(){
		
	}
	
	public void addListener(Listener<T> listener, EventPriority priority){
		if(!listeners.containsKey(priority))
			listeners.put(priority, new ArrayList<Listener<T>>());
		
		List<Listener<T>> list = listeners.get(priority);
		
		if(!list.contains(listener))
			list.add(listener);
	}
	
	public void removeListener(Listener<T> listener){
		for(List<Listener<T>> list : listeners.values()){
			list.remove(listener);
		}
	}
	
	public void invoke(T event){
	
		List<Listener<T>> list;
		
		for(int i = EventPriority.minValue(); i <= EventPriority.maxValue(); i++){
			
			
			list = listeners.get(EventPriority.fromNumericValue(i));
			if(list != null)
				for(Listener<T> listener : list)
					listener.invoke(event);
			
		}
		
	}
}

