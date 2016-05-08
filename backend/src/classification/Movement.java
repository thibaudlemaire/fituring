package classification;

import java.io.Serializable;
import java.util.Vector;

public class Movement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Vector<Gesture> movement = new Vector<Gesture>();
	private String path = "";
	
	private double normalizationCoefficient;
	
	public Movement(String path, double normalizationCoefficient)
	{
		this.path = path;
		this.normalizationCoefficient = normalizationCoefficient;
	}
	
	public void addGesture(Gesture gesture)
	{
		movement.add(gesture);
	}
	
	public double getNormalizationCoefficient() {
		return normalizationCoefficient;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public Vector<Gesture> getGestures()
	{
		return movement;
	}
}
