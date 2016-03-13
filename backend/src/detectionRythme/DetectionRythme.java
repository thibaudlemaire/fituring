package detectionRythme;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.RyhtmeInterface;

public class DetectionRythme implements RyhtmeInterface, KinectListenerInterface {

	KinectInterface kinect;
	int BPM;
	
	static float trigger = (float) 0.1;
	static float limitUp = (float) 0.8;
	static float limitDown = (float) 0.1;
	
	//Booleans needed to make an hysteresis when some movements are noticed
	boolean limitUpExceeded = false;
	boolean limitDownExceeded = false;
	boolean leftHandAboveHead = false;
	boolean rightHandAboveHead = false;
	
	public void initRythmeModule(KinectInterface kinect) 
	{
		this.kinect = kinect;
		this.BPM = 110;
	}
	
	public void skeletonReceived(KinectEventInterface e) 
	{
		Skeleton newSkeleton = e.getNewSkeleton();
		
		float LeftCoordinatesX = newSkeleton.get3DJointX(Skeleton.FOOT_LEFT);
		float RightCoordinatesX = newSkeleton.get3DJointX(Skeleton.FOOT_RIGHT);
		float LeftCoordinatesY = newSkeleton.get3DJointY(Skeleton.FOOT_LEFT);
		float RightCoordinatesY = newSkeleton.get3DJointY(Skeleton.FOOT_RIGHT);
		float LeftCoordinatesZ = newSkeleton.get3DJointZ(Skeleton.FOOT_LEFT);
		float RightCoordinatesZ = newSkeleton.get3DJointZ(Skeleton.FOOT_RIGHT);
		
		
		//Determinating the distance between the two hands
		float distance = (float) Math.sqrt((RightCoordinatesX - LeftCoordinatesX)*(RightCoordinatesX - LeftCoordinatesX) + (RightCoordinatesY - LeftCoordinatesY)*(RightCoordinatesY - LeftCoordinatesY) + (RightCoordinatesZ - LeftCoordinatesZ)*(RightCoordinatesZ - LeftCoordinatesZ));
		//System.out.println(distance);
		
		//Noticing when arms are extended
		if (distance > limitUp && limitUpExceeded == false) {
			limitUpExceeded = true;
			beat();
		}
		
		if (limitUpExceeded == true && distance < (limitUp - trigger)) {
			limitUpExceeded = false;
		}
		
		//Noticing when a clap is done
		if (distance < limitDown && limitDownExceeded == false) {
			limitDownExceeded = true;
			beat();
		}
		
		if (limitDownExceeded == true && distance > (limitDown + trigger)) {
			limitDownExceeded = false;
		}
	}
	
	private void beat()
	{
		
	}

	public int getCurrentTrueBPM() 
	{
		
		return 0;
	}

	public int getCurrentUsedBPM() 
	{

		return 0;
	}
	
	public void startListening() 
	{
		kinect.setListener(this);
	}

	public void stopListening() 
	{
		kinect.unsetListener(this);
	}

}

