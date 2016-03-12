package classification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MovementSerializer {

	public void serialize(Move movement, String path)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(movement);
			oos.close();
			fos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
