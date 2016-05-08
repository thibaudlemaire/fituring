package detectionRythme;

public class TableauDonneesInterpolees {

	private double[][] tab;
	
	public  TableauDonneesInterpolees(TableauDonneesBrutes m){
		this.tab = new double[450][67];
		tab[0][0]=m.getData(0,0);
		for(int i=1;i<450;i++){
			tab[i][0]=tab[0][0]+i*0.02;
		}
	}
	
	public double getData(int line,int column){
		return tab[line][column];
	}
	public double[] getColumn(int column){
		double[] col = new double[450];
		for (int i= 0;i<450;i++){
			col[i]=tab[i][column];
		}
		return col;
	}

	public void setData(int line, int column, double newValue){
		tab[line][column]=newValue;
	}
	
	public double getDistance(int point1, int point2,int temps){
		double x1= tab[temps][point1];
		double y1=tab[temps][point1+1];
		double z1=tab[temps][point1+2];
		double x2=tab[temps][point2];
		double y2=tab[temps][point2+1];
		double z2=tab[temps][point2+2];
		double X=(x1-x2)*(x1-x2);
		double Y=(y1-y2)*(y1-y2);
		double Z=(z1-z2)*(z1-z2);
		return java.lang.Math.sqrt(X+Y+Z);
	}
	
	public void autocorrelation(Autocorrelation autoc){
		for(int j = 1; j<67 ; j++){
			double m = 0;
			for (int i = 0 ; i < 450 ; i++){
				m = m + tab[i][j];
			}
			m = m/450;
			
			double v=0;
			for(int i=0;i<450;i++){
				v=v+(tab[i][j]-m)*(tab[i][j]-m);
			}
			v=v/450;
			
			for(int h=0;h<450;h++){
				double a = 0;
				for(int t=h; t<450;t++){
					a = a + (tab[t][j]-m)*(tab[t-h][j]-m);
				}
				autoc.setData(h,j,a/(v*450));
				
			}
		}
	}
}

