package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class distanceHands implements ClassificationInterface, KinectListenerInterface {
	
	KinectInterface kinectModule;
	Object BDD;
	
	
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
		float handLeftCoordinates = newSkeleton.get3DJointX(Skeleton.HAND_LEFT);
		float handRightCoordinates = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT);
		float distance = handRightCoordinates - handLeftCoordinates;
		System.out.println(distance);
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
