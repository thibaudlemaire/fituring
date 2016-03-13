package main;

import java.util.Scanner;

import classification.MovementRecorder;
import classification.MovementSerializer;
import kinect.Kinect;

public class Recorder {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn = "";
	    
		System.out.println("Création des instances");
		Kinect kinect = new Kinect();
		MovementRecorder mr = new MovementRecorder(kinect);
		MovementSerializer ms = new MovementSerializer();
		
		System.out.println("Initialisation des modules");
		kinect.initKinectModule();

		while(true)
		{
			System.out.println("Appuyer sur une Entrer pour démarer l'enregistrement");
		    sc.nextLine();
		    if(strIn == "q")
		    	break;
		    mr.startListener();
		    
		    sc.nextLine();
		    mr.stopListener();
		    
		    System.out.println("\r\n" + "Nommer cet enregistrement");
		    strIn = sc.nextLine();
		    ms.serialize(mr.getMovement(), "datas/" + strIn);
		}
		kinect.stop();	
		
		sc.close();
		
		System.out.println("FPS: "+kinect.getFPS());
	}

}
