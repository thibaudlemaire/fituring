package engine;

public class Loop {

	private boolean[] onBeat = new boolean[4];
	private boolean[] onMeasure = new boolean[4];
	private int measureCount;
	private Sound sound;
	
	public Loop(Sound sound, boolean[] onMeasure, boolean[] onBeat, int measureCount)
	{
		this.onBeat = onBeat;
		this.onMeasure = onMeasure;
		this.measureCount = measureCount;
		this.sound = sound;
	}
	
	public boolean isOnBeatN(int N)
	{
		return onBeat[N];
	}
	
	public boolean isOnMeasureN(int N)
	{
		return onMeasure[N];
	}
	
	public void measurePassed()
	{
		measureCount--;
	}
	
	public boolean isStillPlaying()
	{
		if (measureCount != 0)
			return true;
		else
			return false;
	}
	
}
