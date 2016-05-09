package engine;

public class Sound 
{

	private String path;
	private int range;
	private int electric;
	private int sweet; 
	private int disco;
	private int explosive;
	private int dance;
	private int rock;
	

	public Sound(String path, int range,
			 int electric, 
			 int sweet, 
			 int disco, 
			 int explosive,
			 int dance,
			 int rock)
	{
		this.path = path;
		this.range = range;
		this.electric = electric;
		this.sweet = sweet;
		this.disco = disco;
		this.explosive = explosive;
		this.dance = dance;
		this.rock = rock;
	}
}
