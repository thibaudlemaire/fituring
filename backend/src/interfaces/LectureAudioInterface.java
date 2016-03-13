package interfaces;

/**
 * LectureAudio module interface
 * @author thibaud
 *
 */
public interface LectureAudioInterface extends LectureInterface{

	/**
	 * This function is called at the beginning to setup the module
	 * @param player TypeToBeDefined, where to play the sound
	 * @param initialVolume in percent
	 */
	public void initLectureAudioModule(Object player, int initialVolume);
	
	/**
	 * This function is call to set a new loop
	 * @param sampleId The id of the sound to play
	 * @param startingTimeNumber The time (from 1 to 4) in the bar where to start playing
	 * @param volume in percent (0 to 100)
	 */
	public void addLoop(int sampleId, int startingTimeNumber, int volume);
	
	/**
	 * This function get the right sound according to the attributes of a movement
	 * @param attribut1 to be defined
	 * @param attribut2 to be defined
	 * @return
	 */
	public Object chooseTheSound(int attribut1, int attribut2);
}
