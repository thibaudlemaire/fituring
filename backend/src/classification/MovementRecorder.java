package classification;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import kinect.Kinect;

public class MovementRecorder implements KinectListenerInterface {
	
	private Move mvt;
	
	public MovementRecorder(KinectInterface kinect)
	{
		kinect.setListener(this);
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
	}
	
	public Move getMovement()
	{
		return mvt;
	}
	
	public static void main(String[] args)
	{
		System.out.println("Debut de l'enregistrement");
		MovementSerializer ms = new MovementSerializer();
		Kinect kinect=new Kinect();
		MovementRecorder mr = new MovementRecorder(kinect);
		
		kinect.initKinectModule();

		try {Thread.sleep(10000);} catch (InterruptedException e) {}
		
		kinect.stop();	
		ms.serialize(mr.getMovement(), "datas/m1.mvt");
		
		System.out.println("FPS: "+kinect.getFPS());
	}
}
