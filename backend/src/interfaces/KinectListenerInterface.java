package interfaces;

import java.util.EventListener;

/**
 * This interface describes functions called when datas are received from the Kinect
 * @author thibaud
 *
 */
public interface KinectListenerInterface extends EventListener {

	/**
	 * This function is automatically called when a new Skeleton is received 
	 * from the Kinect sensor
	 * @param e A KinectEvent object
	 */
	void skeletonReceived(KinectEventInterface e);

}
