package ai;

public class Matrix
{
    double[][] arr;
    int rows;
    int cols;

    public static void main(String[] args)
    {
        Matrix m = new Matrix(3, 2).randomize();
        m.display();
    }

    public Matrix(int rows_, int cols_)
    {
        rows = rows_;
        cols = cols_;

        arr = new double[rows][cols];

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                arr[i][j] = 0;
            }
        }
    }

    /**
     * Changes the state of the current matrix object by adding `x` to all the terms
     */

    public Matrix add(double x)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                arr[i][j] += x;
            }
        }

        return this;
    }

    /**
     * Changes the state of the current matrix object by adding `m` to it if they match in order, otherwise no operation takes place
     */

    public Matrix add(Matrix m)
    {
        if(rows == m.rows && cols == m.cols)
        {
            for(int i = 0; i < rows; i++)
            {
                for(int j = 0; j < cols; j++)
                {
                    arr[i][j] += m.arr[i][j];
                }
            }

            return this;
        }
        else
        {
            System.err.println("Cannot add two matrices of different sizes");
            return this;
        }
    }

    /**
     * Changes the state of the current matrix object by subtracting `x` from all the terms
     */

    public Matrix sub(double x)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                arr[i][j] -= x;
            }
        }

        return this;
    }

    /**
     * Changes the state of the current matrix object by subtracting `m` from it if they match in order, otherwise no operation takes place
     */

    public Matrix sub(Matrix m)
    {
        if(rows == m.rows && cols == m.cols)
        {
            for(int i = 0; i < rows; i++)
            {
                for(int j = 0; j < cols; j++)
                {
                    arr[i][j] -= m.arr[i][j];
                }
            }

            return this;
        }
        else
        {
            System.err.println("Cannot subtract two matrices of different sizes");
            return this;
        }
    }

    /**
     * Changes the state of the current matrix object by multiplying each term by `factor`
     */

    public Matrix dot(double factor)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                arr[i][j] *= factor;
            }
        }

        return this;
    }

    /**
     * Changes the state of the current matrix object according to `new Matrix() = [this].m`
     */

    public Matrix dot(Matrix m)
    {
        if(cols == m.rows)
        {
            Matrix result = new Matrix(rows, m.cols);

            for(int i = 0; i < result.rows; i++)
            {
                for(int j = 0; j < result.cols; j++)
                {
                    double sum = 0;

                    for(int k = 0; k < cols; k++)
                    {
                        sum += arr[i][k]*m.arr[k][j];
                    }

                    result.arr[i][j] = sum;
                }
            }

            return result;
        }
        else
        {
            System.err.println("Incompatible matrix multiplication! ("+rows+", "+cols+") with ("+m.rows+", "+m.cols+")");
            return this;
        }
    }

    /**
     * Changes the state of the current matrix object by multiplying each element of the current object with the corresponding element from `m`
     */

    public Matrix dotWithHadamard(Matrix m)
    {
        if(m.rows == rows && m.cols == cols)
        {
            for(int i = 0; i < rows; i++)
            {
                for(int j = 0; j < cols; j++)
                {
                    arr[i][j] *= m.arr[i][j];
                }
            }
        }
        else
        {
            System.err.println("The sizes for the hadamard product are not compatible!");
        }
        return this;
    }

    /**
     * Returns a NEW Matrix object which is the transpose of the current object
     */

    public Matrix getTranspose()
    {
        Matrix result = new Matrix(cols, rows);

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                result.arr[j][i] = arr[i][j];
            }
        }

        return result;
    }

    /**
     * Changes the state of the current matrix object by setting each value to a number between -1 and 1
     */

    public Matrix randomize()
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                arr[i][j] = (Math.random()*2 - 1);
            }
        }

        return this;
    }

    public Matrix clone()
    {
        Matrix clone = new Matrix(rows, cols);

        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
            {
                clone.arr[i][j] = arr[i][j];
            }
        }

        return clone;
    }

    public static Matrix toMatrix(double[] array)
    {
        Matrix result = new Matrix(array.length, 1);

        for(int i = 0; i < array.length; i++)
        {
            result.arr[i][0] = array[i];
        }

        return result;
    }

    public double[] toArray()
    {
        double[] array = new double[rows];

        for(int i = 0; i < rows; i++)
        {
            array[i] = arr[i][0];
        }

        return array;
    }

    public void display()
    {
        System.out.println(" --");
        for(int i = 0; i < rows; i++)
        {
            System.out.print("| ");
            for(int j = 0; j < cols; j++)
            {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println(" --");
    }
}

/*
for(int i = 0; i < rows; i++)
{
for(int j = 0; j < cols; j++)
{
}
}

[1 2 3][1]   [(1*1)+(2*2)+(3*3)]
[2 3 4][2] = [(2*1)+(3*2)+(4*3)]
[6 7 8][3]   [(6*1)+(7*2)+(8*3)]
 */
