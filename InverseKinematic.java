import java.awt.*;
import java.awt.geom.*;

public class InverseKinematic
{
    public static void main(String[] args) {
        Main.main(args);
    }
    
    Segment Start;
    Segment Last;
    
    Vector startPos;

    int count = 10;
    int length = 50;
    
    boolean fixed;

    public InverseKinematic()
    {
        fixed = false;
        startPos = new Vector();
    }
    
    public InverseKinematic(double ox, double oy, int count, int length)
    {
        this();
        
        Start = new Segment(ox, oy, new Vector(length, 0));
        startPos.set(Start.pos);
        
        Segment current = Start;
        
        for(int i = 0; i < count-1; ++i)
        {
            Segment child = null;
            if(i % 4 == 0)
            {
                child = new Segment(current, new Vector(0, length));
            }
            else if(i % 4 == 1)
            {
                child = new Segment(current, new Vector(-length, 0));
            }
            else if(i % 4 == 2)
            {
                child = new Segment(current, new Vector(0, -length));
            }
            else
            {
                child = new Segment(current, new Vector(length, 0));
            }
            current.child = child;
            current = child;
        }
        
        // Now current is the last child
        Last = current;
    }
    
    public InverseKinematic(double ox, double oy, int count, int length, int padding_w, int padding_h)
    {
        this();
        
        Start = new Segment(ox, oy, padding_w, padding_h, new Vector(length, 0));
        startPos.set(Start.pos);
        
        Segment current = Start;
        
        for(int i = 0; i < count-1; ++i)
        {
            Segment child = null;
            if(i % 4 == 0)
            {
                child = new Segment(current, padding_w, padding_h, new Vector(0, length));
            }
            else if(i % 4 == 1)
            {
                child = new Segment(current, padding_w, padding_h, new Vector(-length, 0));
            }
            else if(i % 4 == 2)
            {
                child = new Segment(current, padding_w, padding_h, new Vector(0, -length));
            }
            else
            {
                child = new Segment(current, padding_w, padding_h, new Vector(length, 0));
            }
            current.child = child;
            current = child;
        }
        
        // Now current is the last child
        Last = current;
    }
    
    public InverseKinematic(double ox, double oy, int count, int length, boolean fixed)
    {
        this();
        this.fixed = fixed;
        
        Start = new Segment(ox, oy, new Vector(length, 0));
        startPos.set(Start.pos);
        
        Segment current = Start;
        
        for(int i = 0; i < count; ++i)
        {
            Segment child = null;
            if(i % 4 == 0)
            {
                child = new Segment(current, new Vector(0, length));
            }
            else if(i % 4 == 1)
            {
                child = new Segment(current, new Vector(-length, 0));
            }
            else if(i % 4 == 2)
            {
                child = new Segment(current, new Vector(0, -length));
            }
            else
            {
                child = new Segment(current, new Vector(length, 0));
            }
            current.child = child;
            current = child;
        }
        
        // Now current is the last child
        Last = current;
    }
    
    public void draw(Graphics g)
    {
        Segment current = Start;
        while(current != null)
        {
            current.draw(g);
            current = current.child;
        }
    }
    
    public void fill(Graphics g)
    {
        Segment current = Start;
        while(current != null)
        {
            current.fill(g);
            current = current.child;
        }
    }
    
    public void follow(Vector target)
    {
        target = target.clone();
        Segment current = Last;
        while(current != null)
        {
            current.follow(target);
            target.set(current.pos);
            current = current.parent;
        }
        
        if(fixed)
        {
            Start.pos.set(startPos);
            Start.calculateEnd();
            
            current = Start.child;
            while(current != null)
            {
                current.pos.set(current.parent.to);
                current.calculateEnd();
                
                current = current.child;
            }
        }
    }
    
    public void follow(Vector target, double move)
    {
        target = target.clone();
        Segment current = Last;
        while(current != null)
        {
            if(current == Last) current.follow(target, move);
            else current.follow(target);
            target.set(current.pos);
            current = current.parent;
        }
        
        if(fixed)
        {
            Start.pos.set(startPos);
            Start.calculateEnd();
            
            current = Start.child;
            while(current != null)
            {
                current.pos.set(current.parent.to);
                current.calculateEnd();
                
                current = current.child;
            }
        }
    }
}
