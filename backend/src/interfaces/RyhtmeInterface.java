package interfaces;

/**
 * Ryhtme module interface
 * @author thibaud
 *
 */
public interface RyhtmeInterface {

	/**
	 * This function initialize the RythmeModule
	 */
	public void initRythmeModule();

	/**
	 * This function returns current true BPM 
	 * @return true BPM
	 */
	public int getCurrentTrueBPM();
	
	/**
	 * This function returns current used BPM
	 * @return used BPM
	 */
	public int getCurrentUsedBPM();
	
}
