package engine;

public abstract class Movement {
	
	private String path;
	
	public Movement(String path)
	{
		this.path = path;
	}
	
	public String getPath()
	{
		return path;
	}
}
