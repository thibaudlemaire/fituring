package classification;

import interfaces.ClassificationInterface;
import interfaces.KinectEvent;
import interfaces.KinectInterface;
import interfaces.KinectListener;

public class Classification implements ClassificationInterface, KinectListener {

	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		kinectModule.setListener(this);
		
	}

	@Override
	public void skeletonReceived(KinectEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	

}
