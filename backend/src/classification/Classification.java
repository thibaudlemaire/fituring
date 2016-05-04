package classification;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.LectureAudioSimpleInterface;
import interfaces.LectureInterface;
import ndollar.*;

public class Classification implements ClassificationInterface, KinectListenerInterface {
	
	Object BDD ;
	KinectInterface kinectModule;
	LectureAudioSimpleInterface audio;
	
	Vector<PointR> points = new Vector<PointR>();
	Vector<Vector<PointR>> strokes = new Vector<Vector<PointR>>();
	static NDollarRecognizer _rec = new NDollarRecognizer();
	
	@Override
	public void initClassificationModule(Object BDD, KinectInterface kinectModule, LectureInterface audio) {
		// TODO Auto-generated method stub
		this.kinectModule = kinectModule;
		this.BDD = BDD ;
		this.audio = (LectureAudioSimpleInterface) audio;
		
		String samplesDir = NDollarParameters.getInstance().SamplesDirectory;
		File currentDir = new File(samplesDir);
		File[] allXMLFiles = currentDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});

		// read them
		for (int i = 0; i < allXMLFiles.length; ++i) {
			_rec.LoadGesture(allXMLFiles[i]);
		}
		
	}

	public void skeletonReceived(KinectEventInterface e){  //automatically called when a new skeleton is captured by the kinect
		// TODO Auto-generated method stub
		Skeleton newSkeleton = e.getNewSkeleton();
		float baseX = newSkeleton.get3DJointX(Skeleton.SPINE_MID);
		float baseY = newSkeleton.get3DJointY(Skeleton.SPINE_MID);
		float baseZ = newSkeleton.get3DJointZ(Skeleton.SPINE_MID);
		float[] handLeftCoordinates = new float[3];
		handLeftCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_LEFT)-baseX;
		handLeftCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_LEFT)-baseY;
		handLeftCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_LEFT)-baseZ;
		float[] handRightCoordinates = new float[3];
		handRightCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT)-baseX;
		handRightCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_RIGHT)-baseY;
		handRightCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT)-baseZ;
		
		points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
		
		
	}
	
	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListening() {
		strokes.clear();
		if (points.size() > 1) {
			strokes.add(new Vector<PointR>(points));
		}
		kinectModule.unsetListener(this);		
	}

	//Renvoie le nom du geste reconnu
	public String nDollarRegognizer() {
		if (strokes.size() > 0) {
			Vector<PointR> allPoints = new Vector<PointR>();
			Enumeration<Vector<PointR>> en = strokes.elements();
			while (en.hasMoreElements()) {
				Vector<PointR> pts = en.nextElement();
				allPoints.addAll(pts);
			}
			NBestList result = _rec.Recognize(allPoints, strokes.size());
			//String resultTxt;
			if (result.getScore() == -1) {
				//resultTxt = MessageFormat.format(
						//"No Match!\n[{0} out of {1} comparisons made]",
						//result.getActualComparisons(),
						//result.getTotalComparisons());
				return "No Match";
			} else {
				//resultTxt = MessageFormat
				//		.format("{0}: {1} ({2}px, {3}{4})  [{5,number,integer} out of {6,number,integer} comparisons made]",
				//				result.getName(),
				//				Utils.round(result.getScore(), 2),
				//				Utils.round(result.getDistance(), 2),
				//				Utils.round(result.getAngle(), 2),
				//				(char) 176, result.getActualComparisons(),
				//				result.getTotalComparisons());
			}
			points.clear();
			return result.getName();
		}
		else {
			return "Error";
		}
	}
}

