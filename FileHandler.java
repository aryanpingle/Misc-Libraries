import java.io.*;

public class FileHandler
{
    public static int compareFiles(String f1, String f2) throws Exception
    {
        try
        {
            FileReader fr1 = new FileReader(f1);
            FileReader fr2 = new FileReader(f2);
            BufferedReader br1 = new BufferedReader(fr1);
            BufferedReader br2 = new BufferedReader(fr2);
            int line = 1;
            String l1 = "";
            String l2 = "";

            while((l1=br1.readLine())!=null && (l2=br2.readLine())!=null)
            {
                if(!l1.equalsIgnoreCase(l2))
                {
                    System.err.println("Difference found!\nFile '"+f1+"', line "+line+": '"+l1+"'\nFile '"+f2+"', line "+line+": '"+l2+"'");
                    br1.close();
                    br2.close();
                    return l2.compareTo(l1);
                }
                line++;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getSourceCode(String filename)
    {
        String src = "";
        try
        {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            String n = "";
            while((n=br.readLine()) != null)
            {
                src += n+"\n";
            }
            src = src.substring(0, src.length()-1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            return src;
        }
    }

    public static void deleteFile(String filename)
    {
        try
        {
            File f = new File(filename);
            f.delete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String readFile(String s) throws Exception
    {
        FileReader f = new FileReader(s);
        BufferedReader br = new BufferedReader(f);

        String str = null, sss="";
        while((str=br.readLine())!=null)
        {
            sss += str+"\n";
        }
        br.close();

        return sss;
    }

    public static void overwriteTextFile(String filename, String text) throws Exception
    {
        FileWriter fw = new FileWriter(filename, false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        pw.print(text);

        pw.close();
    }

    /**
     * Inserts `text` at line number `line` in file `filename`
     * @param line Starts from 1, and if it exceeds the total number of lines, it appends the file
     */
    public static void editTextFile(String filename, String text, int line)throws Exception
    {
        FileWriter fw = new FileWriter(filename, false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        pw.print(text);

        pw.close();
    }

    public static void appendTextFile(String filename, String text)throws Exception
    {
        FileWriter fw = new FileWriter(filename, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        pw.print(text);

        pw.close();
    }

    static String readBinaryFile(String filename)throws Exception
    {
        FileInputStream fis = new FileInputStream(filename);
        DataInputStream dis = new DataInputStream(fis);

        String n = "";
        try
        {
            n += dis.readUTF();
        }
        catch(EOFException e)
        {
            System.err.println("File '"+filename+"' has been read");
        }

        dis.close();

        return n;
    }

    static void overWriteBinaryFile(String filename, String text)throws Exception
    {
        FileOutputStream fos = new FileOutputStream(filename);
        DataOutputStream dos = new DataOutputStream(fos);

        dos.writeUTF(text);

        dos.close();
    }
}
