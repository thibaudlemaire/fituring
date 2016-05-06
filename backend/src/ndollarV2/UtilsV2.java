package ndollarV2;

import java.util.Vector;

public class UtilsV2 {
	
	//Suppose le meme nombre de points
		public static double PathDistance(Vector<PointR> path1, Vector<PointR> path2) {
			double distance = 0;
			for (int i = 0; i < path1.size(); i++) {
				distance += Distance((PointR) path1.elementAt(i),
						(PointR) path2.elementAt(i));
			}
			return distance / path1.size();
		}

		public static double Distance(PointR p1, PointR p2) {
			double dx = p2.X - p1.X;
			double dy = p2.Y - p1.Y;
			return Math.sqrt(dx * dx + dy * dy);
		}

}
