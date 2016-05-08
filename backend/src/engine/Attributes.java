package engine;

public class Attributes 
{
	public static final int RANGE = 0;
	public static final int ROBOTIC = 1;
	public static final int SWEET = 2;
	public static final int DISCO = 3;
	public static final int EXPLOSIVE = 4;
	public static final int NUMBER_OF_ATTRIBUTES = 5;
	
	private int[] attributes = new int[5];
	
	/**
	 * Attributes constructor
	 * Order : 
	 * { Range , Robotic , Sweet , Disco , Explosive }
	 * @param attributes
	 */
	public Attributes(int[] BrutAttributes)
	{
		if (BrutAttributes.length == NUMBER_OF_ATTRIBUTES)
		{
			System.err.println("Wrong number of attributes");
			return;
		}
		
		this.attributes = BrutAttributes;
	}
	
	public void setAtribute(int attributeNumber, int value)
	{
		this.attributes[attributeNumber] = value;
	}
}
