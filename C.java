import java.awt.*;
import javax.imageio.*;
import java.io.*;
import java.awt.image.*;

public class C
{
    public static void main(String[] args)
    {
    }

    /**
     * @param c The reference color
     * @param inc The number of rgb pixels which <b>c</b> is to be shifted by towards <b>Color.WHITE</b>
     * @return A brand new color object which is the result of every rgb value of <b>c</b> shifted inc towards <b>Color.WHITE</b>
     */
    
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

    /**
     * @param c The reference color
     * @param inc The number of rgb pixels which <b>c</b> is to be shifted by towards <b>Color.BLACK</b>
     * @return A brand new color object which is the result of every rgb value of <b>c</b> shifted inc towards <b>Color.BLACK</b>
     */
    
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
     * Used in random, miscellaneous functions where you wanna shift a color <b>c</b> towards another color <b>t</b> by <b>percent</b> percent
     * @param c The color which basically acts as a start point
     * @param t The color to be reached
     * @param percent A value between 0 and 1 which represents the percentage value
     * @return A brand new color object
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
     * Used in loops where you wanna gradually make a color <b>c</b> shift towards <b>end</b> from its (supposedly initial value of <b>start</b>) by a fixed percentage
     * 
     * @param c The color to be shifted
     * @param start The assumed initial value of <b>c</b> before any shifting
     * @param end The final Color to be reached
     * @param percent A value between 0 and 1 which represents the percentage value
     * @return A brand new Color object which is <b>percent</b> percent of the difference between <b>start</b> and <b>end</b>  towards <b>end</b> from <b>c</b>
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

    /**
     * Returns the <b>inverted</b> Color object for <b>c</b>
     * @param c The Color whose inverted color object is to be returned
     * @return A brand new Color object whose value is (255-r, 255-g, 255-b)
     */
    
    public static Color getInvertedColor(Color c)
    {
        return new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue());
    }

    /**
     * @param hue The r/g/b value which is to be normalized
     * @return Returns the value of <b>hue</b> constrained to 0 - 255 (inclusive)
     */
    
    public static int getNormalizedPixel(int hue)
    {
        if(hue > 255) return 255;
        if(hue < 0) return 0;
        return hue;
    }
    
    public static int sign(double n)
    {
        return n==0?0:n>0?1:-1;
    }

    /**
     * @param imagepath The path of the image including any and all sub folders and the extension
     * @return A color object which is the average of all the pixel colors of the image
     */
    
    public static Color getAvgColor(String imagepath)
    {
        BufferedImage bi = null;
        try
        {
            bi = ImageIO.read(new File(imagepath));
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
    
    /**
     * @param colors All the colors for which the average is to be returned
     */
    
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

    /**
     * Converts an image to a Single Dimensional Array of its pixel colors<p></p>
     * To access the color at the coordinates (x, y) use: <code>pixels[x+y*image.width]</code>
     * 
     * @param imagepath The path of the image including any and all sub folders and the extension
     * @return A Single Dimensional Array
     */

    public static Color[] getPixelsInSDA(String imagepath)
    {
        BufferedImage bi = ImageIO.read(new File(imagepath));

        int w = bi.getWidth();
        int h = bi.getHeight();

        Color[] pixels = new Color[w * h];

        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                pixels[i + j*w] = new Color(bi.getRGB(i, j));
            }
        }

        return pixels;
    }

    /**
     * Converts an image to a Double Dimensional Array of its pixel colors<p></p>
     * To access the color at the coordinates (x, y) use: <code>pixels[x][y]</code>
     * 
     * @param imagepath The path of the image including any and all sub folders and the extension
     * @return A Double Dimensional Array
     */

    public static Color[][] getPixelsInDDA(String imagepath)
    {
        BufferedImage bi = ImageIO.read(new File(imagepath));

        int w = bi.getWidth();
        int h = bi.getHeight();

        Color[][] pixels = new Color[w][h];

        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                pixels[i][j] = new Color(bi.getRGB(i, j));
            }
        }

        return pixels;
    }
}
