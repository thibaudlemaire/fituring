package engine;

public class MovementSpecial extends Movement
{
	private int soundID;
	public MovementSpecial(String path, int soundID)
	{
		super(path);
		this.soundID = soundID;
	}
	
	public final int getSoundID() 
	{
		return soundID;
	}

}
