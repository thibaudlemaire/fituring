package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import classification.Classification2;
import classification.Movement;
import classification.Recorder;
import detectionRythme.DetectionRythme;
import interfaces.LectureInterface;
import kinect.Kinect;
import syntheseAudio.LectureAudio;

public class Alpha {

	public static void main(String[] args) 
	{
	    Scanner sc = new Scanner(System.in);
	    String strIn;

		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		//Classification cl = new Classification();
		//Classification2 cl = new Classification2();
		Recorder cl = new Recorder();
		LectureAudio audio = new LectureAudio();
		//DetectionRythme dr = new DetectionRythme();
		
		System.out.println("Initialisation des modules");
		kinect.initKinectModule();
		audio.initLectureAudioModule(new Object(), 100);
		cl.initClassificationModule(kinect);
		//dr.initRythmeModule(kinect, audio);
		/*
		Movement m = null;
		File fichier =  new File("datas/Test.mvt") ;
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichier));
			m = (Movement)ois.readObject() ;
			ois.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cl.addMovement(m);
		*/
		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour commencer (q pour quitter)");
			strIn = sc.nextLine();
		    if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
		    	break;
			System.out.println("Ajout des listeners");

		    cl.startListening();
		    //dr.startListening();
		    sc.nextLine();
		    cl.stopListening();
		    //dr.stopListening();
		}
		//System.out.println(cl.nDollarRecognizer());
	    System.out.println("Stopping Kinect");
		kinect.stop();	
	    System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}

}
