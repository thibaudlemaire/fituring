package detectionRythme;

import interfaces.KinectEventInterface;
import interfaces.KinectListenerInterface;
import interfaces.RyhtmeInterface;

public class DetectionRythme implements RyhtmeInterface, KinectListenerInterface{

	private double bpm;
	KinectListenerInterface kinect;
	
	public void initRythmeModule(KinectListenerInterface kinect) 
	{
		this.kinect = kinect;
		this.bpm = 110;
	}
	
	public void skeletonReceived(KinectEventInterface e) 
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

}
