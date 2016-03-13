package main;

import java.util.Scanner;

import classification.Classification;
import kinect.Kinect;

public class Alpha {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn = "";

		System.out.println("Création des instances");
		Kinect kinect = new Kinect();
		Classification cl = new Classification();
		
		System.out.println("Initialisation des modules");
		cl.initClassificationModule(new Object(), kinect);
		kinect.initKinectModule();

		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour démarer l'analyse (q pour quitter)");
			strIn = sc.nextLine();
		    if(strIn == "q")
		    	break;
		    
		    cl.startListening();
		    sc.nextLine();
		    cl.stopListeninf();
		}
	    
		kinect.stop();	
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
