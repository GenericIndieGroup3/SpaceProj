package events.types;

public abstract class Event {

	private boolean isCanceled = false;
	
	public boolean isCanceled(){
		return isCanceled;
	}
	
	public void cancel(){
		isCanceled = true;
	}

	public void unCancel(){
		isCanceled = false;
	}
	
	
}
