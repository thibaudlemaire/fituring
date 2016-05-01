package interfaces;

/**
 * Server module Interface
 * @author Camille
 *
 */
public interface ServeurInterface {
	
	/**
	 * This function is called to init the server module
	 * @param engine
	 */
	public void initServeurModule(UpdateParamInterface engine);
	
	/**
	 * The server gets the volume from the android app for the audio lecture modules
	 * @return volume
	 */
	public int getVolume();
	
	/**
	 * The server gets the musical style from the android app for the synthese audio module
	 * @return the musical Style (int to define...)
	 */
	public int getMusicalStyle();
	
	/**
	 * This function return true if a client is connected to the server
	 * @return True if connected 
	 */
	public boolean isConnected();
}
