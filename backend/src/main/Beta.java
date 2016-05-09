package main;

import java.util.Scanner;

import classification.Classification;
import detectionRythme.DetectionRythme;
import engine.MoteurMusical;
import kinect.Kinect;
import syntheseAudio.LectureAudio;

public class Beta {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn;

		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		Classification cl = new Classification();
		LectureAudio audio = new LectureAudio();
		DetectionRythme dr = new DetectionRythme();
		MoteurMusical engine = new MoteurMusical();
		
		System.out.println("Initialisation des modules");
		engine.initEngine(audio, cl);
		kinect.initKinectModule();
		audio.initLectureAudioModule(100);
		cl.initClassificationModule(kinect, engine);
		dr.initRythmeModule(kinect, engine);
		
		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour commencer (q pour quitter)");
			strIn = sc.nextLine();
		    if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
		    	break;
			System.out.println("Ajout des listeners");

		    cl.startListening();
		    dr.startListening();
		    engine.startFituring();
		    audio.addLoop("sounds/kick.wav", new boolean[] {false}, new boolean[] {false}, 0);
		    sc.nextLine();
		    engine.stopFituring();
		    cl.stopListening();
		    dr.stopListening();
		    audio.delLoop(0);
		}
	    System.out.println("Stopping Kinect");
		kinect.stop();	
	    System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
