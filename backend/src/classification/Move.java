package classification;

import java.io.Serializable;
import java.util.ArrayList;

public class Move implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -169925548721174155L;

	public ArrayList<Step> steps;

	public Move()
	{
		steps = new ArrayList<Step>();
	}

}
