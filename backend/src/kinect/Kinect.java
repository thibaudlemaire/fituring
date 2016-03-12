package kinect;

import java.util.Date;
import javax.swing.event.EventListenerList;
import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;
import events.KinectEvent;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class Kinect extends J4KSDK implements KinectInterface
{
	Skeleton currentSkeleton = null; // Last skeleton received
	int counter=0; // Used to calculate FPS
	long time=0; // Used to calculate FPS
	
	// List of EventListener used to update each data's module
	private final EventListenerList listeners = new EventListenerList();
	
	/**
	 * Event-called when new skeleton is received
	 */
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		System.out.println("New skeleton !");
		
		// Getting new skeleton
		currentSkeleton = Skeleton.getSkeleton(0, skeleton_tracked, positions, orientations, joint_status, this);
		
		// Create the event
		KinectEvent event = new KinectEvent(currentSkeleton);
		
		// Updating all modules with new datas
		for(KinectListenerInterface listener : listeners.getListeners(KinectListenerInterface.class)) 
            listener.skeletonReceived(event);
	}
	
	/**
	 * Event-called when depthFrame are received
	 */
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2, float[] arg3) 
	{
		// Calculation of the FPS
		if(counter==0)
			time=new Date().getTime();
		counter+=1;
	}
	
	/**
	 * Event-called when colorFrame are received
	 */
	public void onColorFrameEvent(byte[] arg0) 
	{
		// NTD
	}
	
	///////////////////// KinectInterface methods ////////////////////////
	
	/**
	 * This function init the Kinect Module
	 */
	public void initKinectModule() {
		// Init Kinect, datas, current skeleton, etc...
		start(J4KSDK.DEPTH|J4KSDK.SKELETON);
	}

	/**
	 * This function set a new listener in order to get new skeleton datas
	 */
	public void setListener(KinectListenerInterface l) {
		listeners.add(KinectListenerInterface.class, l );
	}

	/** 
	 * This function returns the current skeleton
	 */
	public Skeleton getSkeleton() {
		return currentSkeleton;
	}

	/**
	 * This function returns the amount of dancers
	 * NOT YET IMPLEMENTED
	 */
	public int getNumberOfDancers() {
		return 1;
	}

	/**
	 * This function return the video 
	 * NOT YET IMPLEMENTED
	 */
	public Object getVideo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/** 
	 * This function returns the global FPS rate
	 * @return FPS
	 */
	public long getFPS()
	{
		return (new Date().getTime()-time)/counter;
	}

	@Override
	public void unsetListener(KinectListenerInterface l) 
	{
        listeners.remove(KinectListenerInterface.class, l);
	}
	
}