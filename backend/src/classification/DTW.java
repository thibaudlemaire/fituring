
public class DTW {
	double[] s;
	double[] t;
	
	public DTW(double[] s, double[] t)
	{
		this.s = s;
		this.t = t;
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
				cost = distance(s[i], t[j]);
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
