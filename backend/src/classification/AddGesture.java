package classification;

import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.Vector;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import ndollar.NDollarParameters;
import ndollar.NDollarRecognizer;
import ndollar.PointR;
import ndollar.Utils;
/**
 * This class allows to add a gesture using coordinates given by the Kinect
 * @author robin
 *
 */
public class AddGesture implements KinectListenerInterface {
	
	KinectInterface kinectModule;

	Vector<PointR> points = new Vector<PointR>(); //Points where coordinates of the gesture to be recorded will be saved
	Vector<Vector<PointR>> strokes = new Vector<Vector<PointR>>(); //Stroke where these previous points will be saved at the end of the record
	static NDollarRecognizer _rec = new NDollarRecognizer();
	
	int numberOfSkeletonReceived = 0; //Used to record a skeleton every 333ms
	Skeleton currentSkeleton = new Skeleton(); //Used to rescale spacially
	
	///////Options :
	int resetSkeletonNumber; //Force to add coordinates in the file every resetSkeletonNumber skeleton received
	float resamplingDistance; //Distance between two coordinated resampled
	
	boolean firstSkeletonReceived = true; //Used in resampling
	
	
	//Constructor
	public AddGesture(KinectInterface kinect) {
		Object[] params = ClassificationAncien.getParameters();
		this.resetSkeletonNumber = (int) params[0];
		this.resamplingDistance = (float) params[1];
		this.kinectModule = kinect;
	}

	
	public void skeletonReceived(KinectEventInterface e){  //automatically called when a new skeleton is captured by the kinect
		numberOfSkeletonReceived++;
		Skeleton newSkeleton = e.getNewSkeleton();
		float baseX = newSkeleton.get3DJointX(Skeleton.SPINE_MID);
		float baseY = newSkeleton.get3DJointY(Skeleton.SPINE_MID);
		float baseZ = newSkeleton.get3DJointZ(Skeleton.SPINE_MID);
		float[] handRightCoordinates = new float[3];
		handRightCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT)-baseX;
		handRightCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_RIGHT)-baseY;
		handRightCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT)-baseZ;
		
		
		////Spatial resampling
		
		//First skeleton received : distance calculation is impossible, so we simply add coordinates and save the first skeleton in currentSkeleton
		if (firstSkeletonReceived) {
			points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			firstSkeletonReceived = false;
			currentSkeleton = newSkeleton;
			return ;
		}
		
		//Plus rapide. A supprimer a la version finale quand on aura plein de points du squelette (ici on ne s'interesse qu'a la main droite)
		float[] handRightCoordinatestmp = new float[3];
		handRightCoordinatestmp[0] = currentSkeleton.get3DJointX(Skeleton.HAND_RIGHT)-baseX;
		handRightCoordinatestmp[1] = currentSkeleton.get3DJointY(Skeleton.HAND_RIGHT)-baseY;
		handRightCoordinatestmp[2] = currentSkeleton.get3DJointZ(Skeleton.HAND_RIGHT)-baseZ;
		
		
		//Ajout dans la file au moins toutes les 333ms
		if (numberOfSkeletonReceived >= resetSkeletonNumber) {
			points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			numberOfSkeletonReceived = 0;
			currentSkeleton = newSkeleton;
			return ;
		}
		
		if (distance(handRightCoordinates, handRightCoordinatestmp) < resamplingDistance) {
			return ;
		}
		//Si on arrive ici, 10 squelettes n'ont pas été recus depuis le dernier enregistrement dans la file et il n'y a pas eu de déplacement inférieur à resamplingDistance
		numberOfSkeletonReceived = 0;
		points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
		currentSkeleton = newSkeleton;
	}
	
	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListening() {
		
		points = Utils.treatement(points); //Here, points are translated and rescaled
		strokes.add(new Vector<PointR>(points));
		Scanner sc = new Scanner(System.in);
		
		//Used to set the name of the gesture recorded
		System.out.println("Entrer le nom du mouvement : ");
		String name = sc.nextLine();
		while (name == null || name.equals("")) {
			System.out.println("Champ vide ! Entrer le nom du mouvement : ");
			name = sc.nextLine();
		}
		
		saveGesture(name);
		strokes.clear();
		points.clear();
		kinectModule.unsetListener(this);		
	}
	
	//Distance calculator. Used for resample
	public float distance(float[] coordinates1, float[] coordinates2) {
		float x = coordinates1[0] - coordinates2[0];
		float y = coordinates1[1] - coordinates2[1];
		return (float) Math.sqrt(x*x + y*y);
	}
	
	//Gesture saving method. Gestures are saved in .xml format
	public void saveGesture(String name) {
		if (strokes == null || strokes.size() == 0) {
			System.out.println("Cannot save - no gesture!");
			return;
		}
		Vector<Integer> numPtsInStroke = new Vector<Integer>();
		Enumeration<Vector<PointR>> en = strokes.elements();
		while (en.hasMoreElements()) {
			Vector<PointR> pts = en.nextElement();
			numPtsInStroke.add(pts.size());
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		if (_rec.SaveGesture(
				NDollarParameters.getInstance().SamplesDirectory
						+ "\\"
						+ name
						+ "_"
						+ dateFormat.format(GregorianCalendar.getInstance()
								.getTime()) + ".xml", strokes, numPtsInStroke)) {
			System.out.println("Gesture named <<" + name + ">> saved.");
		} else {
			System.out.println("Gesture save failed.");
		}
	}
}
