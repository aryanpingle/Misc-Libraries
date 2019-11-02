import java.util.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

@SuppressWarnings("all")
public class Snowfall extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener, ActionListener, KeyListener
{
    static class Snow
    {
        static final int maxRadius = 7;
        static final int minRadius = 3;

        static final double defaultVelocity = 2.3;

        Color color = new Color(255, 255, 255);

        Vector pos;
        Vector vel;
        Vector g;
        
        double radius;

        double angle = 0;
        
        public Snow(double x, double y, double z)
        {
            pos = new Vector(x, y);
            
            /*
            Right in front of you means z = -50, radius = 10, g = 1
            Far back is z = 50, radius = 5, g = 0.1
            */
            
            radius = p.map(z, -50, 50, maxRadius, minRadius);
            vel = new Vector();
            g = new Vector(0, p.map(z, -50, 50, 1, 0.1));
        }
        
        public void move()
        {
            pos.add(vel);
            vel.add(g);
            g.add(new Vector(p.map(Math.sin(angle), -1, 1, -0.005, 0.005), 0));

            g.add(new Vector(p.map(slider.getValue(), slider.lb, slider.ub, -0.04, 0.04), 0));

            if(pos.j > fh || pos.i < -fw/2 || pos.i > 3*fw/2)
            {
                double z = p.map(Math.random(), 0, 1, -50, 50);
                pos = new Vector(p.map(Math.random(), 0, 1, -fw/2, 3*fw/2), -10);
                radius = p.map(z, -50, 50, 10, 5);
                vel = new Vector(0, defaultVelocity);
                g = new Vector(0, p.map(z, -50, 50, 1, 0.1));
                angle = p.map(Math.random(), 0, 1, 0, Math.PI*2);
                color = C.getColorTowards(Color.WHITE, Color.BLACK, p.map(z, -50, 50, 0, 0.4));
            }

            angle += 0.2;
        }
        
        public void draw(Graphics g)
        {
            double x = pos.i;
            double y = pos.j;
            
            g.setColor(color);
            g.fillOval((int)(x-radius), (int)(y-radius), (int)(2*radius), (int)(2*radius));
            // g.drawImage(flake, (int)(x-radius), (int)(y-radius), (int)(2*radius), (int)(2*radius), null);
        }
    }
    
    static int T_DELAY = 35;
    static javax.swing.Timer timer;
    
    static int fw = 750; // Width of Frame
    static int fh = 750; // Height of Frame

    static int gw = 3*fw/2;
    static int gh = fh;
    
    static Color defaultBKG = new Color(50, 50, 50);
    static Color bkg = defaultBKG;
    
    static LinkedList<Snow> list = new LinkedList<Snow>();

    //static Image flake = new ImageIcon("images/Snowflake.png").getImage();

    static Slider slider;
    
    public static void main(String[] args)
    {
        main();
    }
    
    public static void main()
    {
        initializeValues();
        
        JFrame f = new JFrame("New Project");
        
        f.setSize(fw, fh+20);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        
        f.add(new Snowfall());
        
        f.setVisible(true);
    }
    
    public static void initializeValues()
    {
        slider = new Slider(fw/2, fh, fw, 0, fw);

        for(int i = 0; i < 300; i++)
        {
            list.add(new Snow(p.map(Math.random(), 0, 1, 0, fw), -10, p.map(Math.random(), 0, 1, -50, 50)));
        }
    }
    
    public Snowfall()
    {
        setBackground(bkg);
        
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        requestFocus(true);
        
        timer = new javax.swing.Timer(T_DELAY, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        for(Snow snow: list)
        {
            snow.draw(g);
        }

        slider.draw(g);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        for(Snow snow: list)
        {
            snow.move();
        }
        
        repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
        else if(key == KeyEvent.VK_UP)
        {
        }
        else if(key == KeyEvent.VK_DOWN)
        {
        }
        else if(key == KeyEvent.VK_LEFT)
        {
        }
        else if(key == KeyEvent.VK_RIGHT)
        {
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_ESCAPE)
        {
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
    }
    
    @Override
    public void mouseReleased(MouseEvent e)
    {
    }
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
    }
    
    @Override
    public void mouseDragged(MouseEvent e)
    {
        int mx = e.getX();
        int my = e.getY();
        if(slider.contains(mx, my))
        {
            if(slider.onPointer(mx, my));
            slider.drag(mx, my);
        }
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
    public void mouseWheelMoved(MouseWheelEvent e)
    {
    }
}
