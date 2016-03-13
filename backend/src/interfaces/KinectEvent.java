package interfaces;

import edu.ufl.digitalworlds.j4k.Skeleton;

/**
 * This interface describes KinectEvent events, typically when a new skeleton is received
 * @author thibaud
 *
 */
public interface KinectEvent {

	public Skeleton getNewSkeleton();
	
	public long getSkeletonTime();
	
}