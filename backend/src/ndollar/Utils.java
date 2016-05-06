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
		

		// new scaling methods rewritten by Lisa as of 8/9/2009 from input from Jake
		public static Vector<PointR> Scale(Vector<PointR> pts, double oneDRatio,
				SizeR size) // scales the oriented bbox based on 1D or 2D
		{
			if (NDollarParameters.getInstance().UseUniformScaling) // scale to a
																	// uniform
																	// circle
			{
				// do new thing
				PointR centroid = UtilsAncien.Centroid(pts);
				double radiusSquared = 1.0d;
				for (PointR point : pts) {
					double distanceSquared = Math.pow((centroid.X - point.X), 2.0)
							+ Math.pow((centroid.Y - point.Y), 2.0);
					if (distanceSquared > radiusSquared)
						radiusSquared = distanceSquared;
				}

				double factor = size.getWidth() / Math.sqrt(radiusSquared);// Assume
																			// that
																			// size
																			// is a
																			// square
																			// and
																			// arbitrarily
																			// select
																			// width
				// this could also be replaced with a constant value (250?)

				Vector<PointR> scaledPts = new Vector<PointR>();
				for (int i = 0; i < pts.size(); i++) {
					PointR p = new PointR((PointR) pts.elementAt(i));
					p.X *= factor;
					p.Y *= factor;
					scaledPts.add(p);
				}
				return scaledPts;
			} else // do old thing
			{
				return UtilsAncien.ScaleByDimension(pts, oneDRatio, size);
			}
		}
		

		// translates the points so that their centroid lies at 'toPt'
		public static Vector<PointR> TranslateCentroidTo(Vector<PointR> points,
				PointR toPt) {
			Vector<PointR> newPoints = new Vector<PointR>(points.size());
			PointR centroid = Centroid(points);
			for (int i = 0; i < points.size(); i++) {
				PointR p = (PointR) points.elementAt(i);
				p.X += (toPt.X - centroid.X);
				p.Y += (toPt.Y - centroid.Y);
				newPoints.add(p);
			}
			return newPoints;
		}
		

		// compute the centroid of the points given
		public static PointR Centroid(Vector<PointR> points) {
			double xsum = 0.0;
			double ysum = 0.0;

			for (PointR p : points) {
				xsum += p.X;
				ysum += p.Y;
			}
			return new PointR(xsum / points.size(), ysum / points.size());
		}
		
		// Calculate the angle from the initial point of the array of Points
		// (points.elementAt(0))
		// to points[index].
		//
		// Returns this angle represented by a unit vector (stored in a Point).
		//
		// **This is used in Gesture.cs:Gesture() to compute the start angle in
		// support
		// of the optimization to not compare candidates to templates whose start
		// angles
		// are widely different. Lisa 8/8/2009
		public static PointR CalcStartUnitVector(Vector<PointR> points, int index) {
			// v is the vector from points.elementAt(0) to points[index]
			PointR v = new PointR(((PointR) points.elementAt(index)).X
					- ((PointR) points.elementAt(0)).X,
					((PointR) points.elementAt(index)).Y
							- ((PointR) points.elementAt(0)).Y);
			// len is the length of vector v
			double len = Math.sqrt(v.X * v.X + v.Y * v.Y);
			// the unit vector representing the angle between points.elementAt(0)
			// and points[index]
			// is the vector v divided by its length len
			// TODO: does there need to be a divide by zero check?
			return new PointR(v.X / len, v.Y / len);
		}
		

		public static double round(double x, int digits) {
			return (double) Math.round(x * 10 * (digits)) / (10 * digits);
		}


		public static double Rad2Deg(double rad) {
			return (rad * 180d / Math.PI);
		}
		
		public static double Deg2Rad(double deg) {
			return (deg * Math.PI / 180d);
		}
		

		// rotate the points by the given radians about their centroid
		public static Vector<PointR> RotateByRadians(Vector<PointR> points,
				double radians) {
			Vector<PointR> newPoints = new Vector<PointR>(points.size());
			PointR c = Centroid(points);

			double cos = Math.cos(radians);
			double sin = Math.sin(radians);

			double cx = c.X;
			double cy = c.Y;

			for (int i = 0; i < points.size(); i++) {
				PointR p = (PointR) points.elementAt(i);

				double dx = p.X - cx;
				double dy = p.Y - cy;

				PointR q = new PointR();
				q.X = dx * cos - dy * sin + cx;
				q.Y = dx * sin + dy * cos + cy;

				newPoints.add(q);
			}
			return newPoints;
		}
		

		// will return result in radians
		public static double AngleBetweenUnitVectors(PointR v1, PointR v2) // gives
																			// acute
																			// angle
																			// between
																			// unit
																			// vectors
																			// from
																			// (0,0)
																			// to
																			// v1,
																			// and
																			// (0,0)
																			// to v2
		{
			// changed this method on 9/28/2009, Lisa
			double test = v1.X * v2.X + v1.Y * v2.Y; // arc cosine of the vector dot
														// product
			// sometimes these two cases can happen because of rounding error in the
			// dot product calculation
			if (test < -1.0)
				test = -1.0; // truncate rounding errors
			if (test > 1.0)
				test = 1.0; // truncate rounding errors
			return Math.acos(test);
		}
}
