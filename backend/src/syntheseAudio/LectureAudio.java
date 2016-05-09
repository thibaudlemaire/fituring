package syntheseAudio;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import interfaces.LectureAudioInterface;
import interfaces.MetronomeListenerInterface;

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
		startBeating(bpm);
	}

	@Override
	public void stopMusic() {
		stopBeating();
	}

	@Override
	public int getAbsoluteMeasure() {
		return pulsThread.getAbsoluteMeasure();
	}

	@Override
	public int getRelativeMeasure() {
		return pulsThread.getRelativeMeasure();
	}

	@Override
	public int getRelativeBeat() {
		return pulsThread.getRelativeBeat();
	}

	@Override
	public int getAbsoluteBeat() {
		return pulsThread.getAbsoluteBeat();
	}

	@Override
	public int addLoop(String soundPath, boolean[] onMeasure, boolean[] onBeat, int volume) {
		Loop newLoop = new Loop(soundPath, onMeasure, onBeat, this);
		pulsThread.setListener(newLoop);
		loops.add(newLoop);
		return (loops.size() - 1);
	}

	@Override
	public void delLoop(int loopNumber) {
		// TODO Auto-generated method stub
		pulsThread.unsetListener(loops.get(loopNumber));
		loops.remove(loopNumber);
	}
	
	public void setMetronomeListener(MetronomeListenerInterface l)
	{
		pulsThread.setListener(l);
	}
	
	public void unsetMetronomeListener(MetronomeListenerInterface l)
	{
		pulsThread.unsetListener(l);
	}

}
