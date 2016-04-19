package detectionRythme;

public class Autocorrelation {
	
	private double[][] ac ;
	
	public Autocorrelation(TableauDonnéesInterpolées tabI){
		this.ac = new double[450][67];
		for(int i=1;i<450;i++){
			ac[i][0]= tabI.getData(i,0);
		}
	}
	
	public double getData(int line,int column){
		return ac[line][column];
	}

	public void setData(int line, int column, double newValue){
		ac[line][column]=newValue;
	}
	
	public double[][] diff(double[][] B){
		double[][] D = new double[449][67];
		for(int k=1; k<67; k++){
			for(int l=0; l<449; l++){
				D[l][k]=B[l+1][k]-B[l][k];
			}
		}
		return D;
	}
	
	public double[][] sign(double[][] D){
		double [][] S = new double[450][66];
		for(int k=1; k<67; k++){
			for(int l=0; l<449; l++){
				if(D[l][k]>0){
					S[l][k]=1;
				}
				if(D[l][k]<0){
					S[l][k]=-1;
				}
				else{
					S[l][k]=0;
				}
			}
		}
		return S;
	}
		
	public void detectionPics(PositionPics P){
		double[][] H = diff(sign(diff(ac)));
		for(int i =0;i<450;i++){
			for(int j=0;j<66;j++){
				if(H[i][j]==-2){
					P.getPics()[i+1][j] = true ;
				}
			}
		}
	}
	
	public void test1et2(PositionPics P){
		for(int i =0;i<450;i++){
			for(int j=0;j<66;j++){
				if ((i<12)||(i>80)){	
					P.getPics()[i][j]=false ;
				}
				if(P.getPics()[i][j]){
					if(ac[i][j]<ac[i-1][j]||ac[i][j]<ac[i-2][j]||ac[i][j]<ac[i-3][j]||ac[i][j]<ac[i+1][j]||ac[i][j]<ac[i+2][j]||ac[i][j]<ac[i+3][j]){
						P.getPics()[i][j]=false;
					}
					if (ac[i][j]/(1-i/450)<0.6){
						P.getPics()[i][j]=false;
					}
				}
			}
		}
	}
	
	
}

	