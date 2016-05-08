package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Scanner;

import classification.Classification;
import classification.Movement;
import classification.Recorder;
import detectionRythme.DetectionRythme;
import interfaces.LectureInterface;
import kinect.Kinect;
import syntheseAudio.LectureAudio;

public class RecorderLauncher {

	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		String strIn;

		System.out.println("Creation des instances");
		Kinect kinect = new Kinect();
		Recorder rec = new Recorder();

		System.out.println("Initialisation des modules");
		kinect.initKinectModule();
		rec.initClassificationModule(kinect);


		while(true)
		{
			System.out.print("Appuyer sur une Entrer pour commencer (q pour quitter)");
			strIn = sc.nextLine();

			if (strIn.length() >= 1 && strIn.charAt(0) == 's') {
				Scanner scName = new Scanner(System.in);
				System.out.println("Entrer le nom du mouvement : ");
				String gestureName = scName.nextLine();
				while (gestureName == null || gestureName.equals("")) {
					System.out.println("Champ vide ! Entrer le nom du mouvement : ");
					gestureName = sc.nextLine();
				}
				scName.close();
				rec.setGestureName(gestureName);
				rec.stopListening();
				System.out.print("Appuyer sur une Entrer pour commencer, s pour sauvegarder le mouvement, q pour quitter");
				strIn = sc.nextLine();
			}

			if(strIn.length() >= 1 && strIn.charAt(0) == 'q')
				break;
			System.out.println("Ajout des listeners");

			rec.startListening();
			sc.nextLine();
		}
		System.out.println("Stopping Kinect");
		kinect.stop();	
		System.out.println("Closing scanner stream");
		sc.close();
		System.out.println("FPS: "+kinect.getFPS());
	}
}
