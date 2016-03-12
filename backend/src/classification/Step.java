package classification;

import java.io.Serializable;
import java.util.ArrayList;

public class Step implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7057741325158870422L;

	private long time;
	private ArrayList<float[]> coordinates = new ArrayList<float[]>();
	
	public Step()
	{
	}
	
	public void setJoint(int joint, float x, float y, float z)
	{
		float[] c = new float[3];
		c[0] = x;
		c[1] = y;
		c[2] = z;
		coordinates.add(c);
	}
	
	public void setTime(long time)
	{
		this.time = time;
	}

	public ArrayList<float[]> getCoordinates() {
		return coordinates;
	}

	public long getTime() {
		return time;
	}
	
}
