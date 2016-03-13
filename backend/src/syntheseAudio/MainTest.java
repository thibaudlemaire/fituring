package syntheseAudio;

import java.io.FileNotFoundException;

public class MainTest {

	public static void main(String[] args) {
		
		PulsThread pulsThread = null;
		AudioThread audioThread = null;
		try 
		{
			pulsThread = new PulsThread("sounds/kick.wav", 400);
			audioThread = new AudioThread("sounds/clap.wav");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		audioThread.start();
		pulsThread.start();

	}

}
