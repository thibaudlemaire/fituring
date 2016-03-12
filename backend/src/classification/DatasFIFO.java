package classification;

public class DatasFIFO {
	
	double[][] tab;
	int len;
	
	
	// the size is 30 cause it matchs the duration of our recorded movements
	public DatasFIFO() {
		this.tab = new double[30][3];
		this.len=0;
	}
	
	public void addData(double[] coordinates) {
		if (len < 29) {
			tab[len+1]=coordinates;
			len++;
		}
		
		else {
			for (int i=0; i<29 ; i++) {
				tab[i] = tab[i+1];
			}
			tab[29]=coordinates;
		}

	}
	
	
	

}
