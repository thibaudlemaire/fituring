package engine;

public class Sound 
{

	private final String path;
	private Attributes attributes;
	private final boolean loopable;

	public Sound(String path, boolean loopable, int[] brutAttributes)
	{
		this.path = path;
		this.attributes = new Attributes(brutAttributes);
		this.loopable = loopable;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public Attributes getAttributes()
	{
		return attributes;
	}
	
	public boolean isLoopable()
	{
		return loopable;
	}
}
