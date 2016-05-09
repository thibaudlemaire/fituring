package engine;

public class Attributes 
{
	public static final int RANGE = 0;
	public static final int ELECTRIC = 1;
	public static final int ARABE = 2;
	public static final int DISCO = 3;
	public static final int EXPLOSIVE = 4;
	public static final int HIPHOP = 5;
	public static final int DANCE = 6;
	public static final int ROCK = 7;
	public static final int NUMBER_OF_ATTRIBUTES = 8;
	
	private int[] attributes = new int[5];
	
	/**
	 * Attributes constructor
	 * Order : 
	 * { Range, Robotic, Sweet, Disco, Explosive, Hiphop, Dance, Rock }
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
