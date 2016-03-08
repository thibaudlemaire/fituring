package kinect;

import java.util.Date;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class Kinect extends J4KSDK 
{
	
	Skeleton currentSkeleton = null;
	int counter=0;
	long time=0;
	//KinectRecorder kinectRecorder = new KinectRecorder(new XEDConvertApp());
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		System.out.println("New skeleton !");
		currentSkeleton = Skeleton.getSkeleton(0, skeleton_tracked, positions, orientations, joint_status, this);
		
		System.out.println(currentSkeleton.get3DJointY(Skeleton.HEAD));
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
	
	public static void main(String[] args)
	{
		
		System.out.println("Début");
		Kinect kinect=new Kinect();
		kinect.start(J4KSDK.DEPTH|J4KSDK.SKELETON);
		
		
		//Sleep for 20 seconds.
		try {Thread.sleep(20000);} catch (InterruptedException e) {}
		
		
		kinect.stop();		
		System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}

	/*public void startRecording(String fileName)
	{
		kinectRecorder.startRecording(fileName);
	}*/
	
}
