package interfaces;

public interface ClassificationInterface {

	/**
	 * This function initialize the Classification Module
	 */
	
	////////////A remettre. Ligne de code commentée pour les tests/////////
	//public void initClassificationModule(KinectInterface kinectModule,  MovementFoundInterface engineModule);
	public void initClassificationModule(KinectInterface kinectModule);
	
	/**
	 * Function used to add a gesture tu recognize
	 * @param path to the gesture
	 * @return ID of the gesture
	 */
	public int addGesture(String path);
	/**
	 * This function starts the listening of kinect skeleton
	 */
	public void startListening();
	
	/**
	 * This function stops the listening of kinect skeleton
	 */
	public void stopListening();
	
	/**
	 * This function returns the number of gestures analyzed
	 * @return
	 */
	public int getNumberOfGestures();
	
}
