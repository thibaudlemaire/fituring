package interfaces;

public interface LectureAudioSimpleInterface extends LectureInterface{
	
	/**
	 * Used to start the beat at the specified BPM
	 * @param BPM
	 */
	public void startBeating(int BPM);
	
	/**
	 * Used to stop the current beat loop
	 * @param BPM
	 */
	public void stopBeating();
	
	/** 
	 * Used to play the specified sound once
	 * @param path
	 */
	public void playSound(String path);
	
}
