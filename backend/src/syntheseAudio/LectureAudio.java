package syntheseAudio;

import java.io.FileNotFoundException;

import interfaces.LectureAudioInterface;
import interfaces.LectureAudioSimpleInterface;

public class LectureAudio implements LectureAudioInterface {
	
	PulsThread pulsThread = null;

	@Override
	public void initLectureAudioModule(int initialVolume) {
		// TODO Auto-generated method stub
		
	}

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
		pulsThread.setBPM(BPM);
	}

	public void stopBeating() {
		// TODO Auto-generated method stub
		pulsThread.stopPulsThread();
	}

	public void playSound(String path, int volume) {
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

	@Override
	public void startMusic(int bpm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopMusic() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAbsoluteMeasure() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRelativeMeasure() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRelativeBeat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAbsoluteBeat() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addLoop(String soundPath, boolean[] onMeasure, boolean[] onBeat, int volume) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delLoop(int loopNumber) {
		// TODO Auto-generated method stub
		
	}

}
