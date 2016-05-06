package ndollarV2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;

import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class NDollarRecognizerV2 {
	private final static String NAMESPACE = null;
	private final static String VERSION = "1.0";

	private Hashtable<String, Multistroke> _gestures;
	
	private static final double Phi = 0.5 * (-1 + Math.sqrt(5)); // Golden Ratio
	private static final double _RotationBound = 45.0;

	public NDollarRecognizerV2() {
		_gestures = new Hashtable<String, Multistroke>(256);
	}
	
	public Hashtable<String, Multistroke> get_Gestures() {
		return _gestures;
	}

	public NBestList Recognize(Vector<PointR> points, int numStrokes) // candidate
	// points
	{
		// removed the candidate transformations by creating a Gesture here
		// of the input points
		// this helps keep the transformations done to templates and candidates
		// the same
		// and we won't have to edit code in two places
		// Lisa, 5/12/2008
		Gesture candidate = new Gesture(points);
		NBestList nbest = new NBestList();

		// added to check how much savings we are getting out of the
		// Utils.AngleBetwenVUnitVectors() check
		// Lisa 8/9/2009
		int totalComparisons = 0;
		int actualComparisons = 0;

		// we have to compare the current gesture to all candidates,
		// each subgesture in our set of Multistrokes
		// Lisa 12/22/2007
		for (Multistroke ms : _gestures.values()) {
			// added as of 8/9/2009
			// optional -- only attempt match when number of strokes is same
			if (!NDollarParameters.getInstance().MatchOnlyIfSameNumberOfStrokes
					|| numStrokes == ms.NumStrokes) {

				NBestList thisMSnbest = new NBestList(); // store the best
				// Vector for just
				// this MS
				for (Gesture p : ms.Gestures) {
					totalComparisons++;
					// added as of 8/9/2009
					if (!NDollarParameters.getInstance().DoStartAngleComparison
							|| (NDollarParameters.getInstance().DoStartAngleComparison && Utils
									.AngleBetweenUnitVectors(
											candidate.StartUnitVector,
											p.StartUnitVector) <= NDollarParameters
											.getInstance().StartAngleThreshold)) {
						actualComparisons++;

						double score = -1;
						double[] best = new double[] { -1, -1, -1 };

						best = GoldenSectionSearch(candidate.Points, // to
								// rotate
								p.Points, // to match
								Utils.Deg2Rad(-_RotationBound), // lbound,
								// Lisa
								// 1/2/2008
								Utils.Deg2Rad(+_RotationBound), // ubound,
								// Lisa
								// 1/2/2008
								Utils.Deg2Rad(2.0) // threshold
								);

						// keep track of what subgesture was best match for this
						// multistroke
						// and only add that particular template's score to the
						// nbest Vector
						// Lisa 12/22/2007
						thisMSnbest.AddResult(p.Name, score, best[0], best[1]); // name,
						// score,
						// distance,
						// angle
					}
				}
				thisMSnbest.SortDescending();
				// add the one that was best of those subgestures
				// these properties return the property of the top result
				// Lisa 12/22/2007
				nbest.AddResult(thisMSnbest.getName(), thisMSnbest.getScore(),
						thisMSnbest.getDistance(), thisMSnbest.getAngle()); // name,
				// score,
				// distance,
				// angle
			}
		}
		nbest.SortDescending(); // sort so that nbest[0] is best result
		nbest.setTotalComparisons(totalComparisons);
		nbest.setActualComparisons(actualComparisons);
		return nbest;
	}

	public boolean LoadGesture(String filename) {
		return LoadGesture(new File(filename));
	}

	static int cnt =0;
	public boolean LoadGesture(File file) {
		boolean success = true;
		MXParser reader = null;
		FileInputStream fis = null;
		try {
			reader = new MXParser();
			fis = new FileInputStream(file);
			reader.setInput(fis, "UTF-8");

			Multistroke p = ReadGesture(reader); // Lisa 1/2/2008

			// remove any with the same name and add the prototype gesture
			if (_gestures.containsKey(p.Name)){
				// jso 09/30/2011
				// do not remove but rename the multistroke - we want them all
				// (Recognizer still returns original name as it uses
				// the name of the Multistroke's OriginalGesture)
				//_gestures.remove(p.Name);		
				p.Name = (p.Name+"-"+(++cnt));
			}

			// _gestures now contains Multistrokes, not just Gestures
			// Lisa 12/21/2007
			System.out.println("add "+p.Name);
			_gestures.put(p.Name, p);
			if (fis != null) {
				fis.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			success = false;
		} finally {

		}
		return success;
	}

	// assumes the reader has been just moved to the head of the content.
	// changed this to return a Multistroke so we can change the order of
	// pre-processing.
	// Lisa 1/2/2008
	private Multistroke ReadGesture(XmlPullParser reader) {
		String name = "", user = "", speed = "";
		Vector<PointR> points = new Vector<PointR>();
		Vector<Vector<PointR>> strokes = new Vector<Vector<PointR>>();
		try {
			int next = reader.next();
			while (next != XmlPullParser.END_DOCUMENT) {

				if (next == XmlPullParser.START_TAG
						&& reader.getName().equals("Gesture")) {
					for (int i = 0; i < reader.getAttributeCount(); ++i) {
						if (reader.getAttributeName(i).equals("Name")) {
							name = reader.getAttributeValue(i);
						} else if (reader.getAttributeName(i).equals("Subject")) {
							user = reader.getAttributeValue(i);
						} else if (reader.getAttributeName(i).equals("Speed")) {
							speed = reader.getAttributeValue(i);
						}
					}
				} else if (next == XmlPullParser.START_TAG
						&& reader.getName().equals("Point")) {
					PointR p = new PointR();
					for (int i = 0; i < reader.getAttributeCount(); ++i) {
						if (reader.getAttributeName(i).equals("X")) {
							p.X = Double.parseDouble(reader
									.getAttributeValue(i));
						} else if (reader.getAttributeName(i).equals("Y")) {
							p.Y = Double.parseDouble(reader
									.getAttributeValue(i));
						} else if (reader.getAttributeName(i).equals("T")) {
							p.T = Integer.parseInt(reader.getAttributeValue(i));
						}
					}
					points.add(p);
				} else if (next == XmlPullParser.START_TAG
						&& reader.getName().equals("Stroke")) {
					{
						// set up stroke index for the beginning of this stroke
						strokes.add(new Vector<PointR>(points));
						points = new Vector<PointR>();
					}
				}
				next = reader.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// add last stroke size
		strokes.add(new Vector<PointR>(points));
		return new Multistroke(name, user, speed, strokes); // keep each stroke
		// separate until
		// we're done
		// pre-processing
	}
	
	private double[] GoldenSectionSearch(Vector<PointR> pts1,
			Vector<PointR> pts2, double a, double b, double threshold) {
		double x1 = Phi * a + (1 - Phi) * b;
		Vector<PointR> newPoints = Utils.RotateByRadians(pts1, x1);
		double fx1 = Utils.PathDistance(newPoints, pts2);

		double x2 = (1 - Phi) * a + Phi * b;
		newPoints = Utils.RotateByRadians(pts1, x2);
		double fx2 = Utils.PathDistance(newPoints, pts2);

		double i = 2.0; // calls
		while (Math.abs(b - a) > threshold) {
			if (fx1 < fx2) {
				b = x2;
				x2 = x1;
				fx2 = fx1;
				x1 = Phi * a + (1 - Phi) * b;
				newPoints = Utils.RotateByRadians(pts1, x1);
				fx1 = Utils.PathDistance(newPoints, pts2);
			} else {
				a = x1;
				x1 = x2;
				fx1 = fx2;
				x2 = (1 - Phi) * a + Phi * b;
				newPoints = Utils.RotateByRadians(pts1, x2);
				fx2 = Utils.PathDistance(newPoints, pts2);
			}
			i++;
		}
		return new double[] { Math.min(fx1, fx2), Utils.Rad2Deg((b + a) / 2.0),
				i }; // distance, angle, calls to pathdist
	}

	
	public boolean SaveGesture(String filename, Vector<Vector<PointR>> strokes,
			Vector<Integer> numPtsInStroke) {
		// add the new prototype with the name extracted from the filename.
		String name = Gesture.ParseName(filename);

		// Lisa 1/2/2008
		Multistroke newPrototype = new Multistroke(name, "test", "test",
				strokes); // points, numPtsInStroke);

		// jso 09/30/2011
		if (_gestures.containsKey(name)){
			// do not remove but rename the multistroke - we want them all
			// (Recognizer still returns original name as it uses
			// the name of the Multistroke's OriginalGesture)
			//_gestures.remove(newPrototype.name);
			newPrototype.Name = (newPrototype.Name+"-"+(++cnt));
		}

		_gestures.put(newPrototype.Name, newPrototype);

		Vector<PointR> points = newPrototype.OriginalGesture.RawPoints;
		// figure out the duration of the gesture
		PointR p0 = points.elementAt(0);
		PointR pn = points.elementAt(points.size() - 1);

		// do the xml writing (of the raw points)
		boolean success = true;
		boolean indentation = true;
		XmlSerializer writer = null;
		OutputStreamWriter osw = null;
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
					System.getProperty(XmlPullParserFactory.PROPERTY_NAME),
					null);
			writer = factory.newSerializer();
			// save the prototype as an Xml file
			osw = new OutputStreamWriter(new FileOutputStream(filename));
			writer.setOutput(osw);
			writer.startTag(NAMESPACE, "Gesture");
			writer.attribute(NAMESPACE, "Name", name);
			writer.attribute(NAMESPACE, "Subject", "test");
			writer.attribute(NAMESPACE, "Speed", "test");
			writer.attribute(NAMESPACE, "NumPts",
					Integer.toString(points.size()));
			writer.attribute(NAMESPACE, "Milliseconds",
					Integer.toString(pn.T - p0.T));
			writer.attribute(NAMESPACE, "AppName", getClass().getName()
					+ "-java");
			writer.attribute(NAMESPACE, "AppVer", VERSION);
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			writer.attribute(NAMESPACE, "Date", dateFormat
					.format(GregorianCalendar.getInstance().getTime()));
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
			writer.attribute(NAMESPACE, "TimeOfDay", timeFormat
					.format(GregorianCalendar.getInstance().getTime()));
			if (indentation)
				writer.text("\n");
			// write out the Stroke tags, Lisa 1/2/2008
			int numStrokesWritten = 0;
			// write out the raw individual points
			// fixed to work with strokes, Lisa 8/8/2009
			for (Vector<PointR> pts : strokes) {
				writer.startTag(NAMESPACE, "Stroke");
				writer.attribute(NAMESPACE, "index",
						Integer.toString(numStrokesWritten + 1));
				if (indentation)
					writer.text("\n");
				numStrokesWritten++;
				for (PointR p : pts) {
					writer.startTag(NAMESPACE, "Point");
					writer.attribute(NAMESPACE, "X", Double.toString(p.X));
					writer.attribute(NAMESPACE, "Y", Double.toString(p.Y));
					writer.attribute(NAMESPACE, "T", Integer.toString(p.T));
					writer.endTag(NAMESPACE, "Point");
					if (indentation)
						writer.text("\n");
				}
				// write the Stroke tags, Lisa 1/2/2008
				writer.endTag(NAMESPACE, "Stroke"); // </Stroke>, I hope
				if (indentation)
					writer.text("\n");
			}
			writer.endTag(NAMESPACE, "Gesture");
			if (indentation)
				writer.text("\n");
			writer.endDocument();
			writer.flush();
			if (osw != null)
				osw.close();
		} catch (IOException xex) {
			xex.printStackTrace();
			success = false;
		} catch (Exception ex) {
			ex.printStackTrace();
			success = false;
		}
		return success; // Xml file successfully written (or not)
	}


}
