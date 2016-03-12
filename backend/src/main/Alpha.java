package main;

import classification.Classification;
import kinect.Kinect;

public class Alpha {

	public static void main(String[] args) 
	{
		System.out.println("Debut");
		Kinect kinect=new Kinect();
		Classification cl = new Classification();
		cl.initClassificationModule(new Object(), kinect);
		
		kinect.initKinectModule();

		try {Thread.sleep(10000);} catch (InterruptedException e) {}
		
		kinect.stop();	
		
		System.out.println("FPS: "+kinect.getFPS());
	}

}
