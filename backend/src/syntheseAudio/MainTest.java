package syntheseAudio;

import java.io.FileNotFoundException;

public class MainTest {

	public static void main(String[] args) {
		
		PulsThread pulsThread = null;
		AudioThread audioThread = null;
		try 
		{
			pulsThread = new PulsThread("sounds/kick.wav", 80);
			audioThread = new AudioThread("sounds/clapwav");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pulsThread.start();
		audioThread.start();
		
		}

}
