package interfaces;

/**
 * SyntheseAudio module interface
 * @author thibaud
 *
 */
public interface SyntheseAudioInterface {

	/**
	 * This function is called at the beginning to setup the module
	 * @param player TypeToBeDefined, where to play the sound
	 * @param initialVolume in percent
	 */
	public void initSyntheseAudioModule(Object player, int initialVolume);
	
	/**
	 * This function is call to set a new loop
	 * @param sampleId The id of the sound to play
	 * @param startingTimeNumber The time (from 1 to 4) in the bar where to start playing
	 * @param volume in percent (0 to 100)
	 */
	public void addLoop(int sampleId, int startingTimeNumber, int volume);
}
