package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEvent;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class Classification implements ClassificationInterface, KinectListenerInterface {
	
	Object BDD ;

	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		kinectModule.setListener(this);
		this.BDD = BDD ;	
	}
		

	public void skeletonReceived(KinectEvent e) {
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		double[] handLeftCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_LEFT);
		double[] handRightCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_RIGHT);
		DatasFIFO datasFIFOLeft = new DatasFIFO();
		DatasFIFO datasFIFORight = new DatasFIFO();
		datasFIFOLeft.addData(handLeftCoordinates);
		datasFIFORight.addData(handRightCoordinates);
	}

	

	@Override
	public void skeletonReceived(KinectEventInterface e) {
		// TODO Auto-generated method stub
		
	}
}
