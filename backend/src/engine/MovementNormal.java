package engine;

public class MovementNormal extends Movement
{
	private Attributes attributes;
	
	public MovementNormal(String path, Attributes attributes)
	{
		super(path);
		this.attributes = attributes;
	}
	
	/**
	 * Order : 
	 * { Range, Robotic, Sweet, Disco, Explosive, Hiphop, Dance, Rock }
	 * @param path
	 */
	public MovementNormal(String path, int[] BrutAttributes)
	{
		super(path);
		this.attributes = new Attributes(BrutAttributes);
	}
	
	public Attributes getAttributes()
	{
		return attributes;
	}
	
}