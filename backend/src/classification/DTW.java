package classification;

public class DTW {
	double[][] s;
	double[][] t;
	
	/* voir l'attribut comme une suite de coordonnées, par exemple [[x1,y1,z1], [x2,y2,z2],...] */
	
	public DTW(double[][] s, double[][] t)
	{
		this.s = s;
		this.t = t;
	}
	
	public double distance_euclid(double[] chaine1, double[] chaine2) {
		double result=0;
		for (int i = 0; i < chaine1.length; i++) {
			result=result + Math.sqrt(chaine2[i]*chaine2[i]-chaine1[i]*chaine1[i]);
		}
		return result;
	}
	
	
	/* en supposant que les sous-listes sont de longueur 3 : [x,y,z] ; à modifier sinon */
	
	public double distance_tchebychev(double[] chaine1, double[] chaine2) {
		return max(Math.abs(chaine2[0]-chaine1[0]) , Math.abs(chaine2[1]-chaine1[1]), Math.abs(chaine2[2]-chaine1[2]));
	}
	
	public double distance(double[] chaine1, double[] chaine2) {
		return distance_euclid(chaine1, chaine2);
		/* return distance_tchebychev(chaine1, chaine2) */
	}
	
	public double DTWDistance()
	{
		double[][] dtw = new double[s.length][t.length];
		for (int i = 0; i < s.length; i++)
		{
			dtw[i][0] = Integer.MAX_VALUE;
		}
		for (int j = 0; j < t.length; j++)
		{
			dtw[0][j] = Integer.MAX_VALUE;
		}
		dtw[0][0] = 0;
		
		double cost;
		for (int i = 0; i < s.length; i++)
		{
			for (int j = 0; j < t.length; j++)
			{
				cost = distance_euclid(s[i], t[j]);
				dtw[i][j] = cost + min(dtw[i-1][j], dtw[i][j-1], dtw[i-1][j-1]);
			}
		}
		return dtw[s.length][t.length];
	}
	
	private double min(double a, double b, double c)
	{
		if (a < b)
		{
			if (a < c)
			{
				return a;
			}
			return c;
		}
		else
		{
			if (b < c)
			{
				return b;
			}
			return c;
		}
	}
	
	private double max(double a, double b, double c)
	{
		if (a > b)
		{
			if (a > c)
			{
				return a;
			}
			return c;
		}
		else
		{
			if (b > c)
			{
				return b;
			}
			return c;
		}
	}
	
	/* Fonction nécessaire afin de pouvoir comparer 2 chaînes dont les coordonnées sont prises aux mêmes instants
	 * Réaliséeà l'aide d'une interpolation linéaire */
	public double[][] interpolation(double[][] chaine1, double[][] chaine2) {
		double[][] result = new double[chaine1.length][3];
		int a = 0;
		double t1 = chaine2[0][2];
		double t2 = chaine2[1][2];
		for (int i = 0 ; i < chaine1.length ; i++){
			double t0 = chaine1[i][2];
			while (t1 > t0 || t2 < t0){
				a=a+1;
				t1 = chaine2[a][2];
				t2 = chaine2[a+1][2];
			}
			if (t1 == t0)
				result[i]=chaine2[a];
			else if (t2 == t0)
				result[i]=chaine2[a+1];
			else {
				double x = chaine2[a][0]+(chaine2[a+1][0]-chaine2[a][0])*(t0-t1)/(t2-t1);
				double y = chaine2[a][1]+(chaine2[a+1][1]-chaine2[a][1])*(t0-t1)/(t2-t1);
				result[i][0]=x;
				result[i][1]=y;   
				result[i][2]=t0; 				
			}
		}
		return result; 
	}
}
