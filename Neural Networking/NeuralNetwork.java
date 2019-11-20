package ai;

public class NeuralNetwork
{
    int no_input, no_hidden, no_output;

    double lr = 0.5;

    Matrix w_ih, w_ho;
    Matrix bias_h;
    Matrix bias_o;

    public static void main()
    {
        NeuralNetwork brain = new NeuralNetwork(2, 100, 1);

        double[][] inputs = {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
            };
        double[][] answers = {
                {0},
                {0},
                {0},
                {1}
            };

        for(int i = 0; i < 1000; i++)
        {
            int index = (int)(Math.random()*inputs.length);
            index %= inputs.length;
            brain.train(inputs[index], answers[index]);
        }

        for(int i = 0; i < inputs.length; i++)
        {
            double[] input = inputs[i];
            for(int j = 0; j < input.length; j++)
            {
                System.out.print(input[j]+", ");
            }
            System.out.print("---> ");
            System.out.println(brain.feedForward(input)[0]);
            System.out.println("\n");
        }
    }

    public NeuralNetwork(int no_input_, int no_hidden_, int no_output_)
    {
        no_input = no_input_;
        no_hidden = no_hidden_;
        no_output = no_output_;

        w_ih = new Matrix(no_hidden, no_input).randomize();
        w_ho = new Matrix(no_output, no_hidden).randomize();
        bias_h = new Matrix(no_hidden, 1).randomize();
        bias_o = new Matrix(no_output, 1).randomize();
    }

    public double[] feedForward(double[] input_array)
    {
        Matrix input = Matrix.toMatrix(input_array);

        // Generate the hidden layer
        Matrix hidden = w_ih.clone().dot(input);
        hidden.add(bias_h);

        // Activate the hidden layer
        hidden = sigmoid(hidden);

        // Generate the Ouput layer
        Matrix guess = w_ho.clone().dot(hidden);
        guess.add(bias_o);

        // Activate the output layer
        guess = sigmoid(guess);

        return guess.toArray();
    }

    public void train(double[] input_array, double[] target_array)
    {
        Matrix input = Matrix.toMatrix(input_array);

        // Generate the hidden layer
        Matrix hidden = w_ih.clone().dot(input);
        hidden.add(bias_h);
        
        hidden = sigmoid(hidden);

        // Generate the Ouput layer
        Matrix guess = w_ho.clone().dot(hidden);
        guess.add(bias_o);

        // Activate the output layer
        guess = sigmoid(guess);

        Matrix targets = Matrix.toMatrix(target_array);

        // Calculate the error
        // ERROR = TARGETS - OUTPUTS
        Matrix output_errors = targets.clone().sub(guess);

        // Output gradient = guess * (1-guess)
        Matrix gradient = dsigmoid(guess.clone()).dotWithHadamard(output_errors).dot(lr);

        // Calculate deltas
        Matrix hidden_t = hidden.getTranspose();
        Matrix w_ho_deltas = gradient.clone().dot(hidden_t);

        // Add to the weights of the hidden to output layers
        w_ho.add(w_ho_deltas);
        bias_o.add(gradient);

        // Calculate hidden errors
        Matrix w_ho_t = w_ho.getTranspose();
        Matrix hidden_errors = w_ho_t.clone().dot(output_errors);

        // Calculate hidden gradient
        Matrix hidden_gradient = dsigmoid(hidden.clone());
        hidden_gradient.dotWithHadamard(hidden_errors).dot(lr);

        // Calculate deltas
        Matrix input_t = input.getTranspose();
        Matrix w_ih_deltas = hidden_gradient.clone().dot(input_t);

        w_ih.add(w_ih_deltas);
        bias_h.add(hidden_gradient);

        /*target.display();
        System.out.println();
        expected.display();
        System.out.println();
        errors.display();*/
    }

    public static double sigmoid(double x)
    {
        double result = 1.0/(1.0+Math.exp(-x));
        return result;
    }

    public static Matrix sigmoid(Matrix m)
    {
        for(int i = 0; i < m.rows; i++)
        {
            for(int j = 0; j < m.cols; j++)
            {
                m.arr[i][j] = sigmoid(m.arr[i][j]);
            }
        }

        return m;
    }

    public static double dsigmoid(double val)
    {
        return val * (1-val);
    }

    public static Matrix dsigmoid(Matrix m)
    {
        for(int i = 0; i < m.rows; i++)
        {
            for(int j = 0; j < m.cols; j++)
            {
                m.arr[i][j] = dsigmoid(m.arr[i][j]);
            }
        }

        return m;
    }
}
