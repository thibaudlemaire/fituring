package classification;

import java.io.Serializable;

public class Point implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float x;
	private float y;
	private float z;
	
	public Point(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public final float getX() {
		return x;
	}

	public final float getY() {
		return y;
	}

	public final float getZ() {
		return z;
	}
	
	public float distanceTo(Point otherPoint)
	{
		return (float) Math.sqrt( ( x - otherPoint.getX() ) * ( x - otherPoint.getX() )
								+ ( y - otherPoint.getY() ) * ( y - otherPoint.getY() ) 
								+ ( z - otherPoint.getZ() ) * ( z - otherPoint.getZ() ));
	}

}
