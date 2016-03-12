package classification;

import java.io.Serializable;

public class Step implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7057741325158870422L;

	private long time;
	private long[][] coordinates;
	
	public Step()
	{
	}
	
	public void setJoint(int joint, long x, long y, long z)
	{
		coordinates[joint][0] = x;
		coordinates[joint][1] = y;
		coordinates[joint][2] = z;
	}
	
	public void setTime(long time)
	{
		this.time = time;
	}

	public long[][] getCoordinates() {
		return coordinates;
	}

	public long getTime() {
		return time;
	}
	
}
