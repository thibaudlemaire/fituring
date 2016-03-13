package events;

import java.util.Date;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;

public class KinectEvent implements KinectEventInterface {

	private Skeleton skeleton;
	private long time;
	
	KinectEvent(Skeleton skeleton)
	{
		this.skeleton = skeleton;
		this.time = new Date().getTime();
	}
	@Override
	public Skeleton getNewSkeleton() {
		return skeleton;
	}

	@Override
	public long getSkeletonTime() {
		// TODO Auto-generated method stub
		return time;
	}

		
}
