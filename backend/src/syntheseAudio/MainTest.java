package syntheseAudio;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*SourceDataLine sourceLine = new SourceDataLine();
		try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } */
		PulsThread pulsThread = new PulsThread(80, "C:/Users/NotAfraid/Documents/Sons-PACT/clap-hall-01.wav");
		PulsThread puls2Thread = new PulsThread(80, "C:/Users/NotAfraid/Documents/Sons-PACT/clap-hall-01.wav");

		//AudioThread audioThread = new AudioThread("C:/Users/NotAfraid/Documents/Sons-PACT/clap-hall-01.wav");
		pulsThread.run();
		puls2Thread.run();
		//audioThread.run();
	}

}
