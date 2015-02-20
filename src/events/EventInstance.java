package events;

public abstract class EventInstance {

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
