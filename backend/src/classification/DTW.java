package classification;

public class DTW {
	double[][] s;
	double[][] t;
	
	/* voir l'attribut comme une suite de coordonnées, par exemple [[x1,y1], [x2,y2],...] */
	
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
	
	
	/* en supposant que les sous-listes sont de longueur 2 : [x,y] ; à modifier sinon */
	
	public double distance_tchebychev(double[] chaine1, double[] chaine2) {
		return Math.max(Math.abs(chaine2[0]-chaine1[0]) , Math.abs(chaine2[1]-chaine1[1]));
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
}
