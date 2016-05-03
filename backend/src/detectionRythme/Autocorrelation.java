package detectionRythme;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

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
	
	public double[][] diff1(double[][] bc){
		double[][] D = new double[449][67];
		for(int k=1; k<67; k++){
			for(int h=0; h<449; h++){
				D[h][k]=bc[h+1][k]-bc[h][k];
			}
		}
		return D;
	}
	
	public double[][] diff2(double[][] B){
		double[][] D = new double[448][67];
		for(int k=1; k<67; k++){
			for(int h=0; h<448; h++){
			D[h][k]=B[h+1][k]-B[h][k];
			
			}
		}
		return D;
	}
	
	public double[][] sign(double[][] D){
		double [][] S = new double[449][67];
		for(int k=1; k<67; k++){
			for(int l=0; l<449; l++){
				if(D[l][k]>0){
					S[l][k]=1;
				}
				if(D[l][k]<0){
					S[l][k]=-1;
				}
				
				
			}
		}
		return S;
	}
		
	public void detectionPics(PositionPics P){
		double[][] F = diff1(ac);
		double[][] G = sign(F);
		double[][] H = diff2(G);
		
		for(int i =0;i<448;i++){
			for(int j=1;j<67;j++){
				if(H[i][j]==-2){
					P.getPics()[i+1][j-1] = true ;
				}
			}
		}
	}
	
	
	public void test1et2(PositionPics P){
		for(int i =0;i<450;i++){
			for(int j=0;j<66;j++){
				if ((i<12)||(i>80)){	
					P.setPics(i,j,false) ;
				}
				if(P.getPics()[i][j]){
					if(ac[i][j]<ac[i-1][j]||ac[i][j]<ac[i-2][j]||ac[i][j]<ac[i-3][j]||ac[i][j]<ac[i+1][j]||ac[i][j]<ac[i+2][j]||ac[i][j]<ac[i+3][j]){
						P.setPics(i,j,false);
					}
					
				}
				if (ac[i][j]/(1-i/450)<0.6){
					P.setPics(i,j,false);
				}
				
				
			}
		}
	}
	
	
}

	