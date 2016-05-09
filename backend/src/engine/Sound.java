package engine;

public class Sound 
{

	private String path;
	private Attributes attributes;

	public Sound(String path, int[] brutAttributes)
	{
		this.path = path;
		this.attributes = new Attributes(brutAttributes);
	}
	
	public String getPath()
	{
		return path;
	}
}
