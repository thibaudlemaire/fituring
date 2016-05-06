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
import ndollar.*;
/**
 * This class describes how gestures are recognized
 * @author robin
 *
 */
public class Classification implements ClassificationInterface, KinectListenerInterface {

	MovementFoundInterface engine ;
	KinectInterface kinectModule;

	Vector<PointR> points = new Vector<PointR>(); //points represents the FIFO
	Vector<Vector<PointR>> strokes = new Vector<Vector<PointR>>();
	
	Vector<Vector<PointR>> comparedTo = new Vector<Vector<PointR>>(); /**
																	  * Tableau de tableau. comparedTo contient à chaque fois les n derniers
	 																  * points de la file, avec n le nombre de points de chaque template.
																	  * Ainsi, la taille de comparedTo est le nombre de gestes dans la base de données.
																	  * Par exemple, si on a 2 mouvements N et Wave dans la database, comparedTo sera de
																	  * taille 2 et comparedTo[0] contiendra les n derniers points de la file avec n le
																	  * nombre de points dans N, et comparedTo[1] contiendra les m derniers points de la
																	  * file avec m le nombre de points dans Wave.
																	  */


	static NDollarRecognizer _rec = new NDollarRecognizer();
	
	private Hashtable<String, Multistroke> _gestures; // Database is loaded in _gestures with a Hashtable. Gestures are loaded under the Multistroke form
	private Hashtable<String, Integer> _gesturesLength = new Hashtable<String, Integer>(); //Obtained with _gestures. Associates the name of the gesture
																						   //with the number of points in the gesture (Integer)

	int numberOfSkeletonReceived = 0; //Used to record a skeleton every 333ms
	Skeleton currentSkeleton = new Skeleton(); //Used to rescale spacially
	///////Options :
	static int resetSkeletonNumber = 10; //Adds coordinates in the file every resetSkeletonNumber skeleton received
	int fifoLimit; //size of the fifo, equals to the maximum size of a gesture in the database
	int minGesturesPoints; //minimum size of gestures database points. Used to start comparitions
	double confidenceValue = 0.85; //Movement recognized if the probability of recognition is superior to confidenceValue
	static float resamplingDistance = (float) 0.05; //distance between two points in resampling
	boolean firstSkeletonReceived = true; //Used in resampling
	int numberOfGestures; //Contains the number of gestures in the database

	
	//Renvoie les paramètres ci-dessus (pour la classe AddGesture pour ne pas avoir a faire 2 fois les modifications sur les paramètres)
	public static Object[] getParameters() {
		Object[] result = new Object[2];
		result[0] = resetSkeletonNumber;
		result[1] = resamplingDistance;
		return result;
	}

	@Override
	public void initClassificationModule(KinectInterface kinectModule, MovementFoundInterface engine) {
		this.kinectModule = kinectModule;
		this.engine = engine ;

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


		int max = -1; //Used to determine the maximum length of gesture database
		int min = Integer.MAX_VALUE; //Used to determine the minimum length of gesture database
		
		//Construction of the Hashtable _gestureLength
		for (Enumeration<String> e = _gestures.keys() ; e.hasMoreElements() ; ) { 
			String key = e.nextElement();
			int value = _gestures.get(key).getOriginalGesture().getPoints().size();
			_gesturesLength.put(key, value);

			//Determination de la longueur maximale pour la file
			if (value > max) {
				max = value;
			}
			
			//Determination de la longueur minimale
			if (value < min) {
				min = value;
			}
			
			numberOfGestures++;
		}
		fifoLimit = max;
		minGesturesPoints = min;
		comparedTo.setSize(numberOfGestures);

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


		////Spatial resampling
		
		//First skeleton received : distance calculation is impossible, so we simply add coordinates and save the first skeleton in currentSkeleton
		if (firstSkeletonReceived) {
			points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			firstSkeletonReceived = false;
			currentSkeleton = newSkeleton;
			return ;
		}

		//Plus rapide. A supprimer a la version finale quand on aura plein de points du squelette (ici on ne s'interesse qu'a la main droite)
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

		//Spatial resampling
		if (distance(handRightCoordinates, handRightCoordinatestmp) < resamplingDistance) {
			return ;
		}

		//Si on arrive ici, 10 squelettes n'ont pas été recus depuis le dernier enregistrement dans la file et il n'y a pas eu de déplacement inférieur à resamplingDistance
		numberOfSkeletonReceived = 0;
		points.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
		currentSkeleton = newSkeleton;

		
		
		////Here we try to recognize gestures
		if (points.size() > minGesturesPoints) { //Recognition starts when fifo has more than minGesturesPoints elements
			
			//FIFO deletes first element when limit is reached
			if (points.size() >= fifoLimit) {
				points.remove(0);
			}
			
			
			int j = 0;
			for (Enumeration<String> element = _gestures.keys() ; element.hasMoreElements() ; ) { //element browses _gesture hashtable, so it browses every gestures in database
				String key = element.nextElement();
				if (_gesturesLength.get(key) <= points.size()) { //if FIFO's size is superior or equals a given gesture
					Vector<PointR> pointsTmp = new Vector<PointR>(); //the last number of points in recorded gesture of the FIFO will be recorded in pointsTmp
					for (int i = points.size() - _gesturesLength.get(key); i<points.size(); i++) {
						pointsTmp.add(points.get(i));
					}
					comparedTo.set(j, pointsTmp); //Adding these last points of FIFO in comparedTo
					if (comparedTo.get(j).size() > 1) {
						strokes.clear();
						strokes.add(comparedTo.get(j)); //These points are added to strokes
					}
					Object[] result = nDollarRecognizer(strokes); //Recognizer results are stocked in result
					if ((double) result[0] > confidenceValue) {
						System.out.println("Movement recognized : " + (String) result[1] + ", Score : " + (double) result[0]);
						points.clear();
						strokes.clear();
						comparedTo.clear();
						return ;
					}
				}
			}
			j++;
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

