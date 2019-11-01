import java.io.*;
import java.util.*;

public class G
{
    public static double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }
    
    public static double constrain(double val, double lb, double ub)
    {
        return val>ub?ub:val<lb?lb:val;
    }
}
