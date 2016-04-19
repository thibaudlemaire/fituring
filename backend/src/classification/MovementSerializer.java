package classification;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
	
	public Move deSerialize(String path)
	{
		Move mvt;
        try
        {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            mvt = (Move) ois.readObject();
            ois.close();
            fis.close();
         }catch(IOException ioe){
             ioe.printStackTrace();
             return null;
          }catch(ClassNotFoundException c){
             System.out.println("Class not found");
             c.printStackTrace();
             return null;
          }
        return mvt;
	}
}
