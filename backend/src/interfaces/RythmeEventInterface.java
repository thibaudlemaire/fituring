package interfaces;

/**
 * This event is throw at each beat
 * @author thibaud
 *
 */
public interface RythmeEventInterface {

	/**
	 * This function returns current true BPM 
	 * @return
	 */
	public int getCurrentTrueBPM();
	
	/**
	 * This function returns current used BPM
	 * @return
	 */
	public int getCurrentUsedBPM();
	
}