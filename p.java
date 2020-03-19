import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;

public class p implements ActionListener, MouseListener, KeyListener
{
    static double ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    static double ScreenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    static Robot r;

    static Color BROWN = new Color(150, 75, 0);
    static Color VIOLET = new Color(127, 0, 255);

    public static double constrain(double val, double lb, double ub)
    {
        return val>ub?ub:(val<lb?lb:val);
    }

    /**
     * Cycles the value `val` from lb to ub-1, with an increment of 1
     */

    public static int cycle(int val, int lb, int ub)
    {
        return (val<ub-1)?(val+1):(lb);
    }

    public static int cycle(int val, int lb, int ub, int increment)
    {
        return (val<ub-increment)?(val+increment):(lb);
    }
    
    public static boolean isVowel(char c)
    {
        c = Character.toLowerCase(c);
        return c=='a'||c=='e'||c=='i'||c=='o'||c=='u';
    }
    
    public static int timesFound(String search, String text)
    {
        int k = 0;
        int s = 0;
        while(true)
        {
            int s1 = text.substring(s).indexOf(search);
            if(s1 != -1)
            {
                k++;
                s = s+s1+1;
            }
            else break;
            if(s >= text.length()) break;
        }
        return k;
    }

    static int[] convertToSDA(int[][] arr)
    {
        int[] temp = new int[arr.length*arr.length];
        int c = 0;

        for(int i = 0; i < arr.length; i++)
        {
            for(int j = 0; j < arr.length; j++)
            {
                temp[c++] = arr[i][j];
            }
        }
        
        return temp;
    }
    
    static char[] convertToSDA(char[][] arr)
    {
        char[] temp = new char[arr.length*arr.length];
        int c = 0;

        for(int i = 0; i < arr.length; i++)
        {
            for(int j = 0; j < arr.length; j++)
            {
                temp[c++] = arr[i][j];
            }
        }
        
        return temp;
    }

    public static Polygon getPolygon(int ox, int oy, int sides, int radius)
    {
        int[] x = new int[sides];
        int[] y = new int[sides];

        double angle = -90;

        for(int i = 0; i < sides; i++)
        {
            x[i] = ox+(int)(radius*Math.cos(Math.toRadians(angle)));
            y[i] = oy+(int)(radius*Math.sin(Math.toRadians(angle)));

            angle += 360.0/sides;
        }

        Polygon p = new Polygon(x, y, sides);

        return p;
    }

    public static Polygon getPolygon(int origin_x, int origin_y, int sides, int radius, double rotation)
    {
        int[] x = new int[sides];
        int[] y = new int[sides];

        double angle = -90;
        angle += rotation;

        for(int i = 0; i < sides; i++)
        {
            x[i] = origin_x+(int)(radius*Math.cos(Math.toRadians(angle)));
            y[i] = origin_y+(int)(radius*Math.sin(Math.toRadians(angle)));

            angle += 360.0/sides;
        }

        Polygon p = new Polygon(x, y, sides);

        return p;
    }

    static int sign(double n)
    {
        return n>0?1:(n<0?-1:0);
    }

    static double map(double value, double x1, double x2, double y1, double y2)
    {
        double resultperinput = (y2-y1)/(x2-x1);

        return y1+((value-x1)*resultperinput);
    }

    /**
     * Returns a VERIFIED angle to (x, y) keeping (0, 0) as the origin - in RADIANS
     */
    static double getAngleInRadians(double x, double y)
    {
        return Math.atan2(y, x);
    }

    /**
     * Returns a VERIFIED angle to (x2, y2) keeping (x1, y1) as the origin - in DEGREES
     * <p>
     * Consider using <b>getAngleInRadians()</b> for the same, if you wish to use the cos and sine components
     * </p>
     */
    static double getAngleInRadians(double x1, double y1, double x2, double y2)
    {
        return Math.atan2(y2-y1, x2-x1);
    }

    /**
     * Returns a VERIFIED angle to (x, y) keeping (0, 0) as the origin - in DEGREES
     * <p>
     * Consider using <b>getAngleInRadians()</b> for the same, if you wish to use the cos and sine components
     * </p>
     */
    static double getAngleInDegrees(double x, double y)
    {
        if(y >= 0 && x >= 0)
            return Math.toDegrees(getAngleInRadians(x, y));
        else return 360+Math.toDegrees(getAngleInRadians(x, y));
    }

    /**
     * Returns a VERIFIED angle to (x2, y2) keeping (x1, y1) as the origin - in RADIANS
     */
    static double getAngleInDegrees(double x1, double y1, double x2, double y2)
    {
        double y = y2-y1;
        double x = x2-x1;
        return getAngleInDegrees(x, y);
    }

    static double increaseValue(double n, double amt)
    {
        if(n!=0)
        {
            return sign(n)*(Math.abs(n)+amt);
        }
        return 0;
    }

    static int getReverse(int n)
    {
        int rev = 0;
        while(n != 0)
        {
            rev = rev*10 + n%10;
            n /= 10;
        }
        return rev;
    }

