package classification;

import java.util.ArrayList;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.LectureAudioSimpleInterface;
import interfaces.LectureInterface;

public class Classification implements ClassificationInterface, KinectListenerInterface {
	
	Object BDD ;
	KinectInterface kinectModule;
	LectureAudioSimpleInterface audio;
	
	private float[][] firstMoveLeft ;
	private float[][] secondMoveLeft ;
	private float[][] firstMoveRight ;
	private float[][] secondMoveRight ;
	
	private DatasFIFO datasFIFOLeft ;   
	private DatasFIFO datasFIFORight; 
	
	private int size1;
	private int size2;
	
	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule, LectureInterface audio) {
		// TODO Auto-generated method stub
		this.kinectModule = kinectModule;
		this.BDD = BDD ;
		this.audio = (LectureAudioSimpleInterface) audio;
		
		MovementSerializer moveSerial = new MovementSerializer();
		Move mvt1=moveSerial.deSerialize("datas/m1.mvt");
		Move mvt2=moveSerial.deSerialize("datas/m2.mvt");
		ArrayList<Step> steps1 = mvt1.steps;
		ArrayList<Step> steps2 = mvt2.steps;
		size1 = steps1.size();
		size2 =steps2.size();
		
		firstMoveLeft = new float[size1][3];
		firstMoveRight = new float[size1][3];
		secondMoveLeft = new float[size2][3];
		secondMoveRight = new float[size2][3];	
		
		datasFIFOLeft=new DatasFIFO(max(size1, size2));
		datasFIFORight=new DatasFIFO(max(size1, size2));
		
		
		for (int i =0; i<size1; i++) 
		{
			firstMoveLeft[i]=substract(steps1.get(i).getCoordinates().get(Skeleton.HAND_LEFT),steps1.get(i).getCoordinates().get(Skeleton.SPINE_MID));
			firstMoveRight[i]=substract(steps1.get(i).getCoordinates().get(Skeleton.HAND_RIGHT),steps1.get(i).getCoordinates().get(Skeleton.SPINE_MID));
		}	
		
		for (int i =0; i<size2; i++) 
		{
			secondMoveLeft[i]=substract(steps2.get(i).getCoordinates().get(Skeleton.HAND_LEFT),steps2.get(i).getCoordinates().get(Skeleton.SPINE_MID));
			secondMoveRight[i]=substract(steps2.get(i).getCoordinates().get(Skeleton.HAND_RIGHT),steps2.get(i).getCoordinates().get(Skeleton.SPINE_MID));
		}	
	}

	private float[] substract(float[] F1, float[] F2) {
		// TODO Auto-generated method stub
		float[] result=new float [F1.length];
		if (F1.length != F2.length){
			System.out.println("Erreur");
			return null;
		}
		else {
			for (int i =0; i< F1.length;i++){
				result[i]=F1[i]-F2[i];
			}
		}
		return result;
	}

	private int max(int a, int b) {
		// TODO Auto-generated method stub
		if (a < b)
			return b ;
		return a;
	}

	public void skeletonReceived(KinectEventInterface e){  //automatically called when a new skeleton is captured by the kinect
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		float baseX = newSkeleton.get3DJointX(Skeleton.SPINE_MID);
		float baseY = newSkeleton.get3DJointY(Skeleton.SPINE_MID);
		float baseZ = newSkeleton.get3DJointZ(Skeleton.SPINE_MID);
		float[] handLeftCoordinates = new float[3];
		handLeftCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_LEFT)-baseX;
		handLeftCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_LEFT)-baseY;
		handLeftCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_LEFT)-baseZ;
		float[] handRightCoordinates = new float[3];
		handRightCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT)-baseX;
		handRightCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_RIGHT)-baseY;
		handRightCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT)-baseZ;
		datasFIFOLeft.addData(handLeftCoordinates);
		datasFIFORight.addData(handRightCoordinates);
		DTW dtw1L = new DTW(firstMoveLeft, datasFIFOLeft.getFIFOTab(size1));
		DTW dtw1R = new DTW(firstMoveRight, datasFIFOLeft.getFIFOTab(size1));
		DTW dtw2L = new DTW(secondMoveLeft, datasFIFOLeft.getFIFOTab(size2));
		DTW dtw2R = new DTW(secondMoveRight, datasFIFOLeft.getFIFOTab(size2));
		double distance1L = dtw1L.DTWDistance();
		double distance1R = dtw1R.DTWDistance();
		double distance2L = dtw2L.DTWDistance();
		double distance2R = dtw2R.DTWDistance();
		double distance1 = (distance1L + distance1R) *0.5;
		double distance2 = (distance2L + distance2R) *0.5;	
		System.out.print("D1 : " + distance1 + "   -   D2 : " + distance2 + "\r");
	}
	
	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListening() {
		kinectModule.unsetListener(this);		
	}

}

