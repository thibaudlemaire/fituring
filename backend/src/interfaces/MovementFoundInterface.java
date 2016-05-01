package interfaces;

/**
 * Interface used to receive new movement done information
 * @author thibaud
 *
 */
public interface MovementFoundInterface 
{
	/**
	 * This function is called when a movement is recognized
	 * @param movementNumber
	 */
	public void MovementDone(int movementNumber);
}
