package classification;

import java.io.FileNotFoundException;

import syntheseAudio.AudioThread;

public class SoundTest {
	
	private static AudioThread audioThread = null;

	public static void initSoundTest()
	{
		try 
		{
			audioThread = new AudioThread("sounds/clap.wav");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void clap() {
		System.out.println("Clap !");
		audioThread.start();
	}
	
	public static void armsExtended() {
		System.out.println("Arms extended !");
	}
	
	public static void leftHandAboveHead() {
		System.out.println("Left hand above head !");
	}
	
	public static void rightHandAboveHead() {
		System.out.println("Right hand above head !");
	}
}
