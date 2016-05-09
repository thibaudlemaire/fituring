package syntheseAudio;

import javax.swing.event.EventListenerList;

import interfaces.MetronomeListenerInterface;

public class PulsThread extends Thread{
	
	private int period; //period : a BPM 
	private boolean keepPlayin;
	
	private int absoluteMeasureCounter = 0;
	private int absoluteBeatCounter = 0;
	private int relativeMeasureCounter = 0;
	private int relativeBeatCounter = 0;
	
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
				absoluteMeasureCounter++;
				absoluteBeatCounter++;
				if (relativeMeasureCounter < 4)
					relativeMeasureCounter++;
				else
					relativeMeasureCounter = 0;
				if (relativeBeatCounter <4)
					relativeBeatCounter++;
				else
					relativeBeatCounter = 0;
				for(MetronomeListenerInterface listener : listeners.getListeners(MetronomeListenerInterface.class))
					listener.beat();
				Thread.sleep(60000/period);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // sleep takes an argument in milliseconds
		}
	}
	
	public void setListener(MetronomeListenerInterface l) {
		listeners.add(MetronomeListenerInterface.class, l );
	}
	
	public void unsetListener(MetronomeListenerInterface l)
	{
		listeners.remove(MetronomeListenerInterface.class, l);
	}

	
	public void setBPM (int BPM){
		this.period=BPM;
	}
	
	public void stopPulsThread (){
		keepPlayin = false;
	}
	
	public int getAbsoluteMeasure() {
		return absoluteMeasureCounter;
	}

	
	public int getRelativeMeasure() {
		return relativeMeasureCounter;
	}

	
	public int getRelativeBeat() {
		return relativeBeatCounter;
	}

	
	public int getAbsoluteBeat() {
		return absoluteBeatCounter;
	}

}