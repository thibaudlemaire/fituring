package detectionRythme;


public class SommeAutocorr {

private double[][] bc = new double[450][2]; ;
	
	public SommeAutocorr(Autocorrelation auto){
		for(int i=1;i<450;i++){
			bc[i][0]= auto.getData(i,0);
		}
	}
	public void SumAutocorr(Autocorrelation auto,PositionPics pospic){
			for (int j = 0; j<66;j++){
			if (pospic.getSelectionAutocorr()[j]){
				for(int i=0;i<450; i++){
					bc[i][1]=bc[i][1]+auto.getData(i,j+1);
				}
				
			}
		}
	}
	

	public double[][] diff1(double[][] B){
		double [][] D = new double[449][2];
		
			for(int l=0; l<449; l++){
				D[l][1]=B[l+1][1]-B[l][1];
			
		}
		return D;
	}
	
	public double[][] diff2(double[][] B){
		double [][] D = new double[448][2];
		
			for(int l=0; l<448; l++){
				D[l][1]=B[l+1][1]-B[l][1];
			
		}
		return D;
	}
	
	public double[][] sign(double[][] D){
		double [][] S = new double[449][2];
		for(int l=0; l<449; l++){
			if(D[l][1]>0){
				S[l][1]=1;
			}
			if(D[l][1]<0){
				S[l][1]=-1;
			}
			else{
				S[l][1]=0;
				}
		}
		return S;
			
	}
		
	public void detectionPics(){
		double max = 0.0;
		int i =0;
		for(int j =0;j<447;j++){
			max = Math.max(max,bc[j][1]);
			if(max==bc[j][1]){
				i = j;
			}
		}
		System.out.println("i : " + i);
		System.out.println("bc : " + this.bc[i][0]);
		System.out.println("BPM ="+3000/this.bc[i][0]);

	}
		
}