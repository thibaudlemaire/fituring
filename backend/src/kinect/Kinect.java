package kinect;

import java.util.Date;

import javax.swing.event.EventListenerList;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectInterface;
import interfaces.KinectListener;

public class Kinect extends J4KSDK implements KinectInterface
{
	
	Skeleton currentSkeleton = null;
	int counter=0;
	long time=0;
	
	private final EventListenerList listeners = new EventListenerList();
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		System.out.println("New skeleton !");
		currentSkeleton = Skeleton.getSkeleton(0, skeleton_tracked, positions, orientations, joint_status, this);
		
	}
	
	@Override
	public void onDepthFrameEvent(short[] arg0, byte[] arg1, float[] arg2, float[] arg3) 
	{
		if(counter==0)
			time=new Date().getTime();
		counter+=1;
	}
	
	@Override
	public void onColorFrameEvent(byte[] arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	
	///////////////////// KinectInterface methods ////////////////////////
	
	@Override
	public void initKinectModule() {
		// TODO Auto-generated method stub
		// A remplir à l'intégration
	}

	@Override
	public void setListener(KinectListener l) {
		listeners.add(KinectListener.class, l );
	}

	@Override
	public Skeleton getSkeleton() {
		return currentSkeleton;
	}

	@Override
	public int getNumberOfDancers() {
		return 1;
	}

	@Override
	public Object getVideo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args)
	{
		
		System.out.println("Debut");
		Kinect kinect=new Kinect();
		
		kinect.start(J4KSDK.DEPTH|J4KSDK.SKELETON);
		
		try {Thread.sleep(10000);} catch (InterruptedException e) {}
		
		kinect.stop();		
		System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}
	
	public void addSkeletonListener(KinectListener listener) {
        listeners.add(KinectListener.class, listener);
    }
	
	
}