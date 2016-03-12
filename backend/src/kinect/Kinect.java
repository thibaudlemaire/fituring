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
	
	Skeleton currentSkeleton = null;
	int counter=0; // Used to calculate FPS
	long time=0; // Used to calculate FPS
	
	private final EventListenerList listeners = new EventListenerList();
	
	/**
	 * Event-called when new skeleton is received
	 */
	@Override
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
	@Override
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
	@Override
	public void onColorFrameEvent(byte[] arg0) 
	{
		// NTD
	}
	
	///////////////////// KinectInterface methods ////////////////////////
	
	/**
	 * This function init the Kinect Module
	 */
	@Override
	public void initKinectModule() {
		// Init Kinect, datas, current skeleton, etc...
	}

	/**
	 * This function set a new listener in order to get new skeleton datas
	 */
	@Override
	public void setListener(KinectListenerInterface l) {
		listeners.add(KinectListenerInterface.class, l );
	}

	/** 
	 * This function returns the current skeleton
	 */
	@Override
	public Skeleton getSkeleton() {
		return currentSkeleton;
	}

	/**
	 * This function returns the amount of dancers
	 * NOT YET IMPLEMENTED
	 */
	@Override
	public int getNumberOfDancers() {
		return 1;
	}

	/**
	 * This function return the video 
	 * NOT YET IMPLEMENTED
	 */
	@Override
	public Object getVideo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	///////////////////////////// Main test function ///////////////////
	public static void main(String[] args)
	{
		
		System.out.println("Debut");
		Kinect kinect=new Kinect();
		
		kinect.start(J4KSDK.DEPTH|J4KSDK.SKELETON);
		
		try {Thread.sleep(10000);} catch (InterruptedException e) {}
		
		kinect.stop();		
		System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}
	
	
}