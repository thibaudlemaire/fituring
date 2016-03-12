package interfaces;

import edu.ufl.digitalworlds.j4k.Skeleton;

/**
 * Kinect module interface
 * @author thibaud
 *
 */
public interface KinectInterface {
	
	/** 
	 * This function initialize the Kinect Module
	 */
	public void initKinectModule();
	
	/**
	 * This function setup a listener for the Kinect
	 * When the Kinect module receive new interesting datas, listeners are called
	 * @param l a KinectListener
	 */
	public void setListener(KinectListener l);
	
	/** 
	 * This function returns current Skeleton 
	 * @return currentSkeleton
	 */
	public Skeleton getSkeleton();
	
	/** This function returns the number of dancers
	 * @return number of dancers
	 */
	public int getNumberOfDancers();
	
	/**
	 * This function returns the last video taken
	 * @return video
	 */
	public Object getVideo(); //le type Video est provisoire
}
