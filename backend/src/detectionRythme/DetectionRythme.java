package detectionRythme;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.LectureAudioSimpleInterface;
import interfaces.RyhtmeInterface;

public class DetectionRythme implements RyhtmeInterface, KinectListenerInterface{

	private TableauCoordoneeTemps tab;
	private double bpm;
	
	public void skeletonReceived(KinectEventInterface e) {
		// TODO Auto-generated method stub
		double newcoordinate = (double) e.getNewSkeleton().get3DJointX(Skeleton.WRIST_RIGHT);
		double newtime = (double) e.getSkeletonTime();
		double[] newcoordinateandtime = {newcoordinate,newtime};
		tab.addData(newcoordinateandtime);
		if(tab.getLen()>398){
			double [] zero = new double[400];
			double[] tdf = TableauCoordoneeTemps.fft(tab.getCoordinateTable(),zero,true);
			int indicemax = 12;
			for (int i = 12; i < 40; i++) {
			if (tdf[i] > tdf[indicemax])
			{
			indicemax = i;
			bpm = 4.95*indicemax;
			}	
			}
			System.out.println("le bpm est"+bpm);
	}
	}

	public void initRythmeModule() {
		// TODO Auto-generated method stub
	tab = new TableauCoordoneeTemps();

	}

	public int getCurrentTrueBPM() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getCurrentUsedBPM() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void initRythmeModule(KinectInterface kinect, LectureAudioSimpleInterface audio) {
		// TODO Auto-generated method stub
		
	}

}
