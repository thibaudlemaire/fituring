package syntheseAudio;

import java.io.FileNotFoundException;

import interfaces.LectureAudioInterface;
import interfaces.MetronomeListenerInterface;

public class Loop implements MetronomeListenerInterface {
	
	private String path;
	private boolean[] onMeasure;
	private boolean[] onBeat;
	LectureAudioInterface player;
	
	public Loop(String path, boolean[] onMeasure, boolean[] onBeat, LectureAudioInterface player)
	{
		this.onBeat = onBeat;
		this.onMeasure = onMeasure;
		this.path = path;
		this.player = player;
	}
	
	public void beat()
	{
		if(!onMeasure[player.getRelativeMeasure()] || !onMeasure[player.getRelativeMeasure()])
			return;
		AudioThread audioThread = null;
		try 
		{
			audioThread = new AudioThread(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		audioThread.start();
	}

	public boolean isOnBeatN(int N)
	{
		return onBeat[N];
	}
	
	public boolean isOnMeasureN(int N)
	{
		return onMeasure[N];
	}
}
