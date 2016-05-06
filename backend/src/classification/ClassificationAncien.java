package classification;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Enumeration;
import java.util.Vector;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.ClassificationInterface;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.MovementFoundInterface;
import ndollar.*;

public class ClassificationAncien implements ClassificationInterface, KinectListenerInterface {

	MovementFoundInterface engine ;
	KinectInterface kinectModule;

	//Right hand
	Vector<PointR> pointsRightHand1 = new Vector<PointR>();
	Vector<PointR> pointsRightHand2 = new Vector<PointR>();
	Vector<Vector<PointR>> strokesRightHand1 = new Vector<Vector<PointR>>();
	Vector<Vector<PointR>> strokesRightHand2 = new Vector<Vector<PointR>>();

	//Left hand
	Vector<PointR> pointsLeftHand1 = new Vector<PointR>();
	Vector<PointR> pointsLeftHand2 = new Vector<PointR>();
	Vector<Vector<PointR>> strokesLeftHand1 = new Vector<Vector<PointR>>();
	Vector<Vector<PointR>> strokesLeftHand2 = new Vector<Vector<PointR>>();

	static NDollarRecognizerAncien _rec = new NDollarRecognizerAncien();

	int numberOfSkeletonReceived = 0; //Counts how many skeletons have been received
	Skeleton currentSkeleton = new Skeleton();
	///////Options :
	static int resetSkeletonNumber = 10; //Adds coordinates in the file every resetSkeletonNumber skeleton received
	int fifoLimit1 = 25; //size of the fifo1
	int fifoLimit2 = 35; //size of the fifo2
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
	public void initClassificationModule(KinectInterface kinectModule, MovementFoundInterface engine) {
		// TODO Auto-generated method stub
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

		// read them
		for (int i = 0; i < allXMLFiles.length; ++i) {
			_rec.LoadGesture(allXMLFiles[i]);
		}

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

		float[] handLeftCoordinates = new float[3];
		handLeftCoordinates[0] = newSkeleton.get3DJointX(Skeleton.HAND_LEFT)-baseX;
		handLeftCoordinates[1] = newSkeleton.get3DJointY(Skeleton.HAND_LEFT)-baseY;
		handLeftCoordinates[2] = newSkeleton.get3DJointZ(Skeleton.HAND_LEFT)-baseZ;


		//Echantillonage spatial

		if (firstSkeletonReceived) {
			pointsRightHand1.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			pointsRightHand2.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			pointsLeftHand1.add(new PointR(handLeftCoordinates[0], handLeftCoordinates[1]));
			pointsLeftHand2.add(new PointR(handLeftCoordinates[0], handLeftCoordinates[1]));
			firstSkeletonReceived = false;
			currentSkeleton = newSkeleton;
			return ;
		}

		//Plus rapide. A supprimer a la version finale quand on aura plein de points du squelette
		float[] handRightCoordinatestmp = new float[3];
		handRightCoordinatestmp[0] = currentSkeleton.get3DJointX(Skeleton.HAND_RIGHT)-baseX;
		handRightCoordinatestmp[1] = currentSkeleton.get3DJointY(Skeleton.HAND_RIGHT)-baseY;
		handRightCoordinatestmp[2] = currentSkeleton.get3DJointZ(Skeleton.HAND_RIGHT)-baseZ;

		float[] handLeftCoordinatestmp = new float[3];
		handLeftCoordinatestmp[0] = currentSkeleton.get3DJointX(Skeleton.HAND_LEFT)-baseX;
		handLeftCoordinatestmp[1] = currentSkeleton.get3DJointY(Skeleton.HAND_LEFT)-baseY;
		handLeftCoordinatestmp[2] = currentSkeleton.get3DJointZ(Skeleton.HAND_LEFT)-baseZ;


		//Ajout dans la file au moins toutes les 333ms
		if (numberOfSkeletonReceived >= resetSkeletonNumber) {
			pointsRightHand1.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			pointsRightHand2.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
			pointsLeftHand1.add(new PointR(handLeftCoordinates[0], handLeftCoordinates[1]));
			pointsLeftHand2.add(new PointR(handLeftCoordinates[0], handLeftCoordinates[1]));
			numberOfSkeletonReceived = 0;
			currentSkeleton = newSkeleton;
			return ;
		}

		if (distance(handRightCoordinates, handRightCoordinatestmp) < resamplingDistance) {
			return ;
		}

		if (distance(handRightCoordinates, handLeftCoordinatestmp) < resamplingDistance) {
			return ;
		}

		//Si on arrive ici, 10 squelettes n'ont pas été recus depuis le dernier enregistrement dans la file et il n'y a pas eu de déplacement inférieur à resamplingDistance
		numberOfSkeletonReceived = 0;
		pointsRightHand1.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
		pointsRightHand2.add(new PointR(handRightCoordinates[0], handRightCoordinates[1]));
		pointsLeftHand1.add(new PointR(handLeftCoordinates[0], handLeftCoordinates[1]));
		pointsLeftHand2.add(new PointR(handLeftCoordinates[0], handLeftCoordinates[1]));
		currentSkeleton = newSkeleton;

		//Gestion de la file main droite 1
		if (pointsRightHand1.size() > fifoLimit1) {
			pointsRightHand1.remove(0);
			pointsLeftHand1.remove(0);
			strokesRightHand1.clear();
			strokesLeftHand1.clear();
			if (pointsRightHand1.size() > 1) {
				strokesRightHand1.add(new Vector<PointR>(pointsRightHand1));
			}
			if (pointsLeftHand1.size() > 1) {
				strokesLeftHand1.add(new Vector<PointR>(pointsLeftHand1));
			}

			Object[] resultRightHand1 = nDollarRecognizer(strokesRightHand1);
			Object[] resultLeftHand1 = nDollarRecognizer(strokesLeftHand1);
			if ((double) resultRightHand1[0] > confidenceValue && !(((String) resultRightHand1[1]).equals("Line"))) {
				System.out.println("Movement recognized : " + (String) resultRightHand1[1] + ", Score : " + (double) resultRightHand1[0]);
				pointsRightHand1.clear();
				pointsRightHand2.clear();
				pointsLeftHand1.clear();
				pointsLeftHand2.clear();
				return ;
			}

			if ((double) resultLeftHand1[0] > confidenceValue && !(((String) resultLeftHand1[1]).equals("Line"))) {
				System.out.println("Movement recognized : " + (String) resultLeftHand1[1] + ", Score : " + (double) resultLeftHand1[0]);
				pointsRightHand1.clear();
				pointsRightHand2.clear();
				pointsLeftHand1.clear();
				pointsLeftHand2.clear();
				return ;
			}
		}

		//Gestion de la file main droite 2
		if (pointsRightHand2.size() > fifoLimit2) {
			pointsRightHand2.remove(0);
			pointsLeftHand2.remove(0);
			strokesRightHand2.clear();
			strokesLeftHand2.clear();
			if (pointsRightHand2.size() > 1) {
				strokesRightHand2.add(new Vector<PointR>(pointsRightHand2));
			}
			
			if (pointsLeftHand2.size() > 1) {
				strokesLeftHand2.add(new Vector<PointR>(pointsLeftHand2));
			}
			
			Object[] resultRightHand2 = nDollarRecognizer(strokesRightHand2);
			Object[] resultLeftHand2 = nDollarRecognizer(strokesLeftHand2);
			if ((double) resultRightHand2[0] > confidenceValue && !(((String) resultRightHand2[1]).equals("Line"))) {
				System.out.println("Movement recognized : " + (String) resultRightHand2[1] + ", Score : " + (double) resultRightHand2[0]);
				pointsRightHand1.clear();
				pointsRightHand2.clear();
				pointsLeftHand1.clear();
				pointsLeftHand2.clear();
				return ;
			}
			
			if ((double) resultLeftHand2[0] > confidenceValue && !(((String) resultLeftHand2[1]).equals("Line"))) {
				System.out.println("Movement recognized : " + (String) resultLeftHand2[1] + ", Score : " + (double) resultLeftHand2[0]);
				pointsRightHand1.clear();
				pointsRightHand2.clear();
				pointsLeftHand1.clear();
				pointsLeftHand2.clear();
				return ;
			}
			
			if (((String) resultRightHand2[1]).equals("Line") && ((String) resultLeftHand2[1]).equals("Line") && ((double) resultRightHand2[0])*((double)resultLeftHand2[0]) > 0.6) {
				System.out.println("Movement recognized : Line, Score : " + ((double) resultRightHand2[0])*((double)resultLeftHand2[0]));
				pointsRightHand1.clear();
				pointsRightHand2.clear();
				pointsLeftHand1.clear();
				pointsLeftHand2.clear();
				return ;
			}
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
		if (strokesRightHand1.size() > 0) {
			Vector<PointR> allPoints = new Vector<PointR>();
			Enumeration<Vector<PointR>> en = strokesRightHand1.elements();
			while (en.hasMoreElements()) {
				Vector<PointR> pts = en.nextElement();
				allPoints.addAll(pts);
			}
			NBestList result = _rec.Recognize(allPoints, strokesRightHand1.size());
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

