package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEvent;
import interfaces.KinectInterface;
import interfaces.KinectListener;

public class Classification implements ClassificationInterface, KinectListener {
	
	Object BDD ;

	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		kinectModule.setListener(this);
		this.BDD = BDD ;	
	}
		

	@Override
	public void skeletonReceived(KinectEvent e) {
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		double[] handLeftCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_LEFT);
		double[] handRightCoordinates = newSkeleton.get3DJoint(Skeleton.HAND_RIGHT);
			
	}

	

}
