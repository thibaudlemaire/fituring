package syntheseAudio;

import java.io.FileNotFoundException;

import interfaces.LectureAudioSimpleInterface;

public class LectureAudio implements LectureAudioSimpleInterface {
	
	PulsThread pulsThread = null;

	@Override
	public void initLectureAudioModule(Object player, int initialVolume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startBeating(int BPM) {
		// TODO Auto-generated method stub
		try 
		{
			pulsThread = new PulsThread("sounds/kick.wav", BPM);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		pulsThread.start();
	}

	@Override
	public void updateBPM(int BPM) {
		// TODO Auto-generated method stub
		pulsThread.setBPM(BPM);
	}

	@Override
	public void stopBeating() {
		// TODO Auto-generated method stub
		pulsThread.stopPulsThread();
	}

	@Override
	public void playSound(String path) {
		// TODO Auto-generated method stub
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
