package interfaces;

public interface ClassificationInterface {

	/**
	 * This function initialize the Classification Module
	 */
	public void initClassificationModule(KinectInterface kinectModule,  MovementFoundInterface engineModule);
	
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
