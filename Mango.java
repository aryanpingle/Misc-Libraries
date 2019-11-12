import java.util.*;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

public class Mango extends JPanel implements MouseListener, MouseWheelListener, MouseMotionListener, ActionListener, KeyListener
{
    static int T_DELAY = 100;
    static javax.swing.Timer timer;
    
    static int fw = 750; // Width of Frame
    static int fh = 750; // Height of Frame
    
    static Color defaultBKG = new Color(255, 255, 255);
    static Color bkg = defaultBKG;
    
    public static void main(String[] args)
    {
        initializeValues();
        
        JFrame f = new JFrame("Mango");
        
        f.setSize(fw, fh);
        f.setLocationRelativeTo(null);
        //f.setUndecorated(true);
        
        f.add(new Mango());
        
        f.setVisible(true);
    }
    
    public static void initializeValues()
    {
    }
    
    public Mango()
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
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
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
