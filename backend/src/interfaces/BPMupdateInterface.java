package interfaces;
/**
 * Interface used to send new BPM datas
 * @author thibaud
 *
 */
public interface BPMupdateInterface 
{
	/**
	 * This function is called by Rythme Module to update BPM...
	 * @param newBPM
	 */
	public void updateBPM(int newBPM);
	
}
