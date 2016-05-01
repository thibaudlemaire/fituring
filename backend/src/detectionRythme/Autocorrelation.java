package detectionRythme;

public class Autocorrelation {
	
	private double[][] ac ;
	
	public Autocorrelation(TableauDonneesInterpolees tabI){
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
	
	public double[][] diff1(double[][] B){
		double[][] D = new double[449][66];
		for(int k=1; k<66; k++){
			for(int l=0; l<449; l++){
				D[l][k-1]=B[l+1][k]-B[l][k];
			}
		}
		return D;
	}
	
	public double[][] diff2(double[][] B){
		double[][] D = new double[448][66];
		for(int k=0; k<66; k++){
			for(int l=0; l<448; l++){
				D[l][k]=B[l+1][k]-B[l][k];
			}
		}
		return D;
	}
	
	public double[][] sign(double[][] D){
		double [][] S = new double[449][66];
		for(int k=1; k<66; k++){
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
		double[][] H = diff2(sign(diff1(ac)));
		for(int i =0;i<448;i++){
			for(int j=0;j<66;j++){
				if(H[i][j]==-2){
					P.getPics()[i+1][j+1] = true ;
				}
			}
		}
	}
	
	public void test1et2(PositionPics P){
		for(int i =0;i<450;i++){
			for(int j=0;j<66;j++){
				
				
			}
		}
	}
	
	
}

	