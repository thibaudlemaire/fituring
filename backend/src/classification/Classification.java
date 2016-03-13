package classification;

import java.util.ArrayList;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class Classification implements ClassificationInterface, KinectListenerInterface {
	
	Object BDD ;
	KinectInterface kinectModule;
	
	private float[][] firstMoveLeft = new float[30][3];
	private float[][] secondMoveLeft = new float[30][3];
	private float[][] firstMoveRight = new float[30][3];
	private float[][] secondMoveRight = new float[30][3];
	private DatasFIFO datasFIFOLeft = new DatasFIFO(30);
	private DatasFIFO datasFIFORight = new DatasFIFO(30);
	
	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		this.kinectModule = kinectModule;
		this.BDD = BDD ;	
		
		MovementSerializer moveSerial = new MovementSerializer();
		Move mvt1=moveSerial.deSerialize("datas/m1.mvt");
		Move mvt2=moveSerial.deSerialize("datas/m2.mvt");
		ArrayList<Step> steps1 = mvt1.steps;
		ArrayList<Step> steps2 = mvt2.steps;
		
		
		
		for (int i =0; i<30; i++) 
		{
			firstMoveLeft[i]=steps1.get(i).getCoordinates().get(Skeleton.HAND_LEFT);
			firstMoveRight[i]=steps1.get(i).getCoordinates().get(Skeleton.HAND_RIGHT);
			secondMoveLeft[i]=steps2.get(i).getCoordinates().get(Skeleton.HAND_LEFT);
			secondMoveRight[i]=steps2.get(i).getCoordinates().get(Skeleton.HAND_RIGHT);
		}
	}

	public void skeletonReceived(KinectEventInterface e){
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		float[] handLeftCoordinates = new float[3];
		handLeftCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_LEFT);
		handLeftCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_LEFT);
		handLeftCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_LEFT);
		float[] handRightCoordinates = new float[3];
		handRightCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT);
		handRightCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_RIGHT);
		handRightCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT);
		datasFIFOLeft.addData(handLeftCoordinates);
		datasFIFORight.addData(handRightCoordinates);
		DTW dtw1L = new DTW(firstMoveLeft, datasFIFOLeft.getFIFOTab());
		DTW dtw1R = new DTW(firstMoveRight, datasFIFORight.getFIFOTab());
		DTW dtw2L = new DTW(secondMoveLeft, datasFIFOLeft.getFIFOTab());
		DTW dtw2R = new DTW(secondMoveRight, datasFIFORight.getFIFOTab());
		double distance1L = dtw1L.DTWDistance();
		double distance1R = dtw1R.DTWDistance();
		double distance2L = dtw2L.DTWDistance();
		double distance2R = dtw2R.DTWDistance();
		double distance1 = (distance1L + distance1R) *0.5;
		double distance2 = (distance2L + distance2R) *0.5;	
		System.out.println("D1 : " + distance1);
		System.out.println("D2 : " + distance2);
	}
	
	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListeninf() {
		kinectModule.unsetListener(this);		
	}
}

