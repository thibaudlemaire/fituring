package detectionRythme;

import java.util.Date;

import edu.ufl.digitalworlds.j4k.Skeleton;
import interfaces.KinectEventInterface;
import interfaces.KinectInterface;
import interfaces.KinectListenerInterface;
import interfaces.LectureAudioSimpleInterface;
import interfaces.RyhtmeInterface;
import kinect.Kinect;


public class DetectionRythme implements RyhtmeInterface, KinectListenerInterface {

	private TableauDonneesBrutes tab;
	private TableauDonneesInterpolees tabI;
	private Autocorrelation autoc;
	private PositionPics pics;
	private int compteur;
	KinectInterface kinect;
	private long time = 0;
	
	public DetectionRythme(){
		this.tab = new TableauDonneesBrutes();
		this.tabI = new TableauDonneesInterpolees(tab);
		this.autoc = new Autocorrelation(tabI);
		this.pics = new PositionPics();
		this.compteur=0;
	}
	
	public void setCompteurTo200(){
		this.compteur=200;
	}
	
	public int getCompteur(){
		return this.compteur;
	}
	
	public void skeletonReceived(KinectEventInterface e){
		
		if (compteur == 0) {
			time = new Date().getTime();
		}
		
		for(int i = 0; i<299; i++){
			for(int j = 0; j<61;j++){
				tab.setData(i,j,tab.getData(i+1,j));
			}
		}
		tab.setData(299, 0,(double) (e.getSkeletonTime() - time)/1000);
		tab.setData(299,1,(double) e.getNewSkeleton().get3DJointX(Skeleton.SPINE_BASE));
		tab.setData(299,2,(double) e.getNewSkeleton().get3DJointY(Skeleton.SPINE_BASE));
		tab.setData(299,3,(double) e.getNewSkeleton().get3DJointZ(Skeleton.SPINE_BASE));
		tab.setData(299,4,(double) e.getNewSkeleton().get3DJointX(Skeleton.SPINE_MID));
		tab.setData(299,5,(double) e.getNewSkeleton().get3DJointY(Skeleton.SPINE_MID));
		tab.setData(299,6,(double) e.getNewSkeleton().get3DJointZ(Skeleton.SPINE_MID));
		tab.setData(299,7,(double) e.getNewSkeleton().get3DJointX(Skeleton.NECK));
		tab.setData(299,8,(double) e.getNewSkeleton().get3DJointY(Skeleton.NECK));
		tab.setData(299,9,(double) e.getNewSkeleton().get3DJointZ(Skeleton.NECK));
		tab.setData(299,10,(double) e.getNewSkeleton().get3DJointX(Skeleton.HEAD));
		tab.setData(299,11,(double) e.getNewSkeleton().get3DJointY(Skeleton.HEAD));
		tab.setData(299,12,(double) e.getNewSkeleton().get3DJointZ(Skeleton.HEAD));
		tab.setData(299,13,(double) e.getNewSkeleton().get3DJointX(Skeleton.SHOULDER_LEFT));
		tab.setData(299,14,(double) e.getNewSkeleton().get3DJointY(Skeleton.SHOULDER_LEFT));
		tab.setData(299,15,(double) e.getNewSkeleton().get3DJointZ(Skeleton.SHOULDER_LEFT));
		tab.setData(299,16,(double) e.getNewSkeleton().get3DJointX(Skeleton.ELBOW_LEFT));
		tab.setData(299,17,(double) e.getNewSkeleton().get3DJointY(Skeleton.ELBOW_LEFT));
		tab.setData(299,18,(double) e.getNewSkeleton().get3DJointZ(Skeleton.ELBOW_LEFT));
		tab.setData(299,19,(double) e.getNewSkeleton().get3DJointX(Skeleton.WRIST_LEFT));
		tab.setData(299,20,(double) e.getNewSkeleton().get3DJointY(Skeleton.WRIST_LEFT));
		tab.setData(299,21,(double) e.getNewSkeleton().get3DJointZ(Skeleton.WRIST_LEFT));
		tab.setData(299,22,(double) e.getNewSkeleton().get3DJointX(Skeleton.HAND_LEFT));
		tab.setData(299,23,(double) e.getNewSkeleton().get3DJointY(Skeleton.HAND_LEFT));
		tab.setData(299,24,(double) e.getNewSkeleton().get3DJointZ(Skeleton.HAND_LEFT));
		tab.setData(299,25,(double) e.getNewSkeleton().get3DJointX(Skeleton.SHOULDER_RIGHT));
		tab.setData(299,26,(double) e.getNewSkeleton().get3DJointY(Skeleton.SHOULDER_RIGHT));
		tab.setData(299,27,(double) e.getNewSkeleton().get3DJointZ(Skeleton.SHOULDER_RIGHT));
		tab.setData(299,28,(double) e.getNewSkeleton().get3DJointX(Skeleton.ELBOW_RIGHT));
		tab.setData(299,29,(double) e.getNewSkeleton().get3DJointY(Skeleton.ELBOW_RIGHT));
		tab.setData(299,30,(double) e.getNewSkeleton().get3DJointZ(Skeleton.ELBOW_RIGHT));
		tab.setData(299,31,(double) e.getNewSkeleton().get3DJointX(Skeleton.WRIST_RIGHT));
		tab.setData(299,32,(double) e.getNewSkeleton().get3DJointY(Skeleton.WRIST_RIGHT));
		tab.setData(299,33,(double) e.getNewSkeleton().get3DJointZ(Skeleton.WRIST_RIGHT));
		tab.setData(299,34,(double) e.getNewSkeleton().get3DJointX(Skeleton.HAND_RIGHT));
		tab.setData(299,35,(double) e.getNewSkeleton().get3DJointY(Skeleton.HAND_RIGHT));
		tab.setData(299,36,(double) e.getNewSkeleton().get3DJointZ(Skeleton.HAND_RIGHT));
		tab.setData(299,37,(double) e.getNewSkeleton().get3DJointX(Skeleton.HIP_LEFT));
		tab.setData(299,38,(double) e.getNewSkeleton().get3DJointY(Skeleton.HIP_LEFT));
		tab.setData(299,39,(double) e.getNewSkeleton().get3DJointZ(Skeleton.HIP_LEFT));
		tab.setData(299,40,(double) e.getNewSkeleton().get3DJointX(Skeleton.KNEE_LEFT));
		tab.setData(299,41,(double) e.getNewSkeleton().get3DJointY(Skeleton.KNEE_LEFT));
		tab.setData(299,42,(double) e.getNewSkeleton().get3DJointZ(Skeleton.KNEE_LEFT));
		tab.setData(299,43,(double) e.getNewSkeleton().get3DJointX(Skeleton.ANKLE_LEFT));
		tab.setData(299,44,(double) e.getNewSkeleton().get3DJointY(Skeleton.ANKLE_LEFT));
		tab.setData(299,45,(double) e.getNewSkeleton().get3DJointZ(Skeleton.ANKLE_LEFT));
		tab.setData(299,46,(double) e.getNewSkeleton().get3DJointX(Skeleton.FOOT_LEFT));
		tab.setData(299,47,(double) e.getNewSkeleton().get3DJointY(Skeleton.FOOT_LEFT));
		tab.setData(299,48,(double) e.getNewSkeleton().get3DJointZ(Skeleton.FOOT_LEFT));
		tab.setData(299,49,(double) e.getNewSkeleton().get3DJointX(Skeleton.HIP_RIGHT));
		tab.setData(299,50,(double) e.getNewSkeleton().get3DJointY(Skeleton.HIP_RIGHT));
		tab.setData(299,51,(double) e.getNewSkeleton().get3DJointZ(Skeleton.HIP_RIGHT));
		tab.setData(299,52,(double) e.getNewSkeleton().get3DJointX(Skeleton.KNEE_RIGHT));
		tab.setData(299,53,(double) e.getNewSkeleton().get3DJointY(Skeleton.KNEE_RIGHT));
		tab.setData(299,54,(double) e.getNewSkeleton().get3DJointZ(Skeleton.KNEE_RIGHT));
		tab.setData(299,55,(double) e.getNewSkeleton().get3DJointX(Skeleton.ANKLE_RIGHT));
		tab.setData(299,56,(double) e.getNewSkeleton().get3DJointY(Skeleton.ANKLE_RIGHT));
		tab.setData(299,57,(double) e.getNewSkeleton().get3DJointZ(Skeleton.ANKLE_RIGHT));
		tab.setData(299,58,(double) e.getNewSkeleton().get3DJointX(Skeleton.FOOT_RIGHT));
		tab.setData(299,59,(double) e.getNewSkeleton().get3DJointY(Skeleton.FOOT_RIGHT));
		tab.setData(299,60,(double) e.getNewSkeleton().get3DJointZ(Skeleton.FOOT_RIGHT));
	
		compteur=compteur+1;
		
		
		if(this.getCompteur()==300){
			tab.interpolationEtDistance(tabI);
			tabI.autocorrelation(autoc);
			autoc.detectionPics(pics);
			autoc.test1et2(pics);
			pics.SetSelectionAutocorr();
			SommeAutocorr sumAuto = new SommeAutocorr(autoc);
			sumAuto.SumAutocorr(autoc,pics);
			sumAuto.detectionPics();
			this.setCompteurTo200();
			
			for (int i = 0; i < 449; i++) {
				System.out.println("pics " + i + " : " + pics.getPics()[i][63] );
			
			}
		}
	}
	
	public void startListening() {
		kinect.setListener(this);
	}
	
	public void stopListening() {
		kinect.unsetListener(this);
	}

	@Override
	public void initRythmeModule(KinectInterface kinect, LectureAudioSimpleInterface audio) {
		this.kinect = kinect;
	}

	@Override
	public int getCurrentTrueBPM() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCurrentUsedBPM() {
		// TODO Auto-generated method stub
		return 0;
	}
}

