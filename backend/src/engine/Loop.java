package engine;

public class Loop {

	private boolean[] beats = new boolean[4];
	private boolean[] measure = new boolean[4];
	private int measureCount;
	private Sound sound;
	
	public Loop(Sound sound, boolean[] onMeasure, boolean[] onBeat, int measureCount)
	{
		this.beats = beats;
		this.measure = measure;
		this.measureCount = measureCount;
		this.sound = sound;
	}
	
}
