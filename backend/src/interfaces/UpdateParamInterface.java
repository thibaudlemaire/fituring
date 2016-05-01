package interfaces;

/**
 * This interface is used to change parameters from the Android App
 * @author thibaud
 *
 */
public interface UpdateParamInterface 
{
	/**
	 * This function is called when a client just connect
	 */
	public void connected();
	
	/**
	 * This function is called when a client disconnect the server
	 */
	public void disconnected();
	
	/**
	 * This function is called when the user changes the volume
	 * @param volume
	 */
	public void setVolume(int volume);
	
	/**
	 * This function is called when the the style is updated
	 * @param musicStyle
	 */
	public void setStyle(int musicStyle);
	
	/**
	 * Current volume getter
	 * @return
	 */
	public int getVolume();
	
	/**
	 * Current music style getter
	 * @return
	 */
	public int getStyle();
	
	/**
	 * This function is called to start the music
	 */
	public void startFituring();
	
	/**
	 * This function is called to stop the music
	 */
	public void  stopFituring();
	
}
