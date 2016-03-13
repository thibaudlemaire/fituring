package classification;

public class DatasFIFO {
	
	private float[][] tab;
	private int len;
	
	
	public DatasFIFO(int len) {
		this.len=len;
		this.tab = new float[len][3];
		this.pointer=len;
	}
	
	public void addData(float[] coordinates) {
		if (pointer > 0) {
			tab[pointer-1]=coordinates;
			pointer--;
		}
		
		else {
			for (int i=len-1; i>0 ; i--) {
				tab[i] = tab[i-1];
			}
			tab[0]=coordinates;
		}
	}

	public float[][] getFIFOTab() {
		return tab;
	}
	
}
