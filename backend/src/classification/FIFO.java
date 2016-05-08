package classification;

public class FIFO {

	private Gesture FIFO;
	private int jointID;
	private final int FIFOlenght;

	public FIFO(int FIFOlenght, int jointID)
	{
		this.FIFOlenght = FIFOlenght;
		this.jointID = jointID;
		FIFO = new Gesture(jointID);
	}
	
	public void addCapture(Point point)
	{
		
		FIFO.addPoint(point);
		
		if(FIFO.size() > FIFOlenght)
			FIFO.removeFirstPoint();
	}
	
	public Gesture getNlastPoints(int N)
	{
		Gesture gestureToReturn = new Gesture(jointID);
		for(int i = FIFO.size() - N; i < FIFO.size(); i++)
			gestureToReturn.addPoint(FIFO.getPoint(i));
		return gestureToReturn;
	}
	
	public Point getLastPoint()
	{
		return FIFO.getPoint(FIFO.size() - 1);
	}
	
	public void clear()
	{
		FIFO.clear();
	}
	
	public Gesture getAll()
	{
		return FIFO;
	}
	
	public int getSize()
	{
		return FIFO.size();
	}
}
