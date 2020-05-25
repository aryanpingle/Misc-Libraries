import java.io.*;
import java.awt.geom.*;
import java.awt.*;

import java.util.LinkedList;

public class Control
{
    public static void main(String[] args)
    {
        Main.main(args);
    }

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

    /**
     * Returns the object present in `filepath`
     * <p>Separates the .dat file and the .txt. file form each other intrinsically, so you don't have to worry about that ;)</p>
     */

    public static Object getObject(String filepath)
    {
        Object obj = null;

        if(filepath.endsWith(".dat"))
        {
            try
            {
                obj = getObjectFromBinaryFile(filepath);
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
                obj = getObjectFromTextFile(filepath);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return obj;
    }

    public static void saveObject(Object obj, String filepath)
    {
        try
        {
            writeObject(obj, filepath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void saveLinkedList(LinkedList list, String filepath)
    {
        try
        {
            writeLinkedList(list, filepath);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static LinkedList getLinkedList(String filepath)
    {
        LinkedList list = null;

        if(filepath.endsWith(".dat"))
        {
            try
            {
                list = getLinkedListFromBinaryFile(filepath);
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
                list = getLinkedListFromTextFile(filepath);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return list;
    }

    // LinkedList Handling

    private static void writeLinkedList(LinkedList list, String filepath)throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));

        for(Object o: list)
        {
            oos.writeObject(o);
        }
        
        oos.close();
    }

    private static LinkedList getLinkedListFromBinaryFile(String filepath)throws Exception
    {
        LinkedList list = new LinkedList();

        ObjectInputStream ois = new ObjectInputStream(new DataInputStream(new FileInputStream(filepath)));

        try
        {
            while(true)
            list.add(ois.readObject());
        }
        catch(EOFException e)
        {
        }

        ois.close();

        return list;
    }

    private static LinkedList getLinkedListFromTextFile(String filepath)throws Exception
    {
        LinkedList list = new LinkedList();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));

        Object o = null;
        while((o = ois.readObject()) != null)
        {
            list.add(o);
        }

        ois.close();

        return list;
    }

    // Object Handling

    private static void writeObject(Object obj, String filepath)throws IOException
    {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));

        oos.writeObject(obj);
        
        oos.close();
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

    // Area Handling

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
