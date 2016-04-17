package main;

import java.util.Scanner;

import classification.distanceHands;
import interfaces.LectureInterface;
import kinect.Kinect;
import syntheseAudio.LectureAudio;

public class AlphaV2 {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn;

		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		distanceHands cl = new distanceHands();
		LectureAudio audio = new LectureAudio();
		
		System.out.println("Initialisation des modules");
		kinect.initKinectModule();
		audio.initLectureAudioModule(new Object(), 100);
		cl.initClassificationModule(new Object(), kinect, (LectureInterface) audio);
		
		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour demarer l'analyse (q pour quitter)");
			strIn = sc.nextLine();
		    if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
		    	break;
		    
		    cl.startListening();
		    sc.nextLine();
		    cl.stopListening();
		}
	    System.out.println("Stopping Kinect");
		kinect.stop();	
	    System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
