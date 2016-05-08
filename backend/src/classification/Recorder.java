package classification;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;
import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.MovementFoundInterface;

/**
 * This class describes how gestures are recognized
 * @author robin
 *
 */
public class Recorder implements ClassificationInterface, KinectListenerInterface {

	MovementFoundInterface engine ;
	KinectInterface kinectModule;

	FIFO fifoRightHand = new FIFO(100, Skeleton.HAND_LEFT);
	Vector<Movement> movements = new Vector<Movement>();
	
	int numberOfSkeletonReceived = 0; //Used to record a skeleton every 333ms
	boolean firstSkeletonReceived = true; //Used in resampling
	int numberOfGestures = 0; //Contains the number of gestures in the database


	///////Options :
	static final int resetSkeletonNumber = 10; //Adds coordinates in the file every resetSkeletonNumber skeleton received
	double confidenceValue = 0.85; //Movement recognized if the probability of recognition is superior to confidenceValue
	static final float resamplingDistance = (float) 0.05; //distance between two points in resampling

	
	
	@Override
	public void initClassificationModule(KinectInterface kinectModule) {

		this.kinectModule = kinectModule;
		
		/////// A remettre : comment� pour les tests
		//this.engine = engine ;
		
		// DEBUG :
		File currentDir = new File("datas");
		File[] allMVTFiles = currentDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".mvt");
			}
		});

		// read them and determine the maximum length of a distance
		for (int i = 0; i < allMVTFiles.length; ++i) {
		
			numberOfGestures++;
		}
		// FIN DEBUG

	}

	public void skeletonReceived(KinectEventInterface e){  //automatically called when a new skeleton is captured by the kinect
		// TODO Auto-generated method stub
		numberOfSkeletonReceived++;
		Skeleton newSkeleton = e.getNewSkeleton();
		
		// Changing base
		float baseX = newSkeleton.get3DJointX(Skeleton.SPINE_MID);
		float baseY = newSkeleton.get3DJointY(Skeleton.SPINE_MID);
		float baseZ = newSkeleton.get3DJointZ(Skeleton.SPINE_MID);
		Point newPoint = new Point(newSkeleton.get3DJointX(Skeleton.HAND_RIGHT) - baseX, 
						newSkeleton.get3DJointY(Skeleton.HAND_RIGHT) - baseY,
						newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT) - baseZ);
		
		////Spatial resampling
		
		//First skeleton received : distance calculation is impossible, so we simply add coordinates and save the first skeleton in currentSkeleton
		if (firstSkeletonReceived) {
			fifoRightHand.addCapture(newPoint);
			firstSkeletonReceived = false;
			return ;
		}

		//Ajout dans la file au moins toutes les 333ms
		if ((numberOfSkeletonReceived >= resetSkeletonNumber) 
				|| (newPoint.distanceTo(fifoRightHand.getLastPoint()) > resamplingDistance)) {
			fifoRightHand.addCapture(newPoint);
			System.out.println("Nouveau point");
			numberOfSkeletonReceived = 0;
		}
	}

	public void startListening() {
		fifoRightHand.clear();
		kinectModule.setListener(this);
	}

	public void stopListening() {
		kinectModule.unsetListener(this);	
		Movement mvt = new Movement("Test.mvt");
		Gesture right = fifoRightHand.getAll();
		mvt.addGesture(right);
		
		File fichier =  new File("datas/Test.mvt") ;
		try {
			ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier)) ;
			oos.writeObject(mvt);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public int addGesture(String path) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void addMovement(Movement mvt)
	{
		movements.add(mvt);
	}
	@Override
	public int getNumberOfGestures() {
		return movements.size();
	}
	
	public Movement recognize()
	{
		int distanceMin = Integer.MAX_VALUE;
		int movementsCount = 0;
		
		Movement tmpResult = null;
		
		for(Movement movement : movements)
		{
			movementsCount++;
			int totalMovementDistance = 0;
			int meanMovementDistance = 0;
			for(Gesture gesture : movement.getGestures())
			{
				int totalGestureDistance = 0;
				int meanGestureDistance = 0;
				int gestureSize = gesture.size();
				Gesture shortedFIFO = fifoRightHand.getNlastPoints(gestureSize);
				
				for(int i = 0; i < gestureSize; i++)
					totalGestureDistance += gesture.getPoint(i).distanceTo(shortedFIFO.getPoint(i));
				
				meanGestureDistance = totalGestureDistance / gestureSize;
				totalMovementDistance += meanGestureDistance;
			}
			meanMovementDistance = totalMovementDistance / movementsCount;
			
			if (meanMovementDistance <= distanceMin)
				tmpResult = movement;
		}
		System.out.println(tmpResult.getPath() + " - " + distanceMin);
		return tmpResult;
	}
}

