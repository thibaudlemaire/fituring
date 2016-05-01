package detectionRythme;

public class TableauDonneesBrutes {

	private double[][] tab;
	
	public  TableauDonneesBrutes(){
		this.tab = new double[300][61];
	}
	
	public double getData(int line,int column){
		return tab[line][column];
	}

	public void setData(int line, int column, double newValue){
		tab[line][column]=newValue;
	}
	
	
	public void interpolationEtDistance(TableauDonneesInterpolees tabI){
		for (int j=1; j<61;j++){
			tabI.setData(0,j,tab[0][j]);
			for(int i=1; i<450;i++){
				for(int k=0;k<299;k++){
					if(tabI.getData(i,0)>= tab[k][0] && tabI.getData(i,0)< tab[k+1][0]){
						double y2 = tab[k+1][j];
						double y1= tab[k][j];
						double t2 = tab[k+1][0];
						double t1 = tab[k][0];
						tabI.setData(i,j,(((y2-y1)*(tabI.getData(i,0)-t1)/(t2-t1))+y1));
					}
				}
			}
		}
		for (int i = 0; i<450;i++){
			tabI.setData(i,61,tabI.getDistance(47,59,i));
			tabI.setData(i,62,tabI.getDistance(23,14,i));
			tabI.setData(i,63,tabI.getDistance(35,26,i));
			tabI.setData(i,64,tabI.getDistance(35,23,i));
			tabI.setData(i,65,tabI.getDistance(38,47,i));
			tabI.setData(i,66,tabI.getDistance(50,59,i));
		}
	}
}
