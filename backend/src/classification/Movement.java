package classification;

import java.io.Serializable;
import java.util.Vector;

public class Movement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int movementID = -1;
	
	private Vector<Gesture> movement = new Vector<Gesture>();
	private String path = "";
	
	public Movement(String path)
	{
		this.path = path;
		this.movementID = movementID;
	}
	
	public void addGesture(Gesture gesture)
	{
		movement.add(gesture);
	}
	
	public String getPath()
	{
		return path;
	}
	
	public Vector<Gesture> getGestures()
	{
		return movement;
	}
	
	public int getMovementID() {
		return movementID;
	}
	
	public void setMovementID(int movementID) {
		this.movementID = movementID;
	}
}
