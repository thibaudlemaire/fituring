package main;

import java.util.Scanner;

import classification.distanceHands;
import kinect.Kinect;

public class AlphaV2 {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn;

		System.out.println("Création des instances");
		Kinect kinect = new Kinect();
		distanceHands cl = new distanceHands();
		
		System.out.println("Initialisation des modules");
		kinect.initKinectModule();
		cl.initClassificationModule(new Object(), kinect);
		
		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour démarer l'analyse (q pour quitter)");
			strIn = sc.nextLine();
		    if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
		    	break;
		    
		    cl.startListening();
		    sc.nextLine();
		    cl.stopListeninf();
		}
	    System.out.println("Stopping Kinect");
		kinect.stop();	
	    System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
