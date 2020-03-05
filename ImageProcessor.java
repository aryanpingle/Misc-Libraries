import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class ImageProcessor extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
    static int fw = 750, fh = 750;
    static boolean SAVE_MODE = false;
    static String filepath = "images/Art1.png";

    static int mx, my;

    // static Color c1 = new Color();

    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        
        f.setSize(fw, fh);
        f.setLocationRelativeTo(null);
        
        f.add(new ImageProcessor());
        
        f.setVisible(true);
    }

    public ImageProcessor()
    {
        setBackground(new Color(0, 0, 0, 0));

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        requestFocus(true);
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        if(SAVE_MODE)
        {
            BufferedImage bi = new BufferedImage(fw, fh, BufferedImage.TYPE_INT_ARGB);
            
            Graphics g2 = bi.getGraphics();
            
            draw(g2);
            
            try
            {
                ImageIO.write(bi, "PNG", new File(filepath));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            SAVE_MODE = false;
        }
        else
        {
            draw(g);
        }
        
        repaint();
    }
    
    public void draw(Graphics g)
    {
        for(int i = 0; i < fw; i++)
        {
            for(int j = 0; j < fh; j++)
            {
                int red = 0;
                int green = 0;
                int blue = 0;

                // double val = f(i);
                // double val = f(i, j);

                g.setColor(new Color(red, green, blue));
                g.fillRect(i, j, 1, 1);
            }
        }
    }

    public static double f(double x)
    {
        return x;
    }

    public static double f(double x, double y)
    {
        return x+y;
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
        else if(key == KeyEvent.VK_ENTER)
        {
            SAVE_MODE = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }
    
    @Override
    public void mouseEntered(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }
    
    @Override
    public void mouseExited(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        mx = e.getX();
        my = e.getY();
    }
}
