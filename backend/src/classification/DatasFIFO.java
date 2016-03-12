package classification;

public class DatasFIFO {
	
	double[][] tab;
	int len;
	
	public DatasFIFO() {
		this.tab = new double[100][3];
		this.len=0;
	}
	
	public void addData(double[] coordinates) {
		if (len < 99) {
			tab[len+1]=coordinates;
			len++;
		}
		
		else {
			for (int i=0; i<98 ; i++) {
				tab[i] = tab[i+1];
			}
			tab[99]=coordinates;
		}

	}
	
	
	

}
