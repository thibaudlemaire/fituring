package syntheseAudio;

import java.io.FileNotFoundException;
import java.util.EventListener;

public class Loop implements EventListener {
	
	private String path;
	
	public Loop(String path)
	{
		this.path = path;
	}
	
	public void beat()
	{
		AudioThread audioThread = null;
		try 
		{
			audioThread = new AudioThread(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		audioThread.start();
	}

}
