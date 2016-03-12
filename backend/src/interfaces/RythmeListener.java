package interfaces;

/**
 * This interface describes functions called when a beat is detected
 * @author thibaud
 *
 */
public interface RythmeListener {
	
	/**
	 * This function is called each time a beat is detected
	 * @param e
	 */
	public void RythmeBeats(RythmeEventInterface e);

}
