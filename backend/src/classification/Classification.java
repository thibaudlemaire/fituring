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
	
	private float[][] firstMoveLeft ;
	private float[][] secondMoveLeft ;
	private float[][] firstMoveRight ;
	private float[][] secondMoveRight ;
	
	private DatasFIFO datasFIFOLeft ;   
	private DatasFIFO datasFIFORight; 
	
	private int size1;
	private int size2;
	
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
		
		MovementSerializer moveSerial = new MovementSerializer();
		Move mvt1=moveSerial.deSerialize("datas/m1.mvt");
		Move mvt2=moveSerial.deSerialize("datas/m2.mvt");
		ArrayList<Step> steps1 = mvt1.steps;
		ArrayList<Step> steps2 = mvt2.steps;
		size1 = steps1.size();
		size2 =steps2.size();
		
		firstMoveLeft = new float[size1][3];
		firstMoveRight = new float[size1][3];
		secondMoveLeft = new float[size2][3];
		secondMoveRight = new float[size2][3];	
		
		datasFIFOLeft=new DatasFIFO(max(size1, size2));
		datasFIFORight=new DatasFIFO(max(size1, size2));
		
		
		for (int i =0; i<size1; i++) 
		{
			firstMoveLeft[i]=substract(steps1.get(i).getCoordinates().get(Skeleton.HAND_LEFT),steps1.get(i).getCoordinates().get(Skeleton.SPINE_MID));
			firstMoveRight[i]=substract(steps1.get(i).getCoordinates().get(Skeleton.HAND_RIGHT),steps1.get(i).getCoordinates().get(Skeleton.SPINE_MID));
		}	
		
		for (int i =0; i<size2; i++) 
		{
			secondMoveLeft[i]=substract(steps2.get(i).getCoordinates().get(Skeleton.HAND_LEFT),steps2.get(i).getCoordinates().get(Skeleton.SPINE_MID));
			secondMoveRight[i]=substract(steps2.get(i).getCoordinates().get(Skeleton.HAND_RIGHT),steps2.get(i).getCoordinates().get(Skeleton.SPINE_MID));
		}	
		
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

	private float[] substract(float[] F1, float[] F2) {
		// TODO Auto-generated method stub
		float[] result=new float [F1.length];
		if (F1.length != F2.length){
			System.out.println("Erreur");
			return null;
		}
		else {
			for (int i =0; i< F1.length;i++){
				result[i]=F1[i]-F2[i];
			}
		}
		return result;
	}

	private int max(int a, int b) {
		// TODO Auto-generated method stub
		if (a < b)
			return b ;
		return a;
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
		
		PointR newPoint = new PointR(handRightCoordinates[0], handRightCoordinates[1]);
		points.add(newPoint);
		if (points.size() > LIMIT_VECTOR_points) {
			PointR removedPoint = points.remove(0);
			I=I-ndollar.Utils.Distance(removedPoint, points.elementAt(0))/NumResamplePoints ;
		}
		
		if (points.size() > 1){
			PointR pt1 = points.elementAt(points.size()-2);
			PointR pt2 = points.elementAt(points.size()-1);
			double d = ndollar.Utils.Distance(pt1, pt2);
			I=I+d/NumResamplePoints;
			
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
			
		}
		
		if (samplePoints.size() > LIMIT_VECTOR_points) {
			samplePoints.remove(0);
		}	
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

