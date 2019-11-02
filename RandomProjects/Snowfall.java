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
        Color color = new Color(255, 255, 255);

        Vector pos;
        Vector vel;
        Vector g;
        
        double radius;

        double angle = 0;
        double angleMove = 0.2;

        int flakeType = 0;
        
        public Snow(double x, double y, double z)
        {
            pos = new Vector(x, y);
            
            /*
            Right in front of you means z = -50, radius = 10, g = 1
            Far back is z = 50, radius = 5, g = 0.1
            */
            
            radius = p.map(z, -50, 50, maxRadius, minRadius);
            vel = new Vector(0, defaultVelocity);
            g = new Vector(0, p.map(z, -50, 50, maxG, minG));
            
            color = C.getColorTowards(Color.WHITE, Color.BLACK, p.map(z, -50, 50, 0, 0.4));
            
            angle = p.map(Math.random(), 0, 1, 0, Math.PI*2);
            angleMove = p.map(z, -50, 50, 0.1, 0.2);

            flakeType = (int)p.map(Math.random(), 0, 1, 0, flakeTypes.length);
            flakeType = (int)G.constrain(flakeType, 0, flakeTypes.length-1);
        }

        public void convertToNewFlake()
        {
            double z = p.map(Math.random(), 0, 1, -50, 50);
            pos = new Vector(p.map(Math.random(), 0, 1, -fw/2, 3*fw/2), -50);
            radius = p.map(z, -50, 50, maxRadius, minRadius);
            vel = new Vector(0, defaultVelocity);
            g = new Vector(0, p.map(z, -50, 50, maxG, minG));
            color = C.getColorTowards(Color.WHITE, Color.BLACK, p.map(z, -50, 50, 0, 0.4));

            flakeType = (int)p.map(Math.random(), 0, 1, 0, flakeTypes.length);
            flakeType = (int)G.constrain(flakeType, 0, flakeTypes.length-1);
            
            angle = p.map(Math.random(), 0, 1, 0, Math.PI*2);
            angleMove = p.map(z, -50, 50, 0.1, 0.2);
        }
        
        public void move()
        {
            pos.add(vel);
            vel.add(g);
            vel.add(new Vector(p.map(Math.sin(angle), -1, 1, -0.005, 0.005), 0));

            vel.add(wind);

            if(pos.j > fh || pos.i < -50 || pos.i > fw+50)
            {
                convertToNewFlake();
            }

            angle += angleMove/10;
        }
        
        public void draw(Graphics g)
        {
            double x = pos.i;
            double y = pos.j;
            
            // g.setColor(color);
            // g.fillOval((int)(x-radius), (int)(y-radius), (int)(2*radius), (int)(2*radius));
            Graphics2D g2d = (Graphics2D)g.create();
            g2d.rotate(angle/2, x, y);
            g2d.drawImage(flakes[flakeType], (int)(x-radius), (int)(y-radius), (int)(2*radius), (int)(2*radius), null);
        }
    }
    
    static int T_DELAY = 35;
    static javax.swing.Timer timer;
    
    static int fw = (int)p.ScreenWidth;//1500; // Width of Frame
    static int fh = (int)p.ScreenHeight-90;//750; // Height of Frame

    static int gw = fw;
    static int gh = fh;
    
    static Color defaultBKG = new Color(30, 30, 60);
    static Color bkg = defaultBKG;
    
    static LinkedList<Snow> list = new LinkedList<Snow>();

    static Image flake = new ImageIcon("images/Flake.png").getImage();

    static String[] flakeTypes = new String[]{"Flake2.png", "Flake3.png", "Flake4.png", "Flake5.png"};
    static Image[] flakes;

    static Slider slider;

    static Vector wind;

    static final int maxRadius = 27;
    static final int minRadius = 5;

    static final double defaultVelocity = 2.5;

    static final double maxG = 0.05;
    static final double minG = 0.001;
    
    public static void main(String[] args)
    {
        main();
    }
    
    public static void main()
    {
        initializeValues();
        
        JFrame f = new JFrame("New Project");
        
        f.setSize(fw, fh+10);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        
        f.add(new Snowfall());
        
        f.setVisible(true);
    }
    
    public static void initializeValues()
    {
        slider = new Slider(fw/2, fh, fw, 0, fw);

        flakes = new Image[flakeTypes.length];
        for(int i = 0; i < flakeTypes.length; i++)
        {
            flakes[i] = new ImageIcon("images/"+flakeTypes[i]).getImage();
        }

        wind = new Vector(p.map(slider.getValue(), slider.lb, slider.ub, -0.003, 0.003), 0);

        for(int i = 0; i < 275; i++)
        {
            list.add(new Snow(p.map(Math.random(), 0, 1, 0, fw), p.map(Math.random(), 0, 1, -10, fh), p.map(Math.random(), 0, 1, -50, 50)));
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

        // double yo = fh-30;

        // double tx = fw/2+yo*wind.i/((maxG+minG)/2);
        // int size = 5;
        // g.setColor(Color.WHITE);
        // g.fillRect((int)(tx), (int)(yo), size, size);
        // int n = 10;
        // for(int i = 1; i <= n; i++)
        // {
        //     g.setColor(C.getColorTowards(Color.WHITE, bkg, (i-1)*1.0/n));
        //     g.fillRect((int)(tx-i*size), (int)(yo), size, size);
        //     g.fillRect((int)(tx+i*size), (int)(yo), size, size);
        // }

        slider.draw(g);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        wind = new Vector(p.map(slider.getValue(), slider.lb, slider.ub, -0.06, 0.06), 0);

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
