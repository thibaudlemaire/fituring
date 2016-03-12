package interfaces;

/**
 * Server module Interface
 * @author Camille
 *
 */
public interface ServeurInterface {
	
	/**+
	 * The server gets the volume from the android app for the audio lecture modules
	 * @return volume
	 */
	public int getVolume();
	
	/**
	 * The server gets the musical style from the android app for the synthese audio module
	 * @return the musical Style
	 */
	public Object getMusicalStyle(); //il faut définir le type de variable à utiliser pour le style de musique
}
