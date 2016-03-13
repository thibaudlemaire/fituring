package detectionRythme;

import java.util.Date;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.LectureAudioSimpleInterface;
import interfaces.RyhtmeInterface;

public class DetectionRythme implements RyhtmeInterface, KinectListenerInterface {

	KinectInterface kinect;
	LectureAudioSimpleInterface audio;
	int BPM;
	
	static float trigger = (float) 0.1;
	static float limitUp = (float) 0.8;
	static float limitDown = (float) 0.1;
	
	//Booleans needed to make an hysteresis when some movements are noticed
	boolean limitUpExceeded = false;
	boolean limitDownExceeded = false;
	boolean leftHandAboveHead = false;
	boolean rightHandAboveHead = false;
	
	long lastDate = 0;
	long lastPeriod[] = {0,0,0,0,0,0,0,0,0,0};
	int lastPeriodPointer = 0;
	long mean = 500;
	
	public void initRythmeModule(KinectInterface kinect, LectureAudioSimpleInterface audio) 
	{
		this.kinect = kinect;
		this.BPM = 110;
		this.audio = audio;
		this.audio.startBeating(120);
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
		if (lastDate != 0)
		{
			long currentDate = new Date().getTime();
			long timeElapsed = currentDate - lastDate;
			lastDate = currentDate;
			mean = ((mean*10) + timeElapsed - lastPeriod[lastPeriodPointer])/10;
			lastPeriod[lastPeriodPointer] = timeElapsed;
			if(lastPeriodPointer <= 8)
				lastPeriodPointer++;
			else
				lastPeriodPointer = 0;
			audio.updateBPM(getCurrentTrueBPM());
		}
		else
			lastDate = new Date().getTime();
			
	}

	public int getCurrentTrueBPM() 
	{
		return (int) (60/mean);
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

