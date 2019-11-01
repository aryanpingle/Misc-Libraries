import java.awt.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class C
{
    public static void main(String[] args)
    {
    }
    
    public static Color getWhiterColor(Color c, int inc)
    {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        
        r += inc;
        if(r > 255) r = 255;
        g += inc;
        if(g > 255) g = 255;
        b += inc;
        if(b > 255) b = 255;
        
        return new Color(r, g, b);
    }
    
    public static Color getBlackerColor(Color c, int inc)
    {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        
        r -= inc;
        if(r < 0) r = 0;
        g -= inc;
        if(g < 0) g = 0;
        b -= inc;
        if(b < 0) b = 0;
        
        return new Color(r, g, b);
    }
    
    /**
    * Returns a color `percent` percent towards the color `t`
    * `percent` can be between 0 and 1
    */
    public static Color getColorTowards(Color c, Color t, double percent)
    {
        int r1 = c.getRed();
        int g1 = c.getGreen();
        int b1 = c.getBlue();
        
        int r2 = t.getRed();
        int g2 = t.getGreen();
        int b2 = t.getBlue();
        
        double dr = r2-r1;
        dr *= percent;
        double dg = g2-g1;
        dg *= percent;
        double db = b2-b1;
        db *= percent;
        
        r1 += dr;
        g1 += dg;
        b1 += db;
        
        return new Color(r1, g1, b1);
    }
    
    /**
    * Used when in a loop you want to increase the percentage towards
    * (REFER PAUSE)
    */
    public static Color getColorTowardsWithSection(Color c, Color start, Color end, double percent)
    {
        int r1 = c.getRed();
        int g1 = c.getGreen();
        int b1 = c.getBlue();
        
        int r2 = end.getRed();
        int g2 = end.getGreen();
        int b2 = end.getBlue();
        
        int r3 = start.getRed();
        int g3 = start.getGreen();
        int b3 = start.getBlue();
        
        double dr = r2-r3;
        dr *= percent;
        double dg = g2-g3;
        dg *= percent;
        double db = b2-b3;
        db *= percent;
        
        if(sign(r1+dr-r2) != sign(r1-r2))
        {
            dr = 0;
            r1 = r2;
        }
        if(sign(g1+dg-g2) != sign(g1-g2))
        {
            dg = 0;
            g1 = g2;
        }
        if(sign(b1+db-b2) != sign(b1-b2))
        {
            db = 0;
            b1 = b2;
        }
        
        r1 += dr;
        g1 += dg;
        b1 += db;
        
        r1 = getNormalizedPixel(r1);
        g1 = getNormalizedPixel(g1);
        b1 = getNormalizedPixel(b1);
        
        return new Color(r1, g1, b1);
    }
    
    public static Color getInvertedColor(Color c)
    {
        return new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue());
    }
    
    public static int getNormalizedPixel(int c)
    {
        if(c > 255) return 255;
        if(c < 0) return 0;
        return c;
    }
    
    public static int sign(double n)
    {
        return n==0?0:n>0?1:-1;
    }
    
    public static Color getAvgColor(Image image)
    {
        BufferedImage bi = null;
        try
        {
            bi = ImageIO.read(new File("Game/images/Scary.png"));
        }
        catch(Exception e){e.printStackTrace();}
        
        double red = 0, green = 0, blue = 0;
        
        for(int i = 0; i < bi.getWidth(); i++)
        {
            for(int j = 0; j < bi.getHeight(); j++)
            {
                Color c = new Color(bi.getRGB(i, j));
                red += c.getRed()/255.0;
                green += c.getGreen()/255.0;
                blue += c.getBlue()/255.0;
            }
        }
        
        int total = bi.getHeight()*bi.getWidth();;
        red = red*255.0/total;
        green = green*255.0/total;
        blue = blue*255.0/total;
        
        return new Color((int)(red), (int)(green), (int)(blue));
    }
    
    static int normal(int hue)
    {
        return hue<0?0:hue>255?255:hue;
    }
    
    static Color getAverageColor(Color... colors)
    {
        Color result = null;
        
        double R = 0, G = 0, B = 0;
        
        for(Color c: colors)
        {
            R += c.getRed()/255.0;
            G += c.getGreen()/255.0;
            B += c.getBlue()/255.0;
        }

        int total = colors.length;
        
        result = new Color((int)(R*255/total), (int)(G*255/total), (int)(B*255/total));
        
        return result;
    }
}