package classification;

import java.io.File;
import java.io.FilenameFilter;
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
public class Classification implements ClassificationInterface, KinectListenerInterface {

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
	public void initClassificationModule(KinectInterface kinectModule, MovementFoundInterface engine) {

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
			numberOfSkeletonReceived = 0;
		}
		
		recognize();
	}

	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListening() {
		kinectModule.unsetListener(this);		
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
		double distanceMin = Double.POSITIVE_INFINITY;
		int movementsCount = 0;
		
		Movement tmpResult = null;
		
		for(Movement movement : movements)
		{
			movementsCount++;
			double totalMovementDistance = 0;
			double meanMovementDistance = 0;
			for(Gesture gesture : movement.getGestures())
			{
				double totalGestureDistance = 0;
				double meanGestureDistance = 0;
				int gestureSize = gesture.size();
				if(fifoRightHand.getSize() < gestureSize)
					return tmpResult;
				
				Gesture shortedFIFO = fifoRightHand.getNlastPoints(gestureSize);
				
				for(int i = 0; i < gestureSize; i++)
					totalGestureDistance += (double) gesture.getPoint(i).distanceTo(shortedFIFO.getPoint(i));
				
				meanGestureDistance = totalGestureDistance / gestureSize;
				totalMovementDistance += meanGestureDistance;
			}
			meanMovementDistance = totalMovementDistance / movementsCount;
			
			if (meanMovementDistance <= distanceMin)
			{
				distanceMin = meanMovementDistance;
				tmpResult = movement;
			}
			System.out.println(movement.getPath() + " - " + meanMovementDistance);
		}
		//System.out.println(tmpResult.getPath() + " - " + distanceMin);
		return tmpResult;
	}
}

