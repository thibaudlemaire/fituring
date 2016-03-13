package syntheseAudio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class AudioThread extends Thread {
	
	private String filePath;
		
	public AudioThread(String filePath) throws FileNotFoundException
	{
		super();
		this.filePath = filePath;
	}
	
	public void run()
	{
		try 
		{
			InputStream in = new FileInputStream(filePath);
		    AudioStream as = new AudioStream(in);
		    AudioPlayer.player.start(as);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
