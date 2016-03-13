package syntheseAudio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PulsThread extends Thread{
	
	private String filePath;
	private int period; //period : a BPM 
	private boolean keepPlayin;
		
	public PulsThread(String filePath, int period) throws FileNotFoundException
	{
		super();
		this.filePath = filePath;
		this.period = period;
		keepPlayin=true;
	}
	
	public void run()
	{
		while(keepPlayin)
		{
			try 
			{
				{
					InputStream in = new FileInputStream(filePath);
				    AudioStream as = new AudioStream(in);
				    AudioPlayer.player.start(as);
					Thread.sleep(60000/period); // sleep takes an argument in milliseconds
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void setBPM (int BPM){
		this.period=BPM;
	}
	
	public void stopPulsThread (){
		keepPlayin=false;
	}
}