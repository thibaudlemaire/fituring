package syntheseAudio;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import interfaces.LectureAudioInterface;

public class LectureAudio implements LectureAudioInterface {
	
	PulsThread pulsThread = null;
	ArrayList<Loop> loops = new ArrayList<Loop>();

	@Override
	public void initLectureAudioModule(int initialVolume) {
		// TODO Auto-generated method stub
		
	}

	public void startBeating(int BPM) {
		// TODO Auto-generated method stub
		pulsThread = new PulsThread(BPM);
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
		Loop newLoop = new Loop(soundPath);
		pulsThread.setListener(newLoop);
		loops.add(newLoop);
		return (loops.size() - 1);
	}

	@Override
	public void delLoop(int loopNumber) {
		// TODO Auto-generated method stub
		
	}

}
