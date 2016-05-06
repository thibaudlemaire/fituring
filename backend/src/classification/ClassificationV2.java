package classification;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.MovementFoundInterface;
import ndollarV2.*;

public class ClassificationV2 implements ClassificationInterface, KinectListenerInterface {

	KinectInterface kinectModule;

	Vector<PointR> points = new Vector<PointR>();
	Vector<Vector<PointR>> strokes = new Vector<Vector<PointR>>();
	
	Vector<Vector<PointR>> comparedTo = new Vector<Vector<PointR>>();


	static NDollarRecognizerV2 _rec = new NDollarRecognizerV2();
	private Hashtable<String, Multistroke> _gestures;
	private Hashtable<String, Integer> _gesturesLength = new Hashtable<String, Integer>();

	int numberOfSkeletonReceived = 0; //Counts how many skeletons have been received
	Skeleton currentSkeleton = new Skeleton();
	///////Options :
	static int resetSkeletonNumber = 10; //Adds coordinates in the file every resetSkeletonNumber skeleton received
	int fifoLimit; //size of the fifo
	int minGesturesPoints; //minimum size of gestures database points
	
	double confidenceValue = 0.85;
	static float resamplingDistance = (float) 0.05; //size of resampling

	//Used in resampling :
	boolean firstSkeletonReceived = true;

	//Renvoie les paramètres ci-dessus (pour la classe AddGesture)
	public static Object[] getParameters() {
		Object[] result = new Object[2];
		result[0] = resetSkeletonNumber;
		result[1] = resamplingDistance;
		return result;
	}

	@Override
	public void initClassificationModule(KinectInterface kinectModule) {
		// TODO Auto-generated method stub
		this.kinectModule = kinectModule;

		String samplesDir = NDollarParameters.getInstance().SamplesDirectory;
		File currentDir = new File(samplesDir);
		File[] allXMLFiles = currentDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});

		// read them and determine the maximum length of a distance
		for (int i = 0; i < allXMLFiles.length; ++i) {
			_rec.LoadGesture(allXMLFiles[i]);
		}

		_gestures = _rec.get_Gestures();


		int max = -1;
		int min = Integer.MAX_VALUE;
		for (Enumeration<String> e = _gestures.keys() ; e.hasMoreElements() ; ) { 
			String key = e.nextElement();
			int value = _gestures.get(key).getOriginalGesture().getPoints().size();
			_gesturesLength.put(key, value);
			comparedTo.set(_gesturesLength.get(key), null);

			//Determination de la longueur maximale pour la file
			if (value > max) {
				max = value;
			}
			
			if (value < min) {
				min = value;
			}
		}
		fifoLimit = max;
		minGesturesPoints = min;

	}

	public void skeletonReceived(KinectEventInterface e){  //automatically called when a new skeleton is captured by the kinect
		// TODO Auto-generated method stub
		numberOfSkeletonReceived++;
		Skeleton newSkeleton = e.getNewSkeleton();
		float baseX = newSkeleton.get3DJointX(Skeleton.SPINE_MID);
		float baseY = newSkeleton.get3DJointY(Skeleton.SPINE_MID);
		float baseZ = newSkeleton.get3DJointZ(Skeleton.SPINE_MID);
		float[] handRightCoordinates = new float[3];
		handRightCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_RIGHT)-baseX;
		handRightCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_RIGHT)-baseY;
		handRightCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_RIGHT)-baseZ;


		//Echantillonage spatial

		if (firstSkeletonReceived) {
			points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			firstSkeletonReceived = false;
			currentSkeleton = newSkeleton;
			return ;
		}

		//Plus rapide. A supprimer a la version finale quand on aura plein de points du squelette
		float[] handRightCoordinatestmp = new float[3];
		handRightCoordinatestmp[0] = currentSkeleton.get3DJointX(Skeleton.HAND_RIGHT)-baseX;
		handRightCoordinatestmp[1] = currentSkeleton.get3DJointY(Skeleton.HAND_RIGHT)-baseY;
		handRightCoordinatestmp[2] = currentSkeleton.get3DJointZ(Skeleton.HAND_RIGHT)-baseZ;

		//Ajout dans la file au moins toutes les 333ms
		if (numberOfSkeletonReceived >= resetSkeletonNumber) {
			points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			numberOfSkeletonReceived = 0;
			currentSkeleton = newSkeleton;
			return ;
		}

		if (distance(handRightCoordinates, handRightCoordinatestmp) < resamplingDistance) {
			return ;
		}

		//Si on arrive ici, 10 squelettes n'ont pas été recus depuis le dernier enregistrement dans la file et il n'y a pas eu de déplacement inférieur à resamplingDistance
		numberOfSkeletonReceived = 0;
		points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
		currentSkeleton = newSkeleton;

		//Comparaisons
		if (points.size() > minGesturesPoints) {
			for (Enumeration<String> element = _gestures.keys() ; element.hasMoreElements() ; ) { 
				String key = element.nextElement();
				if (_gesturesLength.get(key) <= points.size()) {
					Vector<PointR> pointsTmp = new Vector<PointR>();
					for (int i = points.size(); i>points.size() - _gesturesLength.get(key); i--) {
						pointsTmp.add(points.get(i));
					}
					comparedTo.set(_gesturesLength.get(key), pointsTmp);
					if (comparedTo.get(_gesturesLength.get(key)).size() > 1) {
						strokes.clear();
						strokes.add(comparedTo.get(_gesturesLength.get(key)));
					}
					Object[] result = nDollarRecognizer(strokes);
					if ((double) result[0] > confidenceValue) {
						System.out.println("Movement recognized : " + (String) result[1] + ", Score : " + (double) result[0]);
						points.clear();
						strokes.clear();
						comparedTo.clear();
						return ;
					}
				}
			}
		}
		
		//Gestion de la file
		if (points.size() >= fifoLimit) {
			points.remove(0);
		}
	}

	public void startListening() {
		kinectModule.setListener(this);
	}

	public void stopListening() {
		kinectModule.unsetListener(this);		
	}

	//Renvoie le nom du geste reconnu
	public Object[] nDollarRecognizer(Vector<Vector<PointR>> strokes) {
		Object[] resultReturn = new Object[2];
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
				resultReturn[0] = 0;
				resultReturn[1] = "No Match";
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

			resultReturn[0] = result.getScore();
			resultReturn[1] = result.getName();
		}
		else {
			resultReturn[0] = 0;
			resultReturn[1] = "Error";
		}

		return resultReturn;
	}

	public float distance(float[] coordinates1, float[] coordinates2) {
		float x = coordinates1[0] - coordinates2[0];
		float y = coordinates1[1] - coordinates2[1];
		return (float) Math.sqrt(x*x + y*y);
	}


	@Override
	public int addGesture(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfGestures() {
		// TODO Auto-generated method stub
		return 0;
	}

}

