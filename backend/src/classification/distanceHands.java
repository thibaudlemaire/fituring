package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.LectureAudioSimpleInterface;
import interfaces.LectureInterface;

public class distanceHands implements ClassificationInterface, KinectListenerInterface {
	
	KinectInterface kinectModule;
	Object BDD;
	LectureAudioSimpleInterface audio;
	static float trigger = (float) 0.2;
	static float limitUp = (float) 1.40;
	static float limitDown = (float) 0.2;
	
	//Booleans needed to make an hysteresis when some movements are noticed
	boolean limitUpExceeded = false;
	boolean limitDownExceeded = false;
	boolean leftHandAboveHead = false;
	boolean rightHandAboveHead = false;
	
	
	
	
	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule, LectureInterface audio) {
		// TODO Auto-generated method stub
		this.kinectModule = kinectModule;
		this.BDD = BDD;
		this.audio = (LectureAudioSimpleInterface) audio;
	}


	@Override
	public void skeletonReceived(KinectEventInterface e) {
		Skeleton newSkeleton = e.getNewSkeleton();
		float headCoordinatesY = newSkeleton.get3DJointY(Skeleton.HEAD);
		float shoulderLeftCoordinatesY = newSkeleton.get3DJointY(Skeleton.SHOULDER_LEFT);
		float shoulderRightCoordinatesY = newSkeleton.get3DJointY(Skeleton.SHOULDER_RIGHT);
		float shouldersAverageCoordinatesY = (shoulderLeftCoordinatesY + shoulderRightCoordinatesY)/2;
		
		float handLeftCoordinatesX = newSkeleton.get3DJointX(Skeleton.HAND_LEFT);
		float handRightCoordinatesX = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT);
		float handLeftCoordinatesY = newSkeleton.get3DJointY(Skeleton.HAND_LEFT);
		float handRightCoordinatesY = newSkeleton.get3DJointY(Skeleton.HAND_RIGHT);
		float handLeftCoordinatesZ = newSkeleton.get3DJointZ(Skeleton.HAND_LEFT);
		float handRightCoordinatesZ = newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT);
		
		
		//Determinating the distance between the two hands
		float distance = (float) Math.sqrt((handRightCoordinatesX - handLeftCoordinatesX)*(handRightCoordinatesX - handLeftCoordinatesX) + (handRightCoordinatesY - handLeftCoordinatesY)*(handRightCoordinatesY - handLeftCoordinatesY) + (handRightCoordinatesZ - handLeftCoordinatesZ)*(handRightCoordinatesZ - handLeftCoordinatesZ));
		//System.out.println(distance);
		
		//Noticing when arms are extended
		if (distance > limitUp && limitUpExceeded == false && handLeftCoordinatesY < (shouldersAverageCoordinatesY + trigger) && handRightCoordinatesY < (shouldersAverageCoordinatesY + trigger) && handLeftCoordinatesY > (shouldersAverageCoordinatesY - trigger) && handRightCoordinatesY > (shouldersAverageCoordinatesY - trigger)) {
			limitUpExceeded = true;
			audio.playSound("sounds/cymbal.wav");
			System.out.println("Cymbale !");
		}
		
		if (limitUpExceeded == true && distance < (limitUp - trigger)) {
			limitUpExceeded = false;
		}
		
		//Noticing when a clap is done
		if (distance < limitDown && limitDownExceeded == false) {
			limitDownExceeded = true;
			audio.playSound("sounds/clap.wav");
			System.out.println("Clap !");
		}
		
		if (limitDownExceeded == true && distance > (limitDown + trigger)) {
			limitDownExceeded = false;
		}
		
		//Noticing when the left hand is risen
		if (handLeftCoordinatesY > headCoordinatesY && leftHandAboveHead == false) {
			leftHandAboveHead = true;
			audio.playSound("sounds/snare.wav");
			System.out.println("Snare !");
		}
		
		if (leftHandAboveHead == true && handLeftCoordinatesY < (headCoordinatesY - trigger)) {
			leftHandAboveHead = false;
		}
		
		//Noticing when the right hand is risen
		if (handRightCoordinatesY > headCoordinatesY && rightHandAboveHead == false) {
			rightHandAboveHead = true;
			audio.playSound("sounds/basse.wav");
			System.out.println("Basse !");

		}
		
		if (rightHandAboveHead == true && handRightCoordinatesY < (headCoordinatesY - trigger)) {
			rightHandAboveHead = false;
		}
	}

	
	@Override
	public void startListening() {
		// TODO Auto-generated method stub
		kinectModule.setListener(this);
	}

	@Override
	public void stopListening() {
		// TODO Auto-generated method stub
		kinectModule.unsetListener(this);
	}

}
