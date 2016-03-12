package classification;

import java.util.ArrayList;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEvent;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class Classification implements ClassificationInterface, KinectListenerInterface {
	
	Object BDD ;

	private double[][] firstMoveLeft = new double[30][3];
	private double[][] secondMoveLeft = new double[30][3];
	private double[][] firstMoveRight = new double[30][3];
	private double[][] secondMoveRight = new double[30][3];
	
	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		kinectModule.setListener(this);
		this.BDD = BDD ;	
		
		MovementSerializer moveSerial = new MovementSerializer();
		Move mvt1=moveSerial.deSerialize("datas/m2.mvt");
		Move mvt2=moveSerial.deSerialize("datas/m2.mvt");
		ArrayList<Step> steps1 = mvt1.steps;
		ArrayList<Step> steps2 = mvt2.steps;
		
		
		
		for (int i =0; i<30; i++) 
		{
			firstMoveLeft[i]=steps1.get(i).getCoordinates()[Skeleton.HAND_LEFT];
			firstMoveRight[i]=steps1.get(i).getCoordinates()[Skeleton.HAND_RIGHT];
			secondMoveLeft[i]=steps2.get(i).getCoordinates()[Skeleton.HAND_LEFT];
			secondMoveRight[i]=steps2.get(i).getCoordinates()[Skeleton.HAND_RIGHT];
		}
	}
	
	
		

	@Override
	public void skeletonReceived(KinectEventInterface e){
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		double[] handLeftCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_LEFT);
		double[] handRightCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_RIGHT);
		DatasFIFO datasFIFOLeft = new DatasFIFO();
		DatasFIFO datasFIFORight = new DatasFIFO();
		datasFIFOLeft.addData(handLeftCoordinates);
		datasFIFORight.addData(handRightCoordinates);
		DTW dtw1L = new DTW(firstMoveLeft, datasFIFOLeft.tab);
		DTW dtw1R = new DTW(firstMoveRight, datasFIFORight.tab);
		DTW dtw2L = new DTW(secondMoveLeft, datasFIFOLeft.tab);
		DTW dtw2R = new DTW(secondMoveRight, datasFIFORight.tab);
		double distance1L = dtw1L.DTWDistance();
		double distance1R = dtw1R.DTWDistance();
		double distance2L = dtw2L.DTWDistance();
		double distance2R = dtw2R.DTWDistance();
		double distance1 = (distance1L + distance1R) *0.5;
		double distance2 = (distance2L + distance2R) *0.5;	
		System.out.println(distance1);
		System.out.println(distance2);
	}
}

