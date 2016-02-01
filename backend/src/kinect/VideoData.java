package kinect;

public class VideoData {

	public long timestamp;
	public byte data[];
	
	public VideoData(byte data[])
	{
		timestamp=System.currentTimeMillis();
		this.data=data;
	}
	
}
