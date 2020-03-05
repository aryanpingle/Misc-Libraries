import java.awt.*;

public class Vector
{
    static double pi = Math.PI;
    
    double i, j;
    
    public static void main(String[] args)
    {
        System.out.println(new Vector(-1, 1).rotate(-pi/2));
    }
    
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
    
    /**
    * Creates a new vector object that points from (x1, y1) to (x2, y2)
    */
    
    public Vector(double x1, double y1, double x2, double y2)
    {
        this.i = x2-x1;
        this.j = y2-y1;
    }
    
    /**
    * Creates a new vector object with direction ratios <b>i</b> and <b>j</b> with a net magnitude of <b>magnitude</b>
    */
    
    public Vector(double i, double j, double magnitude)
    {
        this.i = i;
        this.j = j;
        
        setMagnitude(magnitude);
    }
    
    /**
    * Sets the <b>i</b> and <b>j</b> components of the current vector to the passed values
    */
    
    public Vector set(double i, double j)
    {
        this.i = i;
        this.j = j;

        return this;
    }

    /**
    * Sets the <b>i</b> and <b>j</b> components of the current vector to that of the passed value
    */
    
    public Vector set(Vector v)
    {
        this.i = v.i;
        this.j = v.j;

        return this;
    }

    /**
    * Changes the state of the current vector object by keeping its maximum magnitude at <b>mag</b>
    */
    
    Vector limitMag(double mag)
    {
        if(getMagnitude() > mag)
        {
            setMagnitude(mag);
        }
        
        return this;
    }
    
    /**
    * Changes the state of the current vector object by limiting its absolute <b>i</b> component at a maximum of <b>mag</b>
    */
    
    Vector limitI(double mag)
    {
        if(Math.abs(i) > mag)
        {
            i = mag*i/Math.abs(i);
        }
        
        return this;
    }
    
    /**
    * Changes the state of the current vector object by limiting its absolute <b>j</b> component at a maximum of <b>mag</b>
    */
    
    Vector limitJ(double mag)
    {
        if(Math.abs(j) > mag)
        {
            j = mag*j/Math.abs(j);
        }
        
        return this;
    }
    
    /**
    * Changes the state of the current vector object by limiting both absolute components at a maximum of <b>mag</b> each
    */
    
    Vector limitEach(double mag)
    {
        limitI(mag);
        limitJ(mag);
        
        return this;
    }
    
    /*
    * Sets the i and j components of this vector to a random number in [-factor, factor]
    */
    
    Vector randomize(double factor)
    {
        i = p.map(Math.random(), 0, 1, -factor, factor);
        j = p.map(Math.random(), 0, 1, -factor, factor);
        
        return this;
    }
    
    /**
    * Returns the angle of this vector from the POSITIVE X AXIS measured in the CLOCKWISE sense (in radians)
    *
    * A vector pointing towards (0, -1) returns 3*PI/2
    * A vector pointing towards (-1, 1) returns 3*PI/4
    * @return The correct, Euclidian angle of the vector considering positive j values to be pointing UP
    */
    
    public double getAngle()
    {
        double y = j;
        double x = i;
        
        if(y == 0 && x == 0)
        {
            return 0;
        }
        
        if(y == 0)
        {
            return x>0?0:pi;
        }
        if(x == 0)
        {
            return y>0?pi/2:3*pi/2;
        }
        if(y > 0 && x > 0) // Q1
        {
            return Math.atan(y/x);
        }
        else if(y > 0 && x < 0) // Q2
        {
            return pi+Math.atan(y/x);
        }
        else if(y < 0 && x < 0) // Q3
        {
            return pi+Math.atan(y/x);
        }
        else // Q4
        {
            return 2*pi + Math.atan(y/x);
        }
    }
    
    public double getMagnitude()
    {
        return Math.sqrt(i*i+j*j);
    }
    
    /**
    * Changes the state of the current vector object and returns its address
    * @return The current vector object after adding <b>v</b> to it
    */
    
    public Vector add(Vector v)
    {
        i += v.i;
        j += v.j;
        
        return this;
    }
    
    /**
    * Changes the state of the current vector object and returns its address
    * @return The current vector object after subtracting <b>v</b> from it
    */
    
    public Vector sub(Vector v)
    {
        i -= v.i;
        j -= v.j;
        
        return this;
    }
    
    /**
    * Changes the state of the current vector object and returns its address
    * @return The current vector object after adding <b>v</b> to it
    */
    
    public Vector add(double i, double j)
    {
        this.i += i;
        this.j += j;
        
        return this;
    }
    
    /**
    * Changes the state of the current vector object and returns its address
    * @return The current vector object after subtracting <b>v</b> from it
    */
    
    public Vector sub(double i, double j)
    {
        this.i -= i;
        this.j -= j;
        
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
        if(i == 0 && j == 0)
        {
            return this;
        }
        
        double d = getMagnitude();
        
        this.i = this.i*magnitude/d;
        this.j = this.j*magnitude/d;
        
        return this;
    }
    
    public void draw(Graphics g, double ox, double oy, double factor)
    {
        Vector u = this.clone().normalize();
        g.drawLine((int)(ox), (int)(oy), (int)(ox+factor*u.i), (int)(oy+factor*u.j));
    }
    
    /**
    * Changes the current vector object to return its unit vector
    */
    
    public Vector normalize()
    {
        this.i /= getMagnitude();
        this.j /= getMagnitude();
        
        return this;
    }
    
    /**
    * Returns the component of the current vetor object along the vector <b>v</b>
    * @return A new vector object along the vector <b>v</b>
    */
    
    public Vector along(Vector v)
    {
        double dot = dot(v);
        double mag = v.getMagnitude();
        Vector result = v.clone().dot(dot/(mag*mag));
        return result;
    }
    
    /**
    * Changes the state of the current vector object and returns its address
    */
    
    public Vector reverse()
    {
        i = -i;
        j = -j;
        
        return this;
    }
    
    /**
    * Changes the current object by adding the angle in the COUNTER-CLOCKWISE sense
    * @param angle The angle to be rotated by, in RADIANS
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
