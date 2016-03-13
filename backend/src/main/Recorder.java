package main;

import java.util.Scanner;

import classification.Classification;
import kinect.Kinect;

public class Recorder {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);

		System.out.println("Création des instances");
		Kinect kinect = new Kinect();
		Classification cl = new Classification();
		
		System.out.println("Initialisation des modules");
		cl.initClassificationModule(new Object(), kinect);
		kinect.initKinectModule();

		System.out.print("Appuyer sur une Entrer pour démarer l'enregistrement");
	    sc.nextLine();
	    
	    cl.startListening();
	    
	    sc.nextLine();
		kinect.stop();	
		
		sc.close();
		
		System.out.println("FPS: "+kinect.getFPS());
	}

}
