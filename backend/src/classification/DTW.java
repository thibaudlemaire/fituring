package classification;

public class DTW {
	float[][] s;
	float[][] t;
	
	/* voir l'attribut comme une suite de coordonn�es, par exemple [[x1,y1,z1], [x2,y2,z2],...] */
	
	public DTW(float[][] s, float[][] t)
	{
		this.s = s;
		this.t = t;
	}
	
	public float distance_euclid(float[] chaine1, float[] chaine2) {
		float result=0;
		for (int i = 0; i < chaine1.length; i++) {
			result= (float) (result + (chaine2[i]-chaine1[i])*(chaine2[i]-chaine1[i]));
		}
		result = (float) Math.sqrt(result);
		return result ;
	}
	
	
	/* en supposant que les sous-listes sont de longueur 3 : [x,y,z] ; � modifier sinon */
	
	public float distance_tchebychev(float[] chaine1, float[] chaine2) {
		return (float) max(Math.abs(chaine2[0]-chaine1[0]) , Math.abs(chaine2[1]-chaine1[1]), Math.abs(chaine2[2]-chaine1[2]));
	}
	
	public float distance(float[] chaine1, float[] chaine2) {
		return distance_euclid(chaine1, chaine2);
		/* return distance_tchebychev(chaine1, chaine2) */
	}
	
	public float DTWDistance()
	{
		float[][] dtw = new float[s.length][t.length];
		for (int i = 0; i < s.length; i++)
		{
			dtw[i][0] = Integer.MAX_VALUE;
		}
		for (int j = 0; j < t.length; j++)
		{
			dtw[0][j] = Integer.MAX_VALUE;
		}
		dtw[0][0] = 0;
		
		float cost;
		dtw[0][0]=distance(s[0], t[0]);
		dtw[1][0]=distance(s[1], t[0]) +dtw[0][0];
		dtw[0][1]=distance(s[0], t[1]) +dtw[0][0];
		for (int i = 1; i < s.length; i++)
		{
			for (int j = 1; j < t.length; j++)
			{
				cost = distance(s[i], t[j]);
				dtw[i][j] = (float) (cost + min(dtw[i-1][j], dtw[i][j-1], dtw[i-1][j-1]));
			}
		}
		return dtw[s.length-1][t.length-1];
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
	
	/* Fonction n�cessaire afin de pouvoir comparer 2 cha�nes dont les coordonn�es sont prises aux m�mes instants
	 * R�alis�e� l'aide d'une interpolation lin�aire */
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
