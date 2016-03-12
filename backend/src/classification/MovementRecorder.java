package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class MovementRecorder implements KinectListenerInterface {
	
	private Move mvt;
	
	public MovementRecorder(KinectInterface kinect)
	{
		kinect.setListener(this);
		mvt = new Move();
	}

	@Override
	public void skeletonReceived(KinectEventInterface e) {
		Step step = new Step();
		step.setTime(e.getSkeletonTime());
		for(int i = 0; i<Skeleton.JOINT_COUNT; i++)
		{
			step.setJoint(i, e.getNewSkeleton().get3DJointX(i),
							e.getNewSkeleton().get3DJointY(i), 
							e.getNewSkeleton().get3DJointZ(i));
		}
		mvt.steps.add(step);		
	}
	
	public static void main(String[] args)
	{
		
	}
}
