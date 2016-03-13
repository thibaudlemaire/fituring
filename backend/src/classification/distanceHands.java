package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class distanceHands implements ClassificationInterface, KinectListenerInterface {
	
	KinectInterface kinectModule;
	Object BDD;
	static float limitUp = (float) 1.30;
	static float limitDown = (float) 0.2;
	
	
	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		this.kinectModule = kinectModule;
		this.BDD = BDD ;
	}


	@Override
	public void skeletonReceived(KinectEventInterface e) {
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		float handLeftCoordinatesX = newSkeleton.get3DJointX(Skeleton.HAND_LEFT);
		float handRightCoordinatesX = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT);
		float handLeftCoordinatesY = newSkeleton.get3DJointY(Skeleton.HAND_LEFT);
		float handRightCoordinatesY = newSkeleton.get3DJointY(Skeleton.HAND_RIGHT);
		float handLeftCoordinatesZ = newSkeleton.get3DJointZ(Skeleton.HAND_LEFT);
		float handRightCoordinatesZ = newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT);
		float distance = (float) Math.sqrt((handRightCoordinatesX - handLeftCoordinatesX)*(handRightCoordinatesX - handLeftCoordinatesX) + (handRightCoordinatesY - handLeftCoordinatesY)*(handRightCoordinatesY - handLeftCoordinatesY) + (handRightCoordinatesZ - handLeftCoordinatesZ)*(handRightCoordinatesZ - handLeftCoordinatesZ));
		System.out.println(distance);
		
		if (distance > limitUp) {
			SyntheseAudio.armsExtended();
		}
		
		if (distance < limitDown) {
			SyntheseAudio.clap();
		}
	}

	
	@Override
	public void startListening() {
		// TODO Auto-generated method stub
		kinectModule.setListener(this);
	}

	@Override
	public void stopListeninf() {
		// TODO Auto-generated method stub
		kinectModule.unsetListener(this);
	}

}
