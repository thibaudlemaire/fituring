package classification;

public class DatasFIFO {
	
	private float[][] tab;
	private int len;
	private int pointer;
	
	
	public DatasFIFO(int len) {
		this.len=len;
		this.tab = new float[len][3];
		this.pointer=len;
	}
	
	public void addData(float[] coordinates) {
		if (pointer == len){
			tab[pointer-1]=coordinates;
			pointer--;
		}
		else if (pointer > 0) {
			for (int i=pointer; i<len-1;i++){
				tab[i-1]=tab[i];
			}
			tab[len-1]=coordinates;
			pointer--;
		}
		else {
			for (int i=1; i<len-1 ; i++) {
				tab[i-1] = tab[i];
			}
			tab[len-1]=coordinates;
		}
	}
	
	public float[][] getFIFOTab(int n) {
		if (n <= 0){
			System.out.println("Error in DatasFIFO.getFIFOTab, size too small");
		}
		if (n>len){
			System.out.println("Error in DatasFIFO.getFIFOTab, size too large");
		}
		float[][] newTab=new float[n][3];
		for (int i=0; i<n;i++){
			newTab[i]=tab[i];
		}
		return newTab;
	}
	
}
