package main;

import java.util.Scanner;

import kinect.Kinect;
import classification.AddGesture;

public class GestureRecordingLauncher {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn;

		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		AddGesture addGesture;
		
		System.out.println("Initialisation des modules");
		kinect.initKinectModule();
		addGesture = new AddGesture(kinect);
		
		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour commencer, s pour sauvegarder le mouvement, q pour quitter");
			strIn = sc.nextLine();
			if (strIn.length() >= 1 && strIn.charAt(0) == 's') {
		    	addGesture.stopListening();
		    	System.out.print("Appuyer sur une Entrer pour commencer, s pour sauvegarder le mouvement, q pour quitter");
		    	strIn = sc.nextLine();
		    }
		    if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
		    	break;
			System.out.println("Ajout des listeners");

		    addGesture.startListening();
		    sc.nextLine();
		}
	    System.out.println("Stopping Kinect");
		kinect.stop();	
	    System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
