package syntheseAudio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class PulsThread extends Thread{
	
	private String filePath;
	private int BPM;
		
	public PulsThread(String filePath, int BPM) throws FileNotFoundException
	{
		super();
		this.filePath = filePath;
		this.BPM = BPM;
	}
	
	public void run()
	{
		while(true)
		{
			try 
			{
				{
					InputStream in = new FileInputStream(filePath);
				    AudioStream as = new AudioStream(in);
				    AudioPlayer.player.start(as);
					Thread.sleep(60000/BPM); //duration in milliseconds
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}