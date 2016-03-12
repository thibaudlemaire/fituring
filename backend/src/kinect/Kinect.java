package kinect;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectInterface;
import interfaces.KinectListener;

public class Kinect extends J4KSDK implements KinectInterface
{
	
	Skeleton currentSkeleton = null;
	static PrintWriter pw = null;
	static String filename = "datas/clap.csv";
	static int captureTime = 10000;
	int counter=0;
	long time=0;
	
	@Override
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] positions, float[] orientations, byte[] joint_status) {
		System.out.println("New skeleton !");
		currentSkeleton = Skeleton.getSkeleton(0, skeleton_tracked, positions, orientations, joint_status, this);
		
		pw.print(new Date().getTime()-time);
		for(int i=0; i<Skeleton.JOINT_COUNT; i++)
		{
			pw.print(";");
			pw.print(currentSkeleton.get3DJointX(i));
			pw.print(",");
			pw.print(currentSkeleton.get3DJointY(i));
			pw.print(",");
			pw.print(currentSkeleton.get3DJointZ(i));
		}
		pw.println();
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
		// TODO Auto-generated method stub
		
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
	public Video getVideo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args)
	{
		
		System.out.println("D�but");
		Kinect kinect=new Kinect();
		
		try {
			pw = new PrintWriter(filename);
		} catch (FileNotFoundException e) 
		{
			System.err.println("File not found !");
		} catch(SecurityException e) {
			System.err.println("Security error !");
		} catch (Exception e) {
			System.err.println("Error !");
			e.printStackTrace(System.err);
		} 

		pw.println("Time;"
				+ "SPINE_BASE_X,SPINE_BASE_Y,SPINE_BASE_Z;"
				+ "SPINE_MID_X,SPINE_MID_Y,SPINE_MID_Z;"
				+ "NECK_X,NECK_Y,NECK_Z;"
				+ "HEAD_X,HEAD_Y,HEAD_Z;"
				+ "SHOULDER_LEFT_X,SHOULDER_LEFT_Y,SHOULDER_LEFT_Z;"
				+ "ELBOW_LEFT_X,ELBOW_LEFT_Y,ELBOW_LEFT_Z;"
				+ "WRIST_LEFT_X,WRIST_LEFT_Y,WRIST_LEFT_Z;"
				+ "HAND_LEFT_X,HAND_LEFT_Y,HAND_LEFT_Z;"
				+ "SHOULDER_RIGHT_X,SHOULDER_RIGHT_Y,SHOULDER_RIGHT_Z;"
				+ "ELBOW_RIGHT_X,ELBOW_RIGHT_Y,ELBOW_RIGHT_Z;"
				+ "WRIST_RIGHT_X,WRIST_RIGHT_Y,WRIST_RIGHT_Z;"
				+ "HAND_RIGHT_X,HAND_RIGHT_Y,HAND_RIGHT_Z;"
				+ "HIP_LEFT_X,HIP_LEFT_Y,HIP_LEFT_Z;"
				+ "KNEE_LEFT_X,KNEE_LEFT_Y,KNEE_LEFT_Z;"
				+ "ANKLE_LEFT_X,ANKLE_LEFT_Y,ANKLE_LEFT_Z;"
				+ "FOOT_LEFT_X,FOOT_LEFT_Y,FOOT_LEFT_Z;"
				+ "HIP_RIGHT_X,HIP_RIGHT_Y,HIP_RIGHT_Z;"
				+ "KNEE_RIGHT_X,KNEE_RIGHT_Y,KNEE_RIGHT_Z;"
				+ "ANKLE_RIGHT_X,ANKLE_RIGHT_Y,ANKLE_RIGHT_Z;"
				+ "FOOT_RIGHT_X,FOOT_RIGHT_Y,FOOT_RIGHT_Z;"
				+ "SPINE_SHOULDER_X,SPINE_SHOULDER_Y,SPINE_SHOULDER_Z;"
				+ "HAND_TIP_LEFT_X,HAND_TIP_LEFT_Y,HAND_TIP_LEFT_Z;"
				+ "THUMB_LEFT_X,THUMB_LEFT_Y,THUMB_LEFT_Z;"
				+ "HAND_TIP_RIGHT_X,HAND_TIP_RIGHT_Y,HAND_TIP_RIGHT_Z;"
				+ "THUMB_RIGHT_X,THUMB_RIGHT_Y,THUMB_RIGHT_Z");
		
		kinect.start(J4KSDK.DEPTH|J4KSDK.SKELETON);
		
		
		try {Thread.sleep(captureTime);} catch (InterruptedException e) {}
		
		if (pw != null) {
			try { pw.close(); } catch (Exception e) { } ;
		}
		
		kinect.stop();		
		System.out.println("FPS: "+kinect.counter*1000.0/(new Date().getTime()-kinect.time));
	}
	
}