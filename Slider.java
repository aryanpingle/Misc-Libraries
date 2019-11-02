import java.awt.*;

public class Slider
{
    double padding = 8;
    
    double x, y; // Origin of the slider
    double len;
    double lb, ub;

    double pos = 0; // X position of pointer with respect to the origin x

    static int pointersize = 5;
    
    public Slider()
    {
        x = 0;
        y = 0;
        len = 0;
        lb = ub = 0;
    }
    
    public Slider(double x, double y, double len, double lb, double ub)
    {
        this.x = x;
        this.y = y;
        this.len = len;
        this.lb = lb;
        this.ub = ub;
    }
    
    public double getValue()
    {
        return p.map(pos, -len/2, len/2, lb, ub);
    }
    
    public void draw(Graphics g)
    {
        // Draw the box
        g.setColor(Color.GRAY);
        g.fillRect((int)(x-len/2-padding), (int)(y-padding), (int)(len+2*padding), (int)(2*padding));

        // Draw the bar
        g.setColor(Color.BLACK);
        g.drawLine((int)(x-len/2), (int)(y), (int)(x+len/2), (int)(y));
        g.drawLine((int)(x-len/2), (int)(y+1), (int)(x+len/2), (int)(y+1)); // For thickness

        //Draw the origin x
        g.drawLine((int)(x), (int)(y-(padding-2)), (int)(x), (int)(y+(padding-2)));

        // Draw the pointer
        g.setColor(Color.BLUE);
        g.fillRect((int)(x+pos-pointersize), (int)(y-pointersize), (int)(2*pointersize), (int)(2*pointersize));
    }

    public boolean contains(int mx, int my)
    {
        return (getBounds().contains(mx, my));
    }

    public boolean onPointer(int mx, int my)
    {
        return getPointerBounds().contains(mx, my);
    }

    public Rectangle getBounds()
    {
        return new Rectangle((int)(x-len/2-padding), (int)(y-padding), (int)(len+2*padding), (int)(2*padding));
    }

    public Rectangle getPointerBounds()
    {
        return new Rectangle((int)(x+pos-pointersize), (int)(y-pointersize), (int)(2*pointersize), (int)(2*pointersize));
    }

    public void drag(int mx, int my)
    {
        mx = (int)G.constrain(mx, x-len/2, x+len/2);

        pos = mx-x;
    }
}
