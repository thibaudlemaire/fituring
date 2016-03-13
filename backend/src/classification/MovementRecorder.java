package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;

public class MovementRecorder implements KinectListenerInterface {
	
	private Move mvt;
	private KinectInterface kinect;
	
	public MovementRecorder(KinectInterface kinect)
	{
		this.kinect = kinect;
		mvt = new Move();
	}

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
		System.out.print("#");
	}
	
	public void startListener()
	{
		kinect.setListener(this);
	}
	
	public void stopListener()
	{
		kinect.unsetListener(this);
	}
	
	public Move getMovement()
	{
		return mvt;
	}
}
