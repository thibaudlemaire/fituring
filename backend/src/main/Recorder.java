package main;

import java.util.Scanner;

import classification.MovementRecorder;
import classification.MovementSerializer;
import kinect.Kinect;

public class Recorder {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn;
	    
		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		MovementRecorder mr = new MovementRecorder(kinect);
		MovementSerializer ms = new MovementSerializer();
		
		System.out.println("Initialisation des modules");
		kinect.initKinectModule();

		while(true)
		{
			System.out.println("Appuyer sur une Entrer pour démarer l'enregistrement");
		    strIn = sc.nextLine();
		    if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
		    	break;
		    
		    mr.startListener();
		    
		    sc.nextLine();
		    mr.stopListener();
		    
		    System.out.println("Nommer cet enregistrement");
		    strIn = sc.nextLine();
		    ms.serialize(mr.getMovement(), "datas/" + strIn);
		    mr = new MovementRecorder(kinect);
		}
		
		System.out.println("Stopping Kinect");
		kinect.stop();	
	    System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
