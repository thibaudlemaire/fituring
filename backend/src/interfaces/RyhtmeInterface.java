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
	public void initRythmeModule(KinectInterface kinect, BPMupdateInterface bpmUpdate);

	/**
	 * This function returns BPM by autocorrelation
	 * @return true BPM
	 */
	public int getBPM();
	
	/**
	 * This function returns BPM using hysteresis method
	 * @return
	 */
	public int getSimpleBPM();
	
	/**
	 * This function return current wealth of dance
	 * @return
	 */
	public int getWealth();
	
}
