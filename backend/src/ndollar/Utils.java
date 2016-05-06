package ndollar;

import java.util.Vector;
/**
 * This class contains some useful methods in many other classes
 * @author robin
 *
 */

public class Utils {
	
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
		
		//Rescaling and translating
		public static Vector<PointR> treatement(Vector<PointR> points) {

			//Rescaling
			Vector<PointR> result = UtilsAncien.Scale(new Vector<PointR>(points), NDollarRecognizerAncien._1DThreshold, NDollarRecognizerAncien.ResampleScale);

			//Translating
			result = UtilsAncien.TranslateCentroidTo(result, NDollarRecognizerAncien.ResampleOrigin);

			return result;
		}

}