    static void deleteFileWithoutConfirmation(String filename)
    {
        try
        {
            new File(filename).delete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            System.err.println("File '"+filename+"' has been deleted");
        }
    }

    static double getDistance(double x, double y)
    {
        return Math.sqrt(x*x+y*y);
    }

    static double getDistance(double x, double y, double x2, double y2)
    {
        return Math.sqrt(((x-x2)*(x-x2))+((y-y2)*(y-y2)));
    }

    /**
     * Delays execution for `delay` milliseconds
     */
    public static void pause(int delay)
    {
        try
        {
            Thread.sleep(delay);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void createRobot()
    {
        try
        {
            r = new Robot();
        }
        catch(Exception e)
        {
        }
    }

    static String[] getRegexMatches(String regex, String main)
    {
        Pattern pat = Pattern.compile(regex);
        Matcher m = pat.matcher(main);
        Matcher m2 = pat.matcher(main);
        int n = 0;
        while(m2.find())
        {
            n++;
        }
        String[] str = new String[n];
        n = 0;
        while(m.find())
        {
            str[n++] = m.group();
        }
        return str;
    }

    static int getSumOfDigits(int n)
    {
        int sum = 0;
        if(n<0)
        {
            System.err.println(n+" is negative, so beware");
        }
        while(n!=0)
        {
            sum += n%10;
            n/=10;
        }
        return sum;
    }

    static long getFactorial(long n)
    {
        long f = 1;
        for(int i = 1; i <= n; i++)
        {
            f *= i;
        }
        if(n < 0)
            return -1;
        else
            return f;
    }

    /**
     * Draws coordinate axes with the Graphics object `g` at `offsetX` from the left of the PaintComponent, 
     * and `offsetY` from the top
     * <li>
     * The grids are drawn with a height and width of 5000px
     * </li>
     */
    static void drawCoOrdinateAxes(Graphics g, int offsetX, int offsetY)
    {
        g.drawLine(offsetX, 0, offsetX, 5000); // Y Axis
        g.drawLine(0, offsetY, 5000, offsetY); // X Axis
    }

    /**
     * Returns a random number between `lowerlimit` (included) and `upperlimit` (excluded)
     */
    static double getRandom(double lowerlimit, double upperlimit)
    {
        return Math.random()*(upperlimit-lowerlimit)+lowerlimit;
    }

    static String getLocalTime()
    {
        ln(Calendar.getInstance().get(Calendar.MILLISECOND));
        return ""+getLocalTime();
    }

    public static Robot getRobot()
    {
        Robot r = null;
        try
        {
            r = new Robot();
        }
        catch(Exception exc)
        {
        }
        return r;
    }

    static void show()
    {
        System.out.print("");
    }

    public static void clr()
    {
        System.out.print("\f");
    }

    public static void ln()
    {
        System.out.println();
    }

    public static void ln(Object str)
    {
        System.out.println(str);
    }

    public static void ln(int n)
    {
        System.out.println(n);
    }

    public static void ln(boolean n)
    {
        System.out.println(n);
    }

    public static void ln(char n)
    {
        System.out.println(n);
    }

    public static void ln(short n)
    {
        System.out.println(n);
    }

    public static void ln(byte n)
    {
        System.out.println(n);
    }

    public static void ln(long n)
    {
        System.out.println(n);
    }

    public static void ln(float n)
    {
        System.out.println(n);
    }

    public static void ln(double n)
    {
        System.out.println(n);
    }

    public static void ln(double ... n)
    {
        System.out.println(n);
    }

    public static void n(Object str)
    {
        System.out.print(str);
    }

    public static void n(int n)
    {
        System.out.print(n);
    }

    public static void n(char n)
    {
        System.out.print(n);
    }

    public static void n(short n)
    {
        System.out.print(n);
    }

    public static void n(byte n)
    {
        System.out.print(n);
    }

    public static void n(long n)
    {
        System.out.print(n);
    }

    public static void n(float n)
    {
        System.out.print(n);
    }

    public static void n(double n)
    {
        System.out.print(n);
    }

    public static void printArray(int[] a)
    {
        for(int n: a)
        {
            System.out.print(n+"\t");
        }
    }

    public static void printArray(char[] a)
    {
        for(char n: a)
        {
            System.out.println(n);
        }
    }

    public static void printArray(short[] a)
    {
        for(short n: a)
        {
            System.out.println(n);
        }
    }

    public static void printArray(long[] a)
    {
        for(long n: a)
        {
            System.out.println(n);
        }
    }

    public static void printArray(float[] a)
    {
        for(float n: a)
        {
            System.out.println(n);
        }
    }

    public static void printArray(double[] a)
    {
        for(double n: a)
        {
            System.out.println(n);
        }
    }

    public static void printArray(byte[] a)
    {
        for(byte n: a)
        {
            System.out.println(n);
        }
    }

    public static void printArray(String[] a)
    {
        for(String n: a)
        {
            System.out.println(n);
        }
    }

    public static void bubbleSort(int[] a)
    {
        for(int i = 0; i < a.length-1; i++)
        {
            for(int j = 0; j < a.length-1-i; j++)
            {
                if(a[j] > a[j+1])
                {
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
    }
}