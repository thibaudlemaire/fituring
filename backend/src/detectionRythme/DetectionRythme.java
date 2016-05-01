package detectionRythme;

import java.util.Date;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.BPMupdateInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.RyhtmeInterface;

public class DetectionRythme implements RyhtmeInterface, KinectListenerInterface {

	KinectInterface kinect;
	BPMupdateInterface engine;
	
	static float trigger = (float) 0.1;
	static float limitUp = (float) 0.5;
	static float limitDown = (float) 0.1;
	
	//Booleans needed to make an hysteresis when some movements are noticed
	boolean limitUpExceeded = false;
	boolean limitDownExceeded = false;
	boolean leftHandAboveHead = false;
	boolean rightHandAboveHead = false;
	
	long lastDate = 0;
	long lastPeriod[] = {500,500,500};
	int lastPeriodPointer = 0;
	long mean = 500;
	
	public void initRythmeModule(KinectInterface kinect, BPMupdateInterface engine) 
	{
		this.kinect = kinect;
		this.mean = 500;
		this.engine = engine;
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
			//beat();
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
			long timeElapsed = (long)(currentDate - lastDate)/2;
			lastDate = currentDate;		
			if (!(timeElapsed <= mean*1.5 && timeElapsed >= mean*0.5))
				timeElapsed = mean;
			lastPeriod[lastPeriodPointer] = timeElapsed;
			long somme = 0;
			for(int i=0; i<3; i++)
				somme += lastPeriod[i];
			mean = somme / 3;
			if(lastPeriodPointer < 2)
				lastPeriodPointer++;
			else
				lastPeriodPointer = 0;
			engine.updateBPM(getBPM());
			System.out.println("Mise � jour BPM : " + getBPM());
		}
		else
			lastDate = new Date().getTime();
			
	}

	public void startListening() 
	{
		kinect.setListener(this);
	}

	public void stopListening() 
	{
		kinect.unsetListener(this);
		mean = 500;
		lastPeriod[0] = 500;
		lastPeriod[1] = 500;
		lastPeriod[1] = 500;
		lastDate = 0;
	}

	@Override
	public int getBPM() {
		// TODO Auto-generated method stub
		return (int) (60000/mean);
	}

	@Override
	public int getSimpleBPM() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWealth() {
		// TODO Auto-generated method stub
		return 0;
	}

}

