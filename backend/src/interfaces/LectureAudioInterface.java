package interfaces;

/**
 * LectureAudio module interface
 * @author thibaud
 *
 */
public interface LectureAudioInterface extends LectureInterface{

	/**
	 * This function start the main sound player
	 * @param bpm
	 */
	public void startMusic(int bpm);
	
	/**
	 * This function stop the music player
	 */
	public void stopMusic();
	
	/**
	 * This function returns the ID of the current measure from the start of the music
	 * @return 
	 */
	public int getAbsoluteMeasure();
	
	/**
	 * This function returns the ID of the current measure, in the current 4-measures block
	 * @return 
	 */
	public int getRelativeMeasure();
	
	/**
	 * This function returns the ID of the current beat in the current measure
	 * @return 
	 */
	public int getRelativeBeat();
	
	/**
	 * This function returns the ID of the current beat from the start of the music
	 * @return 
	 */
	public int getAbsoluteBeat();
	
	/**
	 * This function is call to set a new loop
	 * @param sampleId The id of the sound to play
	 * @param startingTimeNumber The time (from 1 to 4) in the bar where to start playing
	 * @param volume in percent (0 to 100)
	 * @return the loop number / -1 if error
	 */
	public int addLoop(String soundPath, boolean[] onMeasure, boolean[] onBeat, int volume);
	
	/**
	 * This function deletes a loop
	 * @param loopNumber the loop ID to delete
	 */
	public void delLoop(int loopNumber);
	
	
}
