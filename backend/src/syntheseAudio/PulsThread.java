package syntheseAudio;

import javax.swing.event.EventListenerList;

public class PulsThread extends Thread{
	
	private int period; //period : a BPM 
	private boolean keepPlayin;
	
	private EventListenerList listeners = new EventListenerList();
		
	public PulsThread(int period)
	{
		super();
		this.period = period;
		keepPlayin = true;
	}
	
	public void run()
	{
		while(keepPlayin)
		{
			try {
				for(Loop listener : listeners.getListeners(Loop.class)) 
		            listener.beat();
				Thread.sleep(60000/period);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // sleep takes an argument in milliseconds
		}
	}
	
	public void setListener(Loop l) {
		listeners.add(Loop.class, l );
	}
	
	public void unsetListener(Loop l)
	{
		
	}

	
	public void setBPM (int BPM){
		this.period=BPM;
	}
	
	public void stopPulsThread (){
		keepPlayin = false;
	}
}