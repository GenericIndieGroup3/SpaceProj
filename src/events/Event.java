package events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {

	private Map<EventPriority, List<Listener>> listeners = new HashMap<EventPriority, List<Listener>>(5);
	
	public Event(){
		
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
	
	public void invoke(EventInstance e){

		List<Listener> list;
		
		//TODO I don't know how iterators work, it would be much better if EventPriority returned an iterator
		for(int i = EventPriority.minValue(); i <= EventPriority.maxValue(); i++){
			
			list = listeners.get(EventPriority.fromNumericValue(i));
			
			if(list != null){
				
				for(Listener listener : list)
					listener.onInvoke(e);
			}
		}
	}
}
