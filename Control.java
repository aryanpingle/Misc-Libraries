import java.io.*;
import java.awt.geom.*;
import java.awt.*;

public class Control
{
    /**
     * Returns the area object present in the given file
     * <p>Separates the .dat file and the .txt. file form each other intrinsically, so you don't have to worry about that ;)</p>
     */

    public static Area getArea(String filepath)
    {
        Area area = null;

        if(filepath.endsWith(".dat"))
        {
            try
            {
                area = loadAreaFromBinaryFile(filepath);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if(filepath.endsWith(".txt"))
        {
            try
            {
                area = loadAreaFromTextFile(filepath);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return area;
    }

    /**
     * Saves the area object to the given filepath
     */

    public static void saveArea(Area area, String filepath)
    {
        try
        {
            writeArea(area, filepath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static Object getObjectFromBinaryFile(String filepath) throws Exception
    {
        Object obj = null;

        ObjectInputStream ois = new ObjectInputStream(new DataInputStream(new FileInputStream(filepath)));

        obj = ois.readObject();

        ois.close();

        return obj;
    }

    private static Object getObjectFromTextFile(String filepath) throws Exception
    {
        Object obj = null;

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));

        obj = ois.readObject();

        ois.close();

        return obj;
    }

    private static void writeArea(Area a, String filepath)throws Exception
    {
        ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(new File(filepath)));

        ois.writeObject(AffineTransform.getTranslateInstance(0, 0).createTransformedShape(a));

        ois.close();
    }

    private static Area loadAreaFromBinaryFile(String filepath)throws Exception
    {
        Area a = new Area((Shape)getObjectFromBinaryFile(filepath));

        return a;
    }

    private static Area loadAreaFromTextFile(String filepath)throws Exception
    {
        Area a = new Area((Shape)getObjectFromTextFile(filepath));

        return a;
    }
}
