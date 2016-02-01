package kinect;

import edu.ufl.digitalworlds.j4k.J4KSDK;

public class KinectRecorder extends J4KSDK{

	KinectFileWriter out;
	VideoData last_video_frame;
	boolean stop_recording=false;
	int frame_counter=0;
	XEDConvertApp app;
	
	public KinectRecorder(XEDConvertApp app)
	{
		super();
		this.app=app;
	}
	
	public void startRecording(String filename)
	{
		stop_recording=false;
		out=new KinectFileWriter(filename);
		out.openTempFile();
	}
	
	public void stopRecording()
	{
		stop_recording=true;
		while(isProcessingNewFrame(false,false)){try {Thread.sleep(10);} catch (InterruptedException e) {}}
		
		out.closeTempFile();
		FileCompressor fc=new FileCompressor(out);
		fc.addProgressListener(app);
		fc.start("Kinect Compressor");
		out=null;
		stop_recording=false;
	}
	
	private boolean is_processing=false;
	public synchronized boolean isProcessingNewFrame(boolean write, boolean value)
	{
		if(write)is_processing=value;
		return is_processing;
	}
	
	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] player_index, float[] XYZ, float[] UV) {
		long time=System.currentTimeMillis();
		if(out!=null)
		{
			if(!stop_recording)
			{			
				frame_counter+=1;
				app.fps.setText(""+frame_counter);
				VideoData video=last_video_frame;
				if(video!=null)
				{
					isProcessingNewFrame(true,true);
					out.depth_width=getDepthWidth();
					out.depth_height=getDepthHeight();
					out.video_width=getColorWidth();
					out.video_height=getColorHeight();
					out.device_type=getDeviceType();
					out.writeDepthFrameTemp(depth_frame,getAccelerometerReading(),time,video.timestamp);
					out.writeVideoFrameTemp(video.data);
					isProcessingNewFrame(true,false);
				}
			}
		}
		
	}

	@Override
	public void onSkeletonFrameEvent(boolean[] flags, float[] positions, float[] orientations, byte[] joint_status) {}
	
	@Override
	public void onColorFrameEvent(byte[] data) {
			last_video_frame=new VideoData(data);
		}
}
