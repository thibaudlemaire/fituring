package classification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
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

	Hashtable<Integer, FIFO> fifos = new Hashtable<Integer, FIFO>();

	Vector<Movement> movements = new Vector<Movement>();

	private int numberOfSkeletonReceived = 0; //Used to record a skeleton every 333ms
	private boolean firstSkeletonReceived = true; //Used in resampling
	private boolean recorder = false;
	private boolean callRecognize = false;
	private boolean confidenceValueExceeded = false;

	///////Options :
	static final int resetSkeletonNumber = 10; //Adds coordinates in the file every resetSkeletonNumber skeleton received
	double confidenceValue = 0.3; //Movement recognized if the probability of recognition is superior to confidenceValue
	double threshold = 0.05;
	double minimumValue;
	static final float resamplingDistance = (float) 0.05; //distance between two points in resampling

	public static final int RECORD_LEFT_HAND = 0;
	public static final int RECORD_RIGHT_HAND = 1;
	public static final int RECORD_BOTH_HANDS = 2;
	public static final int RECORD_LEFT_HAND_ELBOW = 3;
	public static final int RECORD_RIGHT_HAND_ELBOW = 4;
	public static final int RECORD_BOTH_HANDS_ELBOWS = 5;

	@Override
	public void initClassificationModule(KinectInterface kinectModule, MovementFoundInterface engine) {

		this.kinectModule = kinectModule;
		this.engine = engine ;


		// INITIALISATION DE LA HASHTABLE DE FIFOS
		fifos.put(Skeleton.HAND_RIGHT, new FIFO(100, Skeleton.HAND_RIGHT));
		fifos.put(Skeleton.HAND_LEFT, new FIFO(100, Skeleton.HAND_LEFT));
		fifos.put(Skeleton.ELBOW_RIGHT, new FIFO(100, Skeleton.ELBOW_RIGHT));
		fifos.put(Skeleton.ELBOW_LEFT, new FIFO(100, Skeleton.ELBOW_LEFT));

	}

	public void skeletonReceived(KinectEventInterface e){  //automatically called when a new skeleton is captured by the kinect
		// TODO Auto-generated method stub
		numberOfSkeletonReceived++;
		Skeleton newSkeleton = e.getNewSkeleton();

		// Changing base
		float baseX = newSkeleton.get3DJointX(Skeleton.SPINE_MID);
		float baseY = newSkeleton.get3DJointY(Skeleton.SPINE_MID);
		float baseZ = newSkeleton.get3DJointZ(Skeleton.SPINE_MID);
		Point rightHandPoint = new Point(newSkeleton.get3DJointX(Skeleton.HAND_RIGHT) - baseX, 
				newSkeleton.get3DJointY(Skeleton.HAND_RIGHT) - baseY,
				newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT) - baseZ);

		Point rightElbowPoint = new Point(newSkeleton.get3DJointX(Skeleton.ELBOW_RIGHT) - baseX, 
				newSkeleton.get3DJointY(Skeleton.ELBOW_RIGHT) - baseY,
				newSkeleton.get3DJointZ(Skeleton.ELBOW_RIGHT) - baseZ);


		Point leftElbowPoint = new Point(newSkeleton.get3DJointX(Skeleton.ELBOW_LEFT) - baseX, 
				newSkeleton.get3DJointY(Skeleton.ELBOW_LEFT) - baseY,
				newSkeleton.get3DJointZ(Skeleton.ELBOW_LEFT) - baseZ);

		Point leftHandPoint = new Point(newSkeleton.get3DJointX(Skeleton.HAND_LEFT) - baseX, 
				newSkeleton.get3DJointY(Skeleton.HAND_LEFT) - baseY,
				newSkeleton.get3DJointZ(Skeleton.HAND_LEFT) - baseZ);

		////Spatial resampling

		//First skeleton received : distance calculation is impossible, so we simply add coordinates and save the first skeleton in currentSkeleton
		if (firstSkeletonReceived) {
			fifos.get(Skeleton.HAND_RIGHT).addCapture(rightHandPoint);
			fifos.get(Skeleton.HAND_LEFT).addCapture(leftHandPoint);
			fifos.get(Skeleton.ELBOW_RIGHT).addCapture(rightElbowPoint);
			fifos.get(Skeleton.ELBOW_LEFT).addCapture(leftElbowPoint);
			firstSkeletonReceived = false;
			return ;
		}

		//Ajout dans la file au moins toutes les 333ms
		if ((numberOfSkeletonReceived >= resetSkeletonNumber) 
				|| (rightHandPoint.distanceTo(fifos.get(Skeleton.HAND_RIGHT).getLastPoint()) > resamplingDistance)) {
			fifos.get(Skeleton.HAND_RIGHT).addCapture(rightHandPoint);
			numberOfSkeletonReceived = 0;
			callRecognize = true;
		}

		if ((numberOfSkeletonReceived >= resetSkeletonNumber) 
				|| (rightElbowPoint.distanceTo(fifos.get(Skeleton.ELBOW_RIGHT).getLastPoint()) > resamplingDistance)) {
			fifos.get(Skeleton.ELBOW_RIGHT).addCapture(rightElbowPoint);
			numberOfSkeletonReceived = 0;
			callRecognize = true;
		}


		if ((numberOfSkeletonReceived >= resetSkeletonNumber) 
				|| (leftElbowPoint.distanceTo(fifos.get(Skeleton.ELBOW_LEFT).getLastPoint()) > resamplingDistance)) {
			fifos.get(Skeleton.ELBOW_LEFT).addCapture(leftElbowPoint);
			numberOfSkeletonReceived = 0;
			callRecognize = true;
		}

		if ((numberOfSkeletonReceived >= resetSkeletonNumber) 
				|| (leftHandPoint.distanceTo(fifos.get(Skeleton.HAND_LEFT).getLastPoint()) > resamplingDistance)) {
			fifos.get(Skeleton.HAND_LEFT).addCapture(leftHandPoint);
			numberOfSkeletonReceived = 0;
			callRecognize = true;
		}

		if (callRecognize && !recorder)
		{
			recognize();
			callRecognize = false;
		}
	}

	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListening() {
		kinectModule.unsetListener(this);		
	}

	public void saveMovement(String gestureName, int whichJoint) {
		Movement mvt = new Movement(gestureName + ".mvt");
		if(whichJoint == RECORD_LEFT_HAND || whichJoint == RECORD_BOTH_HANDS)
			mvt.addGesture(fifos.get(Skeleton.HAND_LEFT).getAll());
		if(whichJoint == RECORD_RIGHT_HAND || whichJoint == RECORD_BOTH_HANDS)
			mvt.addGesture(fifos.get(Skeleton.HAND_RIGHT).getAll());
		if(whichJoint == RECORD_RIGHT_HAND_ELBOW || whichJoint == RECORD_BOTH_HANDS_ELBOWS) {
			mvt.addGesture(fifos.get(Skeleton.HAND_RIGHT).getAll());
			mvt.addGesture(fifos.get(Skeleton.ELBOW_RIGHT).getAll());
		}
		if(whichJoint == RECORD_LEFT_HAND_ELBOW || whichJoint == RECORD_BOTH_HANDS_ELBOWS) {
			mvt.addGesture(fifos.get(Skeleton.HAND_LEFT).getAll());
			mvt.addGesture(fifos.get(Skeleton.ELBOW_LEFT).getAll());
		}

		File fichier =  new File("movement.database/" + gestureName + ".mvt") ;
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

	public void clearFifos()
	{
		for (Enumeration<Integer> e = fifos.keys() ; e.hasMoreElements() ; ) { 
			Integer key = e.nextElement();
			FIFO fifo = fifos.get(key);
			fifo.clear();
		}
	}

	public int addGesture(String path) {
		Movement m = null;
		File fichier =  new File("movement.database/" + path) ;
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichier));
			m = (Movement)ois.readObject() ;
			m.setMovementID(movements.size());
			movements.add(m);
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
		return movements.size() - 1;
	}

	@Override
	public int getNumberOfGestures() {
		return movements.size();
	}

	public Movement recognize()
	{	
		double distanceMin = Double.POSITIVE_INFINITY;

		Movement tmpResult = null;

		for(Movement movement : movements)
		{
			double totalMovementDistance = 0;
			double meanMovementDistance = 0;
			int gestureCount = 0;

			for(Gesture gesture : movement.getGestures())
			{
				gestureCount++;
				double totalGestureDistance = 0;
				double meanGestureDistance = 0;
				int gestureSize = gesture.size();

				FIFO currentFIFO = fifos.get(gesture.getJointID());
				if (currentFIFO.getSize() < gestureSize)
					return tmpResult;
				Gesture shortedFIFO = currentFIFO.getNlastPoints(gestureSize);

				for(int i = 0; i < gestureSize; i++)
					totalGestureDistance += (double) gesture.getPoint(i).distanceTo(shortedFIFO.getPoint(i));

				meanGestureDistance = totalGestureDistance / gestureSize;
				totalMovementDistance += meanGestureDistance;
			}
			meanMovementDistance = totalMovementDistance / gestureCount;
			gestureCount = 0;

			if (meanMovementDistance <= distanceMin)
			{
				distanceMin = meanMovementDistance;
				tmpResult = movement;
			}
		}

		if(distanceMin <= confidenceValue && confidenceValueExceeded == false) {
			confidenceValueExceeded = true;
			System.out.println(tmpResult.getPath() + " - " + distanceMin);
			engine.MovementDone(tmpResult.getMovementID());
		}

		if (confidenceValueExceeded == true && distanceMin > (confidenceValue + threshold))
			confidenceValueExceeded = false;

		return tmpResult;
	}

	public void setRecorder()
	{
		recorder = true;
	}
}

