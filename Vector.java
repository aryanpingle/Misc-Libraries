package PhysicsSim;

import java.awt.*;
import javax.swing.*;

public class Vector
{
    double i, j;
    // j points down for positive values

    public Vector()
    {
        i = 0;
        j = 0;
    }

    public Vector(double i, double j)
    {
        this.i = i;
        this.j = j;
    }

    public Vector(double i, double j, double magnitude)
    {
        this.i = i;
        this.j = j;

        setMagnitude(magnitude);
    }

    /*
     * Sets the i and j components of this vector to a random number in [0, factor]
     */

    Vector randomize(double factor)
    {
        i = p.map(Math.random(), 0, 1, -factor, factor);
        j = p.map(Math.random(), 0, 1, -factor, factor);

        return this;
    }

    public double getAngle()
    {
        double mx = i;
        double my = j;
        int px = 0, py = 0;

        if(mx == 0)
        {
            return (my>py?Math.PI/2:Math.PI*3/2);
        }
        if(my == 0)
        {
            return (mx<px?Math.PI:0);
        }

        if(mx > px && my > py)
        {
            return Math.atan((my-py)/(mx-px));
        }
        else if(mx > px && my < py)
        {
            return (2*Math.PI+Math.atan((my-py)/(mx-px)));
        }
        else if(mx < px && my > py)
        {
            return (Math.PI+Math.atan((my-py)/(mx-px)));
        }
        else
        {
            return (Math.PI+Math.atan((my-py)/(mx-px)));
        }
    }

    public double getMagnitude()
    {
        return Math.sqrt(i*i+j*j);
    }

    /*
     * Changes the state of the current vector object and returns its address
     */

    public Vector add(Vector v)
    {
        //return new Vector(i+v.i, j+v.j);
        i += v.i;
        j += v.j;

        return this;
    }

    /*
     * Changes the state of the current vector object and returns its address
     */

    public Vector sub(Vector v)
    {
        //return new Vector(i-v.i, j-v.j);
        i -= v.i;
        j -= v.j;

        return this;
    }

    /*
     * Changes the state of the current vector object and returns its address
     */

    public Vector add(double i, double j)
    {
        //return new Vector(this.i+i, this.j+j);
        this.i += i;
        this.j += j;

        return this;
    }

    /*
     * Changes the state of the current vector object and returns its address
     */

    public Vector sub(double i, double j)
    {
        //return new Vector(this.i-i, this.j-j);
        this.i += i;
        this.j += j;

        return this;
    }

    public double dot(Vector v)
    {
        return i*v.i + j*v.j;
    }

    public double dot(double i, double j)
    {
        return this.i*i + this.j*j;
    }

    public Vector dot(double factor)
    {
        i *= factor;
        j *= factor;

        return this;
    }

    public Vector setMagnitude(double magnitude)
    {
        double d = getMagnitude();

        if(d == 0)
        {
            return this;
        }
        else
        {
            this.i = this.i*magnitude/d;
            this.j = this.j*magnitude/d;

            return this;
        }
    }

    public void draw(Graphics g, double ox, double oy, double factor)
    {
        double angle = getAngle();

        g.drawLine((int)(ox), (int)(oy), (int)(ox+factor*Math.cos(angle)), (int)(oy+factor*Math.sin(angle)));
    }

    public Vector getUnitVector()
    {
        this.i /= getMagnitude();
        this.j /= getMagnitude();

        return this;
    }

    /*
     * Returns the component of the current vetor aboject along the vector `v`
     */

    public Vector along(Vector v)
    {
        double dot = dot(v);
        double mag = v.getMagnitude();
        Vector result = v.clone().dot(dot/(mag*mag));
        return result;
    }

    /*
     * Changes the state of the current vector object and returns its address
     */

    public Vector reverse()
    {
        i = -i;
        j = -j;

        return this;
    }

    /*
     * Adds the angle in the COUNTER-CLOCKWISE sense
     */

    public Vector rotate(double angle)
    {
        angle += getAngle();

        double mag = getMagnitude();

        i = mag*Math.cos(angle);
        j = mag*Math.sin(angle);

        return this;
    }

    public Vector clone()
    {
        return new Vector(i, j);
    }

    @Override
    public String toString()
    {
        return i+"i, "+j+"j";
    }
}
