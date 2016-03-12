package interfaces;

import edu.ufl.digitalworlds.j4k.Skeleton;

/**
 * This interface describes KinectEvent events, typically when a new skeleton is received
 * @author thibaud
 *
 */
public interface KinectEventInterface {

	/**
	 * This function returns the skeleton generating the event 
	 * @return the new skeleton
	 */
	public Skeleton getNewSkeleton();
	
	/**
	 * This function returns the date of the event occuring
	 * @return
	 */
	public long getSkeletonTime();
	
}
