package detectionRythme;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.LectureAudioSimpleInterface;
import interfaces.RyhtmeInterface;

public class DetectionRythme implements RyhtmeInterface, KinectListenerInterface{

	private TableauDonnéesBrutes tab;
	private TableauDonnéesInterpolées tabI;
	private double bpm;
	
	public void skeletonReceived(KinectEventInterface e){
		for(int i = 0; i<299; i++){
			for(int j = 0; j<61;j++){
				tab.setData(i,j,tab.getData(i+1,j));
			}
		}
		tab[300][0]=(double) e.getSkeletonTime();
		tab[300][1]=(double) e.getNewSkeleton().get3DJointX(Skeleton.SPINE_BASE);
		tab[300][2]=(double) e.getNewSkeleton().get3DJointY(Skeleton.SPINE_BASE);
		tab[300][3]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.SPINE_BASE);
		tab[300][4]=(double) e.getNewSkeleton().get3DJointX(Skeleton.SPINE_MID);
		tab[300][5]=(double) e.getNewSkeleton().get3DJointY(Skeleton.SPINE_MID);
		tab[300][6]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.SPINE_MID);
		tab[300][7]=(double) e.getNewSkeleton().get3DJointX(Skeleton.NECK);
		tab[300][8]=(double) e.getNewSkeleton().get3DJointY(Skeleton.NECK);
		tab[300][9]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.NECK);
		tab[300][10]=(double) e.getNewSkeleton().get3DJointX(Skeleton.HEAD);
		tab[300][11]=(double) e.getNewSkeleton().get3DJointY(Skeleton.HEAD);
		tab[300][12]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.HEAD);
		tab[300][13]=(double) e.getNewSkeleton().get3DJointX(Skeleton.SHOULDER_LEFT);
		tab[300][14]=(double) e.getNewSkeleton().get3DJointY(Skeleton.SHOULDER_LEFT);
		tab[300][15]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.SHOULDER_LEFT);
		tab[300][16]=(double) e.getNewSkeleton().get3DJointX(Skeleton.ELBOW_LEFT);
		tab[300][17]=(double) e.getNewSkeleton().get3DJointY(Skeleton.ELBOW_LEFT);
		tab[300][18]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.ELBOW_LEFT);
		tab[300][19]=(double) e.getNewSkeleton().get3DJointX(Skeleton.WRIST_LEFT);
		tab[300][20]=(double) e.getNewSkeleton().get3DJointY(Skeleton.WRIST_LEFT);
		tab[300][21]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.WRIST_LEFT);
		tab[300][22]=(double) e.getNewSkeleton().get3DJointX(Skeleton.HAND_LEFT);
		tab[300][23]=(double) e.getNewSkeleton().get3DJointY(Skeleton.HAND_LEFT);
		tab[300][24]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.HAND_LEFT);
		tab[300][25]=(double) e.getNewSkeleton().get3DJointX(Skeleton.SHOULDER_RIGHT);
		tab[300][26]=(double) e.getNewSkeleton().get3DJointY(Skeleton.SHOULDER_RIGHT);
		tab[300][27]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.SHOULDER_RIGHT);
		tab[300][28]=(double) e.getNewSkeleton().get3DJointX(Skeleton.ELBOW_RIGHT);
		tab[300][29]=(double) e.getNewSkeleton().get3DJointY(Skeleton.ELBOW_RIGHT);
		tab[300][30]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.ELBOW_RIGHT);
		tab[300][31]=(double) e.getNewSkeleton().get3DJointX(Skeleton.WRIST_RIGHT);
		tab[300][32]=(double) e.getNewSkeleton().get3DJointY(Skeleton.WRIST_RIGHT);
		tab[300][33]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.WRIST_RIGHT);
		tab[300][34]=(double) e.getNewSkeleton().get3DJointX(Skeleton.HAND_RIGHT);
		tab[300][35]=(double) e.getNewSkeleton().get3DJointY(Skeleton.HAND_RIGHT);
		tab[300][36]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.HAND_RIGHT);
		tab[300][37]=(double) e.getNewSkeleton().get3DJointX(Skeleton.HIP_LEFT);
		tab[300][38]=(double) e.getNewSkeleton().get3DJointY(Skeleton.HIP_LEFT);
		tab[300][39]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.HIP_LEFT);
		tab[300][40]=(double) e.getNewSkeleton().get3DJointX(Skeleton.KNEE_LEFT);
		tab[300][41]=(double) e.getNewSkeleton().get3DJointY(Skeleton.KNEE_LEFT);
		tab[300][42]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.KNEE_LEFT);
		tab[300][43]=(double) e.getNewSkeleton().get3DJointX(Skeleton.ANKLE_LEFT);
		tab[300][44]=(double) e.getNewSkeleton().get3DJointY(Skeleton.ANKLE_LEFT);
		tab[300][45]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.ANKLE_LEFT);
		tab[300][46]=(double) e.getNewSkeleton().get3DJointX(Skeleton.FOOT_LEFT);
		tab[300][47]=(double) e.getNewSkeleton().get3DJointY(Skeleton.FOOT_LEFT);
		tab[300][48]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.FOOT_LEFT);
		tab[300][49]=(double) e.getNewSkeleton().get3DJointX(Skeleton.HIP_RIGHT);
		tab[300][50]=(double) e.getNewSkeleton().get3DJointY(Skeleton.HIP_RIGHT);
		tab[300][51]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.HIP_RIGHT);
		tab[300][52]=(double) e.getNewSkeleton().get3DJointX(Skeleton.KNEE_RIGHT);
		tab[300][53]=(double) e.getNewSkeleton().get3DJointY(Skeleton.KNEE_RIGHT);
		tab[300][54]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.KNEE_RIGHT);
		tab[300][55]=(double) e.getNewSkeleton().get3DJointX(Skeleton.ANKLE_RIGHT);
		tab[300][56]=(double) e.getNewSkeleton().get3DJointY(Skeleton.ANKLE_RIGHT);
		tab[300][57]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.ANKLE_RIGHT);
		tab[300][58]=(double) e.getNewSkeleton().get3DJointX(Skeleton.FOOT_RIGHT);
		tab[300][59]=(double) e.getNewSkeleton().get3DJointY(Skeleton.FOOT_RIGHT);
		tab[300][60]=(double) e.getNewSkeleton().get3DJointZ(Skeleton.FOOT_RIGHT);
	}

}
