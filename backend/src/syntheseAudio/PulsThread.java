package syntheseAudio;

public class PulsThread extends Thread{
	
	private int period; //period : a BPM 
	private boolean keepPlayin;
		
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
				Thread.sleep(60000/period);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // sleep takes an argument in milliseconds
		}
	}
	
	public void setBPM (int BPM){
		this.period=BPM;
	}
	
	public void stopPulsThread (){
		keepPlayin = false;
	}
}