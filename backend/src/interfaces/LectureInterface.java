package interfaces;

/**
 * Interface used to prevent from difficulties to switch between the two Lecture methods
 * @author thibaud
 *
 */
public abstract interface LectureInterface 
{
	/**
	 * This function is called at the beginning to setup the module
	 * @param player TypeToBeDefined, where to play the sound
	 * @param initialVolume in percent
	 */
	public void initLectureAudioModule(int initialVolume);
	
	/**
	 * This function updates the current BPM of the music
	 * @param newBPM
	 */
	public void updateBPM(int newBPM);
	
	
}
