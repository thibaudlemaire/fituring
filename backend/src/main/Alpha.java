package main;

import classification.Classification;
import kinect.Kinect;

public class Alpha {

	public static void main(String[] args) 
	{
		System.out.println("Création des instances");
		Kinect kinect=new Kinect();
		Classification cl = new Classification();
		
		System.out.println("Initialisation des modules");
		cl.initClassificationModule(new Object(), kinect);
		kinect.initKinectModule();

		System.out.print("Appuyer sur une Entrer pour démarer l'analyse");
		
		try {Thread.sleep(20000);} catch (InterruptedException e) {}
		
		kinect.stop();	
		
		System.out.println("FPS: "+kinect.getFPS());
	}

}
