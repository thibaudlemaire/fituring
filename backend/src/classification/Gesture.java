package classification;

import java.io.Serializable;
import java.util.Vector;

public class Gesture implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int jointID;
	private Vector<Point> points = new Vector<Point>();
	
	public Gesture(int jointID)
	{
		this.jointID = jointID;
	}

	public void addPoint(Point point)
	{
		points.add(point);
	}
	
	public Point getPoint(int i)
	{
		return points.get(i);
	}
	
	public void removeFirstPoint()
	{
		points.remove(0);
	}
	
	public int size()
	{
		return points.size();
	}
	
	public int getJointID()
	{
		return jointID;
	}
	
	public Vector<Point> getPoints()
	{
		return points;
	}
	
	public void clear()
	{
		points.clear();
	}
}
