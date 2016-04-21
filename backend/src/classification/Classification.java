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
	
	private int LIMIT_VECTOR_points = 50 ; //can be modified 
	
	Vector<PointR> points = new Vector<PointR>();
	Vector<PointR> samplePoints = new Vector<PointR>();
	Vector<Vector<PointR>> strokes = new Vector<Vector<PointR>>();
	static NDollarRecognizer _rec = new NDollarRecognizer();
	
	public int NumResamplePoints = 64;
	private double D=0;
	private double I;
	
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
		System.out.println("New Skeleton !");
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
		
		PointR newPoint = new PointR(handRightCoordinates[0], handRightCoordinates[1]);
		points.add(newPoint);
		
		// traitement cyclique des points et ré-échantillonage spatial en réutilisant
		// l'algorithme présent dans ndollar/Utils
		
		if (points.size() > LIMIT_VECTOR_points) {
			PointR removedPoint = points.remove(0);
			I=I-ndollar.Utils.Distance(removedPoint, points.elementAt(0))/(NumResamplePoints-1) ;
		}
		
		if (samplePoints.size()==0)
			samplePoints.add(points.elementAt(0));
		
		else 
			samplePoints.set(0, points.elementAt(0));
		
		if (points.size() > 1){			
			
			// should be used only once, at the second skeleton detected
			if (samplePoints.size() < NumResamplePoints) {
				
				for (int i = 1; i < points.size(); i++) {
					PointR pt1 = (PointR) points.elementAt(i - 1);
					PointR pt2 = (PointR) points.elementAt(i);

					double d = ndollar.Utils.Distance(pt1, pt2);
					if ((D + d) >= I) {
						double qx = pt1.X + ((I - D) / d) * (pt2.X - pt1.X);
						double qy = pt1.Y + ((I - D) / d) * (pt2.Y - pt1.Y);
						PointR q = new PointR(qx, qy);
						samplePoints.add(q); // append new point 'q'
						points.insertElementAt(q, i); // insert 'q' at position i in
														// points s.t.
						// 'q' will be the next i
						D = 0.0;
					} else {
						D += d;
					}
				}	
			}
			
			// "sometimes we fall a rounding-error short of adding the last point, so add it if so"
			// should be used once
			if (samplePoints.size() < NumResamplePoints){
				samplePoints.add(points.elementAt(points.size()-1));
			}
			
			// should be used at every skeleton received
			PointR pt1 = points.elementAt(points.size()-2);
			PointR pt2 = points.elementAt(points.size()-1);
			double d = ndollar.Utils.Distance(pt1, pt2);
			I=I+d/(NumResamplePoints-1);
			
			if ((D + d) >= I) {
				double qx = pt1.X + ((I - D) / d) * (pt2.X - pt1.X);
				double qy = pt1.Y + ((I - D) / d) * (pt2.Y - pt1.Y);
				PointR q = new PointR(qx, qy);
				D = 0.0;
				samplePoints.add(q); // append new point 'q'
				points.insertElementAt(q, points.size()-1); // insert 'q' at position i in
												// points s.t.
			}
			else {
				D += d;
			}
			
			if(samplePoints.size() > NumResamplePoints) {
				samplePoints.remove(1); 
				//cause samplePoints.elementAt(0) must stay and match points.elementAt(0)
			}	
		}
		
		this.nDollarRegognizer();
		
	}
	
	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListening() {
		strokes.clear();
		if (samplePoints.size() > 1) {
			strokes.add(new Vector<PointR>(samplePoints));
		}
		kinectModule.unsetListener(this);		
	}
	
	

	//Renvoie le nom du geste reconnu
	public String nDollarRegognizer() {
		if (samplePoints.size() > 0) {
			Vector<PointR> allPoints = new Vector<PointR>();
			Enumeration<PointR> en = samplePoints.elements();
			while (en.hasMoreElements()) {
				PointR pts = en.nextElement();
				allPoints.add(pts);
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

