package detectionRythme;


public class SommeAutocorr {

private double[][] bc;  
	
	public SommeAutocorr(){
		this.bc=new double[450][2];
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
		}
		return S;
			
	}
		
	public void detectionPics(){
		double[][] H = diff2(sign(diff1(bc)));
		int i =0;
		while(H[i][1]!=-2 && i<447){
			i++;
		}
		i=i+1;
		System.out.println("i : " + i);
		System.out.println("BPM ="+3000/i);

	}
		
}