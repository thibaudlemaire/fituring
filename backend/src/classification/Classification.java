package classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEvent;
import interfaces.KinectInterface;
import interfaces.KinectListener;

public class Classification implements ClassificationInterface, KinectListener {
	
	Object BDD ;

	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		kinectModule.setListener(this);
		this.BDD = BDD ;	
	}
		

	@Override
	public void skeletonReceived(KinectEvent e) {
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		double[] handLeftCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_LEFT);
		double[] handRightCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_RIGHT);
			
	}

	private static double[][][] getDatasTable(String path) throws FileNotFoundException {
		BufferedReader reader = null;
		String line;
		try {
			reader = new BufferedReader(new FileReader(path));
			
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Fichier introuvable");
		}
		while((line = reader.readLine()) != null)
		{
			String[] items = line.split(";");
			long time = Long.parseLong(items[0]);
			for(int i = 1; i<25; i++)
			{
				
			}
			
		}
			return datasTable;
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		getDatasTable("datas/armsUp.csv");
	}
}
