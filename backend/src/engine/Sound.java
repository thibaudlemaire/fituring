package engine;

public class Sound 
{

	private String path;
	private int range;
	private int robotic;
	private int sweet; 
	private int disco;
	private int explosive;
	

	public Sound(String path, int range,
			 int robotic, 
			 int sweet, 
			 int disco, 
			 int explosive)
	{
		this.path = path;
		this.range = range;
		this.robotic = robotic;
		this.sweet = sweet;
		this.disco = disco;
		this.explosive = explosive;
	}
}
