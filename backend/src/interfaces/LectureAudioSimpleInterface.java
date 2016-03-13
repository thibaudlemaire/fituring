package interfaces;

public interface LectureAudioSimpleInterface extends LectureInterface{
	/**
	 * This function is called at the beginning to setup the module
	 * @param player TypeToBeDefined, where to play the sound
	 * @param initialVolume in percent
	 */
	public void initLectureAudioModule(Object player, int initialVolume);

	/**
	 * Used to start the beat at the specified BPM
	 * @param BPM
	 */
	public void startBeating(int BPM);
	
	/** 
	 * Used to update the BPM of the beat loop
	 * @param BPM
	 */
	public void updateBPM(int BPM);
	
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
