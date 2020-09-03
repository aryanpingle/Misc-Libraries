import java.awt.*;
import java.awt.geom.*;

public class Segment
{
    static int default_padding_h, default_padding_w;
    static AffineTransform at;

    static Vector temp_line;

    public static void main(String[] args) {
        Main.main(args);
    }
    
    public static void init()
    {
        default_padding_h = 10;
        default_padding_w = 5;

        at = new AffineTransform();

        temp_line = new Vector();
    }

    Segment child;

    Segment parent;
    Vector pos;
    Vector to;
    double radius;
    double angle;
    double padding_w, padding_h;
    Shape shape;
    
    public Segment(Vector pos, double padding_w, double padding_h, Vector radius)
    {
        this.parent = null;
        this.pos = pos.clone();
        this.padding_w = padding_w;
        this.padding_h = padding_h;
        this.radius = radius.getMagnitude();
        this.angle = radius.getAngle();

        calculateEnd();
    }
    
    public Segment(Vector pos, Vector radius)
    {
        this.parent = null;
        this.pos = pos.clone();
        this.padding_w = default_padding_w;
        this.padding_h = default_padding_h;
        this.radius = radius.getMagnitude();
        this.angle = radius.getAngle();

        calculateEnd();
    }

    public Segment(double ox, double oy, double padding_w, double padding_h, Vector radius)
    {
        this.parent = null;
        this.pos = new Vector(ox, oy);
        this.padding_w = padding_w;
        this.padding_h = padding_h;
        this.radius = radius.getMagnitude();
        this.angle = radius.getAngle();

        calculateEnd();
    }

    public Segment(double ox, double oy, Vector radius)
    {
        this.parent = null;
        this.pos = new Vector(ox, oy);
        this.padding_w = default_padding_w;
        this.padding_h = default_padding_h;
        this.radius = radius.getMagnitude();
        this.angle = radius.getAngle();

        calculateEnd();
    }

    public Segment(Segment parent, double padding_w, double padding_h, Vector radius)
    {
        this.parent = parent;
        this.pos = new Vector(parent.to.i, parent.to.j);
        this.padding_w = padding_w;
        this.padding_h = padding_h;
        this.radius = radius.getMagnitude();
        this.angle = radius.getAngle();

        calculateEnd();
    }

    public Segment(Segment parent, Vector radius)
    {
        this.parent = parent;
        this.pos = parent.to.clone();
        this.padding_w = default_padding_w;
        this.padding_h = default_padding_h;
        this.radius = radius.getMagnitude();
        this.angle = radius.getAngle();

        calculateEnd();
    }

    public void calculateEnd()
    {
        if(to == null)
        {
            to = new Vector();
        }
        to.set(pos.i + radius*Math.cos(angle), pos.j + radius*Math.sin(angle));

        shape = getShape();
    }

    public void follow(Vector pointer)
    {
        // temp_line is the line from this segment's position to the pointer
        temp_line.set(pointer).sub(pos);
        angle = temp_line.getAngle();
        temp_line.setMagnitude(radius);
        pos.set(pointer.sub(temp_line));
        calculateEnd();
    }

    public void follow(Vector pointer, double move)
    {
        // line is the line from this segment's position to the pointer
        temp_line.set(pointer).sub(pos);
        if(temp_line.getMagnitude() <= radius+1)
        {
            return;
        }
        angle = temp_line.getAngle();
        temp_line.setMagnitude(move);
        pos.add(temp_line);
        calculateEnd();
    }

    public void follow(double px, double py)
    {
        follow(new Vector(px, py));
    }

    public void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.draw(shape);

        // g2d.fillRect((int)(pos.i)-1, (int)(-pos.j)-1, 2, 2);
        // g2d.fillRect((int)(to.i)-2, (int)(-to.j)-2, 4, 4);

        g2d.dispose();
    }

    public void fill(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g.create();
        g2d.fill(shape);

        // g2d.fillRect((int)(pos.i)-1, (int)(-pos.j)-1, 2, 2);
        // g2d.fillRect((int)(to.i)-2, (int)(-to.j)-2, 4, 4);

        g2d.dispose();
    }

    public void rotate(double theta)
    {
        angle += theta;
        calculateEnd();
    }

    public Shape getShape()
    {
        Shape rect = new Rectangle2D.Double(pos.i-padding_w, -pos.j-padding_h, radius+2*padding_w, 2*padding_h);

        at.rotate(-angle, pos.i, -pos.j);
        rect = at.createTransformedShape(rect);
        at.setToIdentity();

        return rect;
    }
}
