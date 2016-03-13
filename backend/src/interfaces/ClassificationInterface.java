package interfaces;

public interface ClassificationInterface {

	/**
	 * This function initialize the Classification Module
	 */
	public void initClassificationModule(Object BDD, KinectInterface kinectModule, LectureAudioSimpleInterface audio);
	
	/**
	 * This function starts the listening of kinect skeleton
	 */
	public void startListening();
	
	/**
	 * This function stops the listening of kinect skeleton
	 */
	public void stopListening();
	
	
	
}
